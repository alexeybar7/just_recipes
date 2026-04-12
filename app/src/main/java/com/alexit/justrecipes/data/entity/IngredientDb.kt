package com.alexit.justrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientDb(
    @PrimaryKey val id: Int,
    val name: String,
    val energy: Float,
    val protein: Float,
    val fat: Float,
    val carbohydrate: Float,
    val synonym: String,
    val category: String
)
