package com.alexit.justrecipes.di

import com.alexit.justrecipes.domain.usecase.AddNewIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetSuggestionsUseCase
import com.alexit.justrecipes.domain.repository.RecipesRepository
import com.alexit.justrecipes.domain.usecase.AddInputtedIngredientUseCase
import com.alexit.justrecipes.domain.usecase.ChangeWeightIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetCategoriesUseCase
import com.alexit.justrecipes.domain.usecase.GetIngredientUseCase
import com.alexit.justrecipes.domain.usecase.GetInputtedIngredientsUseCase
import com.alexit.justrecipes.domain.usecase.GetMAXIdIngredientsUseCase
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
    fun provideGetIngredientUseCase(recipesRepository: RecipesRepository): GetIngredientUseCase {
        return GetIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideGetSuggestionsUseCase(recipesRepository: RecipesRepository): GetSuggestionsUseCase {
        return GetSuggestionsUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideGetInputtedIngredientsUseCase(recipesRepository: RecipesRepository): GetInputtedIngredientsUseCase {
        return GetInputtedIngredientsUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(recipesRepository: RecipesRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideAddNewIngredientUseCase(recipesRepository: RecipesRepository): AddNewIngredientUseCase {
        return AddNewIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideAddInputtedIngredientUseCase(recipesRepository: RecipesRepository): AddInputtedIngredientUseCase {
        return AddInputtedIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveInputtedIngredientUseCase(recipesRepository: RecipesRepository): RemoveInputtedIngredientUseCase {
        return RemoveInputtedIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideChangeWeightIngredientUseCase(recipesRepository: RecipesRepository): ChangeWeightIngredientUseCase {
        return ChangeWeightIngredientUseCase(recipesRepository)
    }

    @Provides
    @Singleton
    fun provideGetMAXIdIngredientsUseCase(recipesRepository: RecipesRepository): GetMAXIdIngredientsUseCase {
        return GetMAXIdIngredientsUseCase(recipesRepository)
    }
}