package com.alexit.justrecipes.di

import android.content.Context
import androidx.room.Room
import com.alexit.justrecipes.data.local.room.dao.RecipesDao
import com.alexit.justrecipes.data.local.room.RecipesDatabase
import com.alexit.justrecipes.data.repository.RecipesRepositoryImpl
import com.alexit.justrecipes.domain.repository.RecipesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipesDatabase {
        return Room.databaseBuilder(context, RecipesDatabase::class.java,"recipesDatabase.db")
            .createFromAsset("databases/RecipesDatabase.db")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipesDao(recipesDatabase: RecipesDatabase): RecipesDao {
        return recipesDatabase.recipesDao()
    }
}