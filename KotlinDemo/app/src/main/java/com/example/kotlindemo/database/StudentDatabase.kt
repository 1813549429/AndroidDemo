package com.example.kotlindemo.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {

    // 最终给用户的就是 DAO
    abstract fun getStudentDao() : StudentDao

    //单例
    companion object {
        private var INSTANCE : StudentDatabase? = null

        fun getDatabase(context: Context) : StudentDatabase? {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, StudentDatabase::class.java, "student_database.db")
                    .allowMainThreadQueries()  //允许主线程允运行
                    .build()
            }
            return INSTANCE
        }

        fun getDatabase() : StudentDatabase? = INSTANCE
    }


}