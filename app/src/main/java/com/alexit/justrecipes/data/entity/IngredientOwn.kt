package com.alexit.justrecipes.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients_own")
data class IngredientOwn(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String
)
