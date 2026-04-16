package com.alexit.justrecipes.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexit.justrecipes.JusRecipesApp
import com.alexit.justrecipes.data.repository.RecipesRepository
import com.alexit.justrecipes.data.repository.RecipesRepositoryImpl
import com.alexit.justrecipes.data.room.RecipesDao
import com.alexit.justrecipes.data.room.RecipesDatabase
import com.alexit.justrecipes.data.sources.IngredientsSource
import com.alexit.justrecipes.data.sources.IngredientsSourceDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object JustRecipesAppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) : RecipesDatabase {
        return Room.databaseBuilder(app, RecipesDatabase::class.java,"RecipesDatabase.db")
            .createFromAsset("databases/RecipeDatabase.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(db: RecipesDatabase) : RecipesDao {
        return db.recipesDao()
    }
}