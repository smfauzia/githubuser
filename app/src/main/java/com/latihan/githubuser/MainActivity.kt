package com.latihan.githubuser


import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.latihan.githubuser.Favorite.FavoriteActivity
import com.latihan.githubuser.alarmreminder.ReminderActivity
import com.latihan.githubuser.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class MainActivity : AppCompatActivity() {


    private val list = ArrayList<User>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.setHasFixedSize(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataUser()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isEmpty()) {
                    return true
                } else {
                    list.clear()
                    getDataUser(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.setting ->{
                val i = Intent(this, ReminderActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun getDataUser(Username: String = "sidiq") {
        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/search/users?q=$Username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val searchUser =  responseObject.getJSONArray("items")

                    for(i in 0 until searchUser.length()){
                        val getUser = searchUser.getJSONObject(i)
                        val usernames = getUser.getString("login")
                        val photos = getUser.getString("avatar_url")
                        val ids = getUser.getInt("id")
                        val Id = ids.toString()

                        val user = User(
                                username = usernames,
                                avatar = photos,
                                id = Id)

                        list.add(user)
                        showRecyclerList()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun showRecyclerList() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(list)
        binding.rvUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_PERSON,data.username)
                startActivity(intent)
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        Toast.makeText(this, "Kamu memilih " + user.username, Toast.LENGTH_SHORT).show()
    }
}