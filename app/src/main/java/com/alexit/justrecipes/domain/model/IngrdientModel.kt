package com.alexit.justrecipes.domain.model

import androidx.room.ColumnInfo

data class IngredientModel(
    val id: Int,
    val name: String,
    val category: String,
    val weight: Int? = null,
    @ColumnInfo(name = "is_inputted") val isInputted: Boolean
)
