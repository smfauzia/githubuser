package com.latihan.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name: String? = null,
    val username: String?= null,
    val avatar: String?= null,
    val following: String?= null,
    val follower: String?= null,
    val company: String?= null,
    val location: String?= null,
    val repository: String?= null,
    val id: String?= null,
    val favorite: String? =null
): Parcelable