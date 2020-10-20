package com.amwa.noteapp.di.module

import android.content.Context
import com.amwa.noteapp.data.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    fun provideNoteDatabase(@ApplicationContext context: Context) = NoteDatabase.create(context)

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()
}