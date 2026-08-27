package com.alexit.justrecipes.domain.model.database

import androidx.room.ColumnInfo

data class IngredientModelShort(
    val id: Int,
    val name: String,
    val synonym: String?,
    val isInputted: Boolean
)
