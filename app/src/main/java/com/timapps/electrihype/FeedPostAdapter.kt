package com.timapps.electrihype

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.squareup.picasso.Picasso
class FeedPostAdapter (
    private var mList: MutableList<FeedPostDataModel>
) : RecyclerView.Adapter<FeedPostAdapter.ViewHolder>() {

    // Update the data list
    fun setData(posts: List<FeedPostDataModel>) {
        mList.clear()
        mList.addAll(posts)
        notifyDataSetChanged()
    }

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

        private var imageUri: Uri? = null

        private val numberOfLikesTextView: TextView = itemView.findViewById(R.id.tv_number_of_likes)
        private val mainContentTextView: TextView = itemView.findViewById(R.id.tv_text_main_content)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_image_main_content)
        private val usernameTextView: TextView = itemView.findViewById(R.id.tv_username)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_date)



        // Bind the data to the views in the ViewHolder
        fun bind(mainContent: FeedPostDataModel) {
            numberOfLikesTextView.text = mainContent.numberOfLikes.toString()
            mainContentTextView.text = mainContent.mainContentText
            usernameTextView.text = mainContent.username
            dateTextView.text = formatDate(mainContent.date).toString()
            imageUri = mainContent.imageResId



            // Set the visibility of the ImageView based on the imageResId
            if (imageUri != null) {
                imageView.visibility = View.VISIBLE

                // Load the image using Picasso
                Picasso.get().load(mainContent.imageResId).into(imageView)

                imageView.visibility = View.VISIBLE
                imageView.setImageURI(imageUri)
            } else {
                imageView.visibility = View.GONE
            }
        }


        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val selectedItem = mList[mList.size - position - 1]
                    // Handle item selection here, e.g., start a new activity with the selected item text
                    val intent = Intent(itemView.context, PostDetailViewActivity::class.java)
                    intent.putExtra("selectedItemText", selectedItem.mainContentText)
                    intent.putExtra("selectedItemID", selectedItem.id)
                    intent.putExtra("selectedItemUsername", selectedItem.username)
                    intent.putExtra("selectedItemLikes", selectedItem.numberOfLikes)
                    intent.putExtra("selectedItemImageUri", imageUri?.toString())
                    intent.putExtra("selectedItemDate", selectedItem.date)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

    fun addData(post: FeedPostDataModel) {
        mList.add(post)
        notifyDataSetChanged()
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("MMMM d h:mm a", Locale.getDefault())
        return dateFormat.format(date)
    }
}