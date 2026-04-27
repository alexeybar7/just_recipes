package com.alexit.justrecipes.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredients")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val energy: Double,
    val protein: Double,
    val fat: Double,
    val carbohydrate: Double,
    @ColumnInfo(defaultValue = "NULL") val synonym: String? = null,
    val category: String,
    @ColumnInfo(defaultValue = "NULL") val weight: Int? = null,
    @ColumnInfo(name = "is_inputted", defaultValue = "0") val isInputted: Boolean = false
)
