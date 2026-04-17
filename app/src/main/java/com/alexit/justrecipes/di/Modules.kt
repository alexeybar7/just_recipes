package com.alexit.justrecipes.di

import android.content.Context
import androidx.room.Room
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.RecipesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JustRecipesAppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : RecipesDatabase {
        return Room.databaseBuilder(context, RecipesDatabase::class.java,"RecipeDatabase.db")
            .createFromAsset("databases/RecipeDatabase.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(db: RecipesDatabase): RecipesDao {
        return db.recipesDao()
    }
}