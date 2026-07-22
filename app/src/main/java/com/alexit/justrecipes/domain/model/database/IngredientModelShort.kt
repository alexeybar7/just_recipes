package com.alexit.justrecipes.domain.model.database

import androidx.room.ColumnInfo

data class IngredientModelShort(
    val id: Int,
    val name: String,
    val synonym: String?,
    @ColumnInfo(name = "is_inputted") val isInputted: Boolean
)
