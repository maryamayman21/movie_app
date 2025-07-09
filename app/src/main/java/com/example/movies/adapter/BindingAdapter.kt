//package com.example.movies.adapter
//
//import android.widget.ImageView
//import androidx.core.content.ContextCompat
//import androidx.databinding.BindingAdapter
//import com.bumptech.glide.Glide
//import com.example.movies.R
//import com.example.movies.utils.extensions.dpToPx
//import com.google.android.flexbox.FlexboxLayout
//import com.google.android.material.chip.Chip
//
//@BindingAdapter("imageUrl")
//fun ImageView.setImageUrl(url: String?) {
//    Glide
//        .with(context)
//        .load(url)
//        .placeholder(R.drawable.ic_launcher_background)
//        .error(R.drawable.ic_launcher_foreground)
//        .into(this)
//}
//
//@BindingAdapter("genres")
//fun FlexboxLayout.setGenres(genres: List<String>?) {
//    removeAllViews()
//    genres?.forEach { genre ->
//        val chip = Chip(context).apply {
//            text = genre
//            setChipBackgroundColorResource(R.color.chip_background)
//            setTextColor(ContextCompat.getColor(context, android.R.color.white))
//            layoutParams = FlexboxLayout.LayoutParams(
//                FlexboxLayout.LayoutParams.WRAP_CONTENT,
//                FlexboxLayout.LayoutParams.WRAP_CONTENT
//            ).apply {
//                setMargins(0, 0, 8.dpToPx(context), 4.dpToPx(context))
//            }
//        }
//        addView(chip)
//    }
//}