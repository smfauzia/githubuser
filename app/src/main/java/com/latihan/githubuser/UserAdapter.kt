package com.latihan.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<UserItems>()

    fun setData(items: ArrayList<UserItems>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val mView = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserItemsBinding.bind(itemView)
        fun bind(userItems: UserItems) {
            with(itemView){
                binding.tvName.text = userItems.name
                binding.tvUsername.text = userItems.username
                binding.tvCompany.text = userItems.company
            }

        }

    }
}