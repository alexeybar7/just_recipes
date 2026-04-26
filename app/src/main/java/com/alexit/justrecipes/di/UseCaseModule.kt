package com.alexit.justrecipes.di

import com.alexit.justrecipes.domain.usecase.AddNewIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetIngredientsUseCase
import com.alexit.justrecipes.domain.repository.RecipesRepository
import com.alexit.justrecipes.domain.usecase.AddInputtedIngredientUseCase
import com.alexit.justrecipes.domain.usecase.ChangeWeightIngredientUseCase
import com.alexit.justrecipes.domain.usecase.RemoveInputtedIngredientUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetIngredientsUseCase(recipesRepository: RecipesRepository): GetIngredientsUseCase {
        return GetIngredientsUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideAddNewIngredientUseCase(recipesRepository: RecipesRepository): AddNewIngredientUseCase{
        return AddNewIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideAddInputtedIngredientUseCase(recipesRepository: RecipesRepository): AddInputtedIngredientUseCase{
        return AddInputtedIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveInputtedIngredientUseCase(recipesRepository: RecipesRepository): RemoveInputtedIngredientUseCase{
        return RemoveInputtedIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideChangeWeightIngredientUseCase(recipesRepository: RecipesRepository): ChangeWeightIngredientUseCase{
        return ChangeWeightIngredientUseCase(recipesRepository)
    }
}