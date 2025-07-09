package com.example.movies.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(
    val id: Int,
    val name: String,
    val url: String,
    val imageOriginal: String,
    val genres: List<String>,
    val summary : String,
    val rating : Double
): Serializable {

    override fun hashCode(): Int {
        return id
    }

    override fun equals(other: Any?): Boolean {
        return other is Movie && other.id == this.id
    }
}
