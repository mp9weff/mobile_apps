package com.example.app.di

import android.app.Application
import com.example.app.data.AppDatabase
import com.example.app.data.StudyRepository
import com.example.app.ui.SubjectDetailViewModel
import com.example.app.ui.SubjectListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()) }
    single { get<AppDatabase>().subjectDao() }
    single { get<AppDatabase>().labWorkDao() }
}

val repositoryModule = module {
    single { StudyRepository(get(), get()) }
}

val viewModelModule = module {
    viewModel { SubjectListViewModel(get()) }
    viewModel { (subjectId: Long) -> SubjectDetailViewModel(get(), subjectId) }
}


