package com.latihan.githubuser

import android.app.Person
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var currentUserImage: ImageView
    private lateinit var currentUserName: TextView
    private lateinit var currentUserUsername: TextView
    private lateinit var currentUserFollower: TextView
    private lateinit var currentUserFollowing: TextView
    private lateinit var currentUserCompany: TextView
    private lateinit var currentUserLocation: TextView
    private lateinit var currentUserRepository: TextView
    private lateinit var btnMyRepository: Button

    companion object{
        const val EXTRA_PERSON = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionBar = supportActionBar
        actionBar!!.title = "User Profile"

        val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as User
        val tName = person.name.toString()
        val tUsername = person.username.toString()
        val tFollowing = person.following.toString()
        val tFollowers = person.follower.toString()
        val tCompany = person.company.toString()
        val tLocation = person.location.toString()
        val tRepository = person.repository.toString()
        val imgUserAvatar = person.avatar
        currentUserImage = findViewById(R.id.user_photo)
//        currentUserImage.setImageResource(imgUserAvatar)

        currentUserName = findViewById(R.id.curr_user_name)
        currentUserUsername = findViewById(R.id.curr_user_username)
        currentUserFollowing = findViewById(R.id.user_following)
        currentUserFollower = findViewById(R.id.user_followers)
        currentUserCompany = findViewById(R.id.user_company)
        currentUserLocation = findViewById(R.id.user_location)
        currentUserRepository = findViewById(R.id.user_repository)

        val currUserName = "$tName"
        val currUserUsername = "$tUsername"
        val currUserFollowing = "$tFollowing"
        val currUserFollowers = "$tFollowers"
        val currUserCompany = "Company: $tCompany"
        val currUserLocation = "Location: $tLocation"
        val currUserRepository = "Repository: $tRepository"

        currentUserName.text = currUserName
        currentUserUsername.text = currUserUsername
        currentUserFollowing.text = currUserFollowing
        currentUserFollower.text = currUserFollowers
        currentUserCompany.text = currUserCompany
        currentUserLocation.text = currUserLocation
        currentUserRepository.text = currUserRepository

        btnMyRepository = findViewById(R.id.btn_myRepository)
        btnMyRepository.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_myRepository ->{
                val person = intent.getParcelableExtra<Person>(EXTRA_PERSON) as User
                val myRep = person.username.toString()
                val uri: Uri = Uri.parse("http://www.github.com/$myRep")
                val intentCari = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intentCari)
            }
        }
    }
}