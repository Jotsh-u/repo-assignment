package com.myrepo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserReposDao {

    @Query("SELECT * FROM repo_table")
    suspend fun getAllRepoTableData(): List<UserRepo>

    @Query("SELECT * FROM repo_table WHERE full_Name LIKE :searchKeyword")
    suspend fun getAllSearchData(searchKeyword:String): List<UserRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepoData(repoData: UserRepo)

    @Query("DELETE FROM repo_table")
    fun deleteAllRepoData()
}