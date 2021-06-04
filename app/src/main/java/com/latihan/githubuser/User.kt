package com.latihan.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name: String?,
    val username: String?,
    val avatar: Int = 0,
    val following: Int = 0,
    val follower: Int = 0,
    val company: String?,
    val location: String?,
    val repository: Int = 0
): Parcelable