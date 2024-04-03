package com.myrepo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// if fullname (HERE IT's Batter to use the and Unique Id field )exist in the same column data will update otherwise data will insert at new row
//@Entity(tableName = "repo_table", indices = [Index(value = ["fullName"], unique = true)])
@Entity(tableName = "repo_table")
data class UserRepo(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    @ColumnInfo(name = "forksCount", defaultValue = "0") val forksCount: Int?,
    @ColumnInfo(name ="full_Name", defaultValue = "") val fullName: String?,
    @ColumnInfo(name = "description", defaultValue = "") val description: String?,
    @ColumnInfo(name = "language", defaultValue = "") val language: String?,
    @ColumnInfo(name = "stargazers_count", defaultValue = "0") val stargazersCount: Int?,
    @ColumnInfo(name = "forks", defaultValue = "0") val forks: Int?,
    @ColumnInfo(name = "avtar_url", defaultValue = "") val avatarUrl: String?,
    @ColumnInfo(name = "contributorUrl", defaultValue = "") val contributorUrl: String?,
)
