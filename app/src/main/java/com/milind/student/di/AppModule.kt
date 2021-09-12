package com.milind.student.di

import android.app.Application
import com.milind.student.storage.datastore.UIModeDataStore
import com.milind.student.storage.db.StudentDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun providePreferenceManager(application: Application): UIModeDataStore {
        return UIModeDataStore(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideNoteDatabase(application: Application): StudentDatabase {
        return StudentDatabase.invoke(application.applicationContext)
    }

}