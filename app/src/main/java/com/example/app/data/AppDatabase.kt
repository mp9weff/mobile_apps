package com.example.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Subject::class, LabWork::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun labWorkDao(): LabWorkDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: build(context).also { INSTANCE = it }
        }

        private fun build(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "study_tracker.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seeding can be performed after first access if needed
                    }
                })
                .build()
        }

        private suspend fun seed(db: AppDatabase) {
            val subjects = listOf(
                Subject(name = "Мобільна розробка"),
                Subject(name = "Алгоритми та структури даних"),
                Subject(name = "Бази даних")
            )
            val subjectIds = subjects.map { db.subjectDao().insert(it) }

            subjectIds.forEachIndexed { index, subjectId ->
                val labs = listOf(
                    LabWork(subjectId = subjectId, title = "Лабораторна 1"),
                    LabWork(subjectId = subjectId, title = "Лабораторна 2"),
                    LabWork(subjectId = subjectId, title = "Лабораторна 3")
                )
                labs.forEach { db.labWorkDao().insert(it) }
            }
        }
    }
}


