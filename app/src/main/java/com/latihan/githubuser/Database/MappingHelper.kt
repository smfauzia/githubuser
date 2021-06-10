package com.latihan.githubuser.Database

import android.database.Cursor
import com.latihan.githubuser.User

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        notesCursor?.apply {
            while (moveToNext()) {
                val usernames = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.USERNAME))
                val companys = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.COMPANY))
                val avatars = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.AVATAR_URL))
                val locatios = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.LOCATION))
                val ids = getString(getColumnIndexOrThrow(UserDatabase.UserColumns._ID))
                val favorites = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.FAVORITE))
                usersList.add(User(username = usernames, company = companys, avatar =  avatars, location = locatios, id = ids, favorite = favorites))
            }
        }
        return usersList
    }

    fun mapCursorToObject(notesCursor: Cursor?): User {
        var user = User()
        notesCursor?.apply {
            moveToFirst()
            val fav = getString(getColumnIndexOrThrow(UserDatabase.UserColumns.FAVORITE))
            user = User(favorite = fav)
        }
        return user
    }
}