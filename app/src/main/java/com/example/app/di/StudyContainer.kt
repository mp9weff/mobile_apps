package com.example.app.di

import android.content.Context
import com.example.app.data.AppDatabase
import com.example.app.data.StudyRepository

class StudyContainer(context: Context) {
    private val db = AppDatabase.getInstance(context)
    val repository: StudyRepository = StudyRepository(db.subjectDao(), db.labWorkDao())
}


