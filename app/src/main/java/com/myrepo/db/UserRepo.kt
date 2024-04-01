package com.myrepo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class UserRepo(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    @ColumnInfo(name = "forksCount") val forksCount: Int?,
    @ColumnInfo(name ="full_Name") val fullName: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "language") val language: String?,
    @ColumnInfo(name = "stargazers_count") val stargazersCount: Int?,
    @ColumnInfo(name = "forks") val forks: Int?,
    @ColumnInfo(name = "avtar_url") val avatarUrl: String?,
    @ColumnInfo(name = "contributorUrl") val contributorUrl: String?,
)
