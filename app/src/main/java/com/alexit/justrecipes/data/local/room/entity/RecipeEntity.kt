package com.alexit.justrecipes.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val portion: Int?,
    val image: String?,
    val details: String?,
    @ColumnInfo(name = "details_img") val detailsImg: String?,
    val duration: Int?,
    @ColumnInfo(name = "is_own") val isOwn: Boolean
)
