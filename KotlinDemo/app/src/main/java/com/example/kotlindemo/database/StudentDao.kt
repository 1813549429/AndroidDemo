package com.example.kotlindemo.database

import androidx.room.*

@Dao
interface StudentDao {

    //vararg  可变参数  对应java中的   (String... student)
    @Insert
    abstract fun insertStudents(vararg student: Student)

    @Delete
    abstract fun deleteStudents(vararg student: Student)

    @Update
    abstract fun updateStudents(vararg student: Student)

    @Query("DELETE FROM student")
    abstract fun deleteAllStudents()

    @Query("SELECT * FROM student")
    abstract fun queryAllStudents() : List<Student>
}