package com.latihan.consumerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class Following : Fragment() {

    private val list = ArrayList<User>()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    companion object {
        private val EXTRA_NAME = "extra_name"

        fun newInstance(username: String?):Following{
            val fragment = Following()
            val bundle = Bundle()
            bundle.putString(EXTRA_NAME,username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_user)
        progressBar = view.findViewById(R.id.progressBar)
        val username = arguments?.getString(EXTRA_NAME)
        if (username != null) {
            getDataUser(username)
        }
    }

    private fun getDataUser(Username: String) {
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")
        val url = " https://api.github.com/users/$Username/following"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                progressBar.visibility = View.INVISIBLE
                val result = String(responseBody)
                try {
                    val responseObject = JSONArray(result)
                    for(i in 0 until responseObject.length()){
                        val getUser = responseObject.getJSONObject(i)
                        val usernames = getUser.getString("login")
                        val photos = getUser.getString("avatar_url")
                        val ids = getUser.getInt("id")
                        val Id = ids.toString()

                        val user = User(
                            username = usernames,
                            avatar = photos,
                            id = Id)
                        list.add(user)
                        recyclerView.layoutManager = LinearLayoutManager(activity)
                        val listUserAdapter = ListUserAdapter(list)
                        recyclerView.adapter = listUserAdapter
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
            }
        })

    }
}