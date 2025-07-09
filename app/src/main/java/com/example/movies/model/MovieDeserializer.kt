package com.example.movies.model

import com.google.gson.*
import java.lang.reflect.Type

class MovieDeserializer : JsonDeserializer<Movie> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Movie{
        val jsonObject = json.asJsonObject

        val id = jsonObject.get("id").asInt
        val name = jsonObject.get("name").asString
        val url = jsonObject.get("url").asString
        val summary = jsonObject.get("summary").asString
        // Handle image - get original URL
        val imageObject = jsonObject.getAsJsonObject("image")
        val imageOriginal = imageObject.get("original").asString

        // Handle rating - get original URL
        val ratingObject = jsonObject.getAsJsonObject("rating")
        val averageRating = ratingObject.get("average").asDouble

        // Handle genres
        val genresArray = jsonObject.getAsJsonArray("genres")
        val genres = mutableListOf<String>()
        genresArray.forEach { genres.add(it.asString) }

        return Movie(id, name, url, imageOriginal, genres, summary , averageRating)
    }
}