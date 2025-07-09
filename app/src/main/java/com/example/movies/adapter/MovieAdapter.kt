package com.example.movies.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.model.Movie
import com.example.movies.utils.extensions.dpToPx
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import javax.inject.Inject

class MovieAdapter  @Inject constructor()  : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
        val movieSummary: TextView = itemView.findViewById(R.id.movieSummary)
        val genresLayout: FlexboxLayout = itemView.findViewById(R.id.genresLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.movieTitle.text = movie.name
       holder.movieSummary.text = movie.summary

        Glide.with(holder.itemView.context)
            .load(movie.imageOriginal)
            .placeholder(R.drawable.ic_launcher_background) // optional placeholder
            .error(R.drawable.ic_launcher_foreground) // optional error fallback
            .into(holder.movieImage)

        // Clear existing chips
        holder.genresLayout.removeAllViews()

        // Add genre chips - simplest approach
        movie.genres.forEach { genre ->
            val chip = Chip(holder.itemView.context).apply {
                text = genre
                setChipBackgroundColorResource(R.color.chip_background)
                setTextColor(ContextCompat.getColor(context, android.R.color.white))
                layoutParams = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 0, 8.dpToPx(context), 4.dpToPx(context))
                }
            }
            holder.genresLayout.addView(chip)
        }


        holder.itemView.apply {

            setOnClickListener{
                onItemClickListener?.let{
                    it(movie)
                }
            }
        }


    }

    //Allows the fragment to set a click listener to handle navigation
    private  var onItemClickListener:((Movie) -> Unit) ? = null
    fun setOnItemClickLisnter(listener: (Movie)-> Unit){
        onItemClickListener  = listener
    }
}
