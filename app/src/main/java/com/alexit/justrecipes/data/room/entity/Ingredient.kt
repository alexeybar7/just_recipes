package com.alexit.justrecipes.data.room.entity

import androidx.compose.ui.text.font.FontWeight
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val energy: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrate: Float,
    val synonym: String?,
    val category: String,
    val weight: Int?,
    @ColumnInfo(name = "is_inputted") val isInputted: Boolean
)
