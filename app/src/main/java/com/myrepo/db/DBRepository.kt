package com.myrepo.db

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class DBRepository {

    companion object {
        private var appDatabase: AppDatabase? = null
        private var listRepoData: List<UserRepo>? = null
        private fun initialisationDb(context: Context): AppDatabase {
            return AppDatabase.getDatabase(context)
        }

        fun deleteAllRecord(context: Context) {
            appDatabase = initialisationDb(context)

            CoroutineScope(IO).launch {
                appDatabase?.repoDao()?.deleteAllRepoData()
            }
        }

        fun insertData(context: Context, repoData: UserRepo) {

            appDatabase = initialisationDb(context)
            CoroutineScope(IO).launch {
                appDatabase?.repoDao()?.insertRepoData(repoData)
            }
        }

        suspend fun getRepoData(context: Context): List<UserRepo>? {
            appDatabase = initialisationDb(context)
            listRepoData = appDatabase?.repoDao()?.getAllRepoTableData()
            return listRepoData
        }
        suspend fun getAllSearchData(context: Context,searchedKeyword:String): List<UserRepo>? {
            appDatabase = initialisationDb(context)
            listRepoData = appDatabase?.repoDao()?.getAllSearchData(searchedKeyword)
            return listRepoData
        }
    }
}