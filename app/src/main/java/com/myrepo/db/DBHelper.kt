package com.myrepo.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "TRENDING_REPO"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "REPO_TABLE"
        const val ID_COL = "ID_COL"
        const val FORKS_COUNT_COL = "forksCount"
        const val AVTAR_URL_COL = "avtar_url"
        const val FULL_NAME_COL = "full_Name"
        const val DESCRIPTION_COL = "description"
        const val LANGUAGE_COL = "language"
        const val STARGAZERS_COUNT_COL = "stargazers_Count"
        const val FORKS_COL = "forks"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                FORKS_COUNT_COL + " INTEGER," +
                AVTAR_URL_COL + " TEXT," +
                FULL_NAME_COL + " TEXT," +
                DESCRIPTION_COL + " TEXT," +
                LANGUAGE_COL + " TEXT," +
                STARGAZERS_COUNT_COL + " TEXT," +
                FORKS_COL + " INTEGER" + ")")

        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertRepoData(data: RepoData) {
        val values = ContentValues()
        values.put(FORKS_COUNT_COL, data.forksCount)
        values.put(AVTAR_URL_COL, data.avatarUrl)
        values.put(FULL_NAME_COL, data.fullName)
        values.put(DESCRIPTION_COL, data.description)
        values.put(LANGUAGE_COL, data.language)
        values.put(STARGAZERS_COUNT_COL, data.stargazersCount)
        values.put(FORKS_COL, data.forks)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getAllRepoTableData(): ArrayList<RepoData> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val list: ArrayList<RepoData> = arrayListOf()
        if (cursor?.moveToFirst() == true) {
            do {
                list.add(
                    RepoData(
                        cursor.getInt(cursor.getColumnIndex(FORKS_COUNT_COL)),
                        cursor.getString(cursor.getColumnIndex(FULL_NAME_COL)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION_COL)),
                        cursor.getString(cursor.getColumnIndex(LANGUAGE_COL)),
                        cursor.getInt(cursor.getColumnIndex(STARGAZERS_COUNT_COL)),
                        cursor.getInt(cursor.getColumnIndex(FORKS_COL)),
                        cursor.getString(cursor.getColumnIndex(AVTAR_URL_COL)),
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return list
    }

}

data class RepoData(
    val forksCount: Int? = 0,
    val fullName: String? = "",
    val description: String? = "",
    val language: String? = "",
    val stargazersCount: Int? = 0,
    val forks: Int? = 0,
    val avatarUrl: String? = "",
    val contributorUrl: String? = "",
    )