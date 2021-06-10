package com.latihan.githubuser.Favorite

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.githubuser.Database.UserDatabase
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.CONTENT_URI
import com.latihan.githubuser.DetailActivity
import com.latihan.githubuser.ListUserAdapter
import com.latihan.githubuser.User
import com.latihan.githubuser.databinding.ActivityFavoriteBinding

    private val list = ArrayList<User>()

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var cursor: Cursor

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFavorite()
    }

    private fun getDataFavorite() {
        binding.progressBar.visibility = View.VISIBLE
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null)!!
        if (cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val user = User(
                    username = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserColumns.USERNAME)),
                    avatar = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserColumns.AVATAR_URL)),
                    id = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabase.UserColumns._ID))
                )
                list.add(user)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        showRecyclerList()

    }

    private fun showRecyclerList() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val moveWithObjectIntent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_PERSON,data.username)
                startActivity(moveWithObjectIntent)
            }
        })
    }
}