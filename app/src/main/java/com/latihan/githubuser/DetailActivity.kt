package com.latihan.githubuser

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.latihan.githubuser.Database.HelperUser
import com.latihan.githubuser.Database.MappingHelper
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.AVATAR_URL
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.COMPANY
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.CONTENT_URI
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.FAVORITE
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.LOCATION
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.USERNAME
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion._ID
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

    private lateinit var urlAvatar: String
    private var urlId: Int = 0


    private lateinit var floatActButton: FloatingActionButton

    lateinit var cursor: Cursor
    lateinit var uriWithId: Uri

    private lateinit var favorite: String

    private lateinit var getHelper: HelperUser
    private var statusFavorite = false

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar?.title = "User Profile"

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

        floatActButton = binding.favButton

        floatActButton.setOnClickListener{
            statusFavorite = !statusFavorite
            setStatusFavorite(statusFavorite)
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite){
                val dataUsername = currentUserUsername.text.toString().trim()
                val dataAvatar = urlAvatar
                val datacompany = currentUserCompany.text.toString().trim()
                val dataFavorite = "1"
                val dataLocation = currentUserLocation.text.toString().trim()
                val dataids = currentUserId.text.toString().trim()


                val values = ContentValues()
                values.put(USERNAME, dataUsername)
                values.put(AVATAR_URL, dataAvatar)
                values.put(COMPANY, datacompany)
                values.put(FAVORITE, dataFavorite)
                values.put(LOCATION, dataLocation)
                values.put(_ID, dataids)

                Toast.makeText(this, "Berhasil dimasukkan ke dalam Favorite", Toast.LENGTH_SHORT).show()
                floatActButton.setImageResource(R.drawable.ic_baseline_favorite_24)

                contentResolver?.insert(CONTENT_URI, values)
        }
        else{
                floatActButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                contentResolver?.delete(uriWithId, null, null)
                Toast.makeText(this,"Berhasil dihapus dari Favorite", Toast.LENGTH_SHORT).show()
            }
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

                    urlAvatar = photos
                    urlId = ids

                    Picasso.get().load(photos).into(currentUserImage)
                    currentUserName.text = name
                    currentUserUsername.text = usernames
                    currentUserFollowing.text = following
                    currentUserFollower.text = followers
                    currentUserCompany.text = company
                    currentUserLocation.text = location
                    currentUserId.text = Id

                    uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + urlId)
                    cursor = contentResolver?.query(uriWithId, null, null, null, null)!!
                    if (cursor.count > 0) {
                        var users = MappingHelper.mapCursorToObject(cursor)
                        favorite = users.favorite.toString()

                        if (favorite == "1"){
                            val checked: Int = R.drawable.ic_baseline_favorite_24
                            floatActButton.setImageResource(checked)
                            statusFavorite = true
                            cursor.close()
                        }
                        else{
                            statusFavorite = false
                        }
                    }

                } catch (e: Exception) {
                    Toast.makeText(this@DetailActivity, "Ada Error Le", Toast.LENGTH_SHORT).show()
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
