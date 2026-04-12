package com.alexit.justrecipes.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inputted_ingredients")
data class InputtedIngredients(
    @PrimaryKey val id: Int,
    val weight: Int?
)