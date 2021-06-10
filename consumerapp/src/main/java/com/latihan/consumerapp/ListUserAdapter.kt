package com.latihan.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.latihan.consumerapp.databinding.ItemRowUserBinding

class ListUserAdapter(private val listUser:ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ListViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(user.avatar)
                        .apply(RequestOptions().override(55, 55))
                        .into(imgItemPhoto)
                tvUserUsername.text = user.username
                tvUserId.text = user.id

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size


    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}