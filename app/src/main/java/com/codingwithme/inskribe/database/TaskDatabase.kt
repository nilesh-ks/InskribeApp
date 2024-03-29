package com.codingwithme.inskribe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codingwithme.inskribe.dao.TaskDao
import com.codingwithme.inskribe.entities.Task
import com.codingwithme.inskribe.util.Converter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Task::class], version = 1,exportSchema = false)
@TypeConverters(Converter::class)
abstract class TaskRoomDatabase: RoomDatabase() {
    companion object {
        val NUMBER_OF_THREADS: Int = 4; //For background thread
        val DATABASE_NAME: String = "todoister_database"
        @Volatile private var INSTANCE : TaskRoomDatabase? = null //volatile cannot be used with val
        val databaseWriterExecutor: ExecutorService = Executors.newFixedThreadPool(
            NUMBER_OF_THREADS)




        fun getDatabase(context: Context): TaskRoomDatabase? {
            if(INSTANCE==null)
            {
                synchronized(TaskRoomDatabase::class.java)
                {
                    if (INSTANCE==null)
                    {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TaskRoomDatabase::class.java, DATABASE_NAME)
                            .addCallback(object : RoomDatabase.Callback(){
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    databaseWriterExecutor.execute {
                                        //invoke Dao and write
                                        INSTANCE?.taskDao()?.deleteAll()

                                        //writing to the table
                                    }
                                }
                            }) //Manages to clear the database. Can also add things to
                            // the database just after its creation
                            .build()
                    }
                }
            }
            return INSTANCE
        }

    }
    public abstract fun taskDao(): TaskDao;

}