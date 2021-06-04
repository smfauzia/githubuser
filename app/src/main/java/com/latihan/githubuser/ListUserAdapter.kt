package com.latihan.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListUserAdapter(private val listUser:ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback



    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_user_username)
        var tvUserCompany: TextView = itemView.findViewById(R.id.tv_user_company)
        var imgAvatar: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    fun setOnItemClickCallback(onItemClickCallback: Any) {
        this.onItemClickCallback = onItemClickCallback as OnItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        /*val user = listUser[position] //ngamvil data dsiini. PANGGIL API KESINI??

        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(65,65))
            .into(holder.imgAvatar)

        holder.tvName.text = user.name
        holder.tvUsername.text = user.username
        holder.tvUserCompany.text = "company: ${user.company}"

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context
            val userDetailActivity = Intent(context, DetailActivity::class.java)

            val person = User(user.name, user.username, user.avatar, user.following,user.follower,user.company,user.location, user.repository)
            userDetailActivity.putExtra(DetailActivity.EXTRA_PERSON, person)
            context.startActivity(userDetailActivity)
        }*/
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}