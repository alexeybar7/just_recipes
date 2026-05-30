package com.alexit.justrecipes.domain.model

import androidx.room.ColumnInfo

data class IngredientModelShort(
    val id: Int,
    val name: String,
    @ColumnInfo(name = "is_inputted") val isInputted: Boolean
)
