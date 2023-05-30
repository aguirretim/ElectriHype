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
    private var mList: MutableList<FeedPostDataModel>
) : RecyclerView.Adapter<FeedPostAdapter.ViewHolder>() {

    // Update the data list
    fun updateData(newList: List<FeedPostDataModel>) {
        mList.clear()
        mList.addAll(newList)
    }


    // Create new views for the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the card_view_design view that will hold each list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_feed_item_recycleview_design, parent, false)

        return ViewHolder(view)


    }

    // Bind the data to the views in each list item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mainContent = mList[mList.size - position - 1]

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



        // Bind the data to the views in the ViewHolder
        fun bind(mainContent: FeedPostDataModel) {
            numberOfLikesTextView.text = mainContent.numberOfLikes.toString()
            mainContentTextView.text = mainContent.mainContentText

            // Set the visibility of the ImageView based on the imageResId
            if (mainContent.imageResId != 0) {
                imageView.visibility = View.VISIBLE
                imageView.setImageResource(mainContent.imageResId)
            } else {
                imageView.visibility = View.GONE
            }
            imageView.setImageResource(mainContent.imageResId)
            usernameTextView.text = mainContent.username

        }

    }

    fun addData(post: FeedPostDataModel) {
        mList.add(post)
    }


}