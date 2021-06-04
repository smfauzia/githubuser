package com.latihan.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.latihan.githubuser.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    //private lateinit var rvUser: RecyclerView
    //private val list: ArrayList<User> = arrayListOf()

    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        //rvUser = findViewById(R.id.rv_user)
       // rvUser.setHasFixedSize(true)

        //list.addAll(UserData.listData)
        //showRecyclerList()

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Kamu memilih " + user.name, Toast.LENGTH_SHORT).show()
    }
}