package com.latihan.githubuser.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.latihan.githubuser.Database.UserDatabase.UserColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private const val DATABASE_NAME = "dbfavuser"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " (${UserDatabase.UserColumns.USERNAME} TEXT PRIMARY KEY NOT NULL," +
                " ${UserDatabase.UserColumns.AVATAR_URL} TEXT NOT NULL," +
                " ${UserDatabase.UserColumns._ID} INTEGER NOT NULL," +
                " ${UserDatabase.UserColumns.COMPANY} TEXT NOT NULL,"+
                " ${UserDatabase.UserColumns.LOCATION} TEXT NOT NULL,"+
                " ${UserDatabase.UserColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_TABLE_USER)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        }
        onCreate(db)
    }

}