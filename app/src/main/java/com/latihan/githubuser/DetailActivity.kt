package com.latihan.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.latihan.githubuser.databinding.ActivityDetailBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }

    private lateinit var currentUserImage: ImageView
    private lateinit var currentUserName: TextView
    private lateinit var currentUserUsername: TextView
    private lateinit var currentUserFollower: TextView
    private lateinit var currentUserFollowing: TextView
    private lateinit var currentUserCompany: TextView
    private lateinit var currentUserLocation: TextView
    private lateinit var currentUserId: TextView

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "User Profile"

        val username = intent.getStringExtra(EXTRA_PERSON)
        username.toString()

        if (username != null) {
            getDataUser(username)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        if (username != null) {
            sectionsPagerAdapter.username = username
        }

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun getDataUser(Username: String) {
        currentUserName = findViewById(R.id.curr_user_name)
        currentUserUsername = findViewById(R.id.curr_user_username)
        currentUserFollowing = findViewById(R.id.user_following)
        currentUserFollower = findViewById(R.id.user_followers)
        currentUserCompany = findViewById(R.id.user_company)
        currentUserLocation = findViewById(R.id.user_location)
        currentUserId = findViewById(R.id.user_id)
        currentUserImage = findViewById(R.id.user_photo)

        binding.progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = "https://api.github.com/users/$Username"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                binding.progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)

                    val name = responseObject.getString("name")
                    val usernames = responseObject.getString("login")
                    val photos = responseObject.getString("avatar_url")
                    val following = responseObject.getString("following")
                    val followers = responseObject.getString("followers")
                    val company = responseObject.getString("company")
                    val location = responseObject.getString("location")
                    val ids = responseObject.getInt("id")
                    val Id = ids.toString()

                    Picasso.get().load(photos).into(currentUserImage)
                    currentUserName.text = name
                    currentUserUsername.text = usernames
                    currentUserFollowing.text = following
                    currentUserFollower.text = followers
                    currentUserCompany.text = company
                    currentUserLocation.text = location
                    currentUserId.text = Id

                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@DetailActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

}