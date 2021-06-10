package com.latihan.consumerapp.Favorite

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.consumerapp.Database.UserDatabase
import com.latihan.consumerapp.Database.UserDatabase.UserColumns.Companion.CONTENT_URI
import com.latihan.consumerapp.DetailActivity
import com.latihan.consumerapp.ListUserAdapter
import com.latihan.consumerapp.User
import com.latihan.consumerapp.databinding.ActivityFavoriteBinding

    private val list = ArrayList<User>()

    private lateinit var binding: ActivityFavoriteBinding

    private lateinit var cursor: Cursor

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite Users"

        list.clear()

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
        binding.progressBar.visibility = View.INVISIBLE

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = FavoriteAdapter(list)
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