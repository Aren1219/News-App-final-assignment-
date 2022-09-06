package com.example.newsappfinalassignment.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "news"
)
data class Data(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("keywords")
    val keywords: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("published_at")
    val publishedAt: String,
//    @SerializedName("relevance_score")
//    val relevanceScore: Any,
    @SerializedName("snippet")
    val snippet: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("uuid")
    val uuid: String
)