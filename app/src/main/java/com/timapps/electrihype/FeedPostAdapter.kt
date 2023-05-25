package com.timapps.electrihype

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class FeedPostAdapter (
    private val mList: List<FeedPostDataModel>
) : RecyclerView.Adapter<FeedPostAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_item_recycleview_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mainContent = mList[position]

        holder.bind(mainContent)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding  image and text
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberOfLikesTextView: TextView = itemView.findViewById(R.id.tv_number_of_likes)
        private val mainContentTextView: TextView = itemView.findViewById(R.id.tv_text_main_content)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_main_content)
        private val usernameTextView: TextView = itemView.findViewById(R.id.tv_username)

        fun bind(mainContent: FeedPostDataModel) {
            numberOfLikesTextView.text = mainContent.numberOfLikes.toString()
            mainContentTextView.text = mainContent.mainContentText
            imageView.setImageResource(mainContent.imageResId)
            usernameTextView.text = mainContent.username

        }

    }
}