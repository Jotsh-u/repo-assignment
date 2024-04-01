package com.myrepo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserReposDao {

    @Query("SELECT * FROM repo_table")
    suspend fun getAllRepoTableData(): List<UserRepo>

    @Insert
    fun insertRepoData(repoData: UserRepo)

    @Query("DELETE FROM repo_table")
    fun deleteAllRepoData()
}