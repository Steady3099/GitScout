package com.example.gitscout.ui.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitscout.R
import com.example.gitscout.data.model.User
import com.example.gitscout.ui.fragment.UserProfileFragment

class ConnectionsAdapter(private val userList: List<User>,private val context: Context,private val clickListener: ItemClickListener) : RecyclerView.Adapter<ConnectionsAdapter.ConnectionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.connections_card, parent, false)
        return ConnectionsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(userList[position].login)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ConnectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTv)
        private val avatarImg: ImageView = itemView.findViewById(R.id.avatarImg)

        fun bind(user: User) {
            userNameTextView.text = user.login

            Glide.with(context)
                .load(user.avatarUrl)
                .centerCrop()
                .into(avatarImg)
        }
    }
}
