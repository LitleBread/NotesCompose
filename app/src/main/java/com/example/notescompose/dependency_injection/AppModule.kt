package com.example.notescompose.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.notescompose.notes_feature.data.data_source.NoteDatabase
import com.example.notescompose.notes_feature.data.repository.NoteRepositoryImplementation
import com.example.notescompose.notes_feature.domain.repository.NoteRepository
import com.example.notescompose.notes_feature.domain.use_cases.DeleteNoteUseCase
import com.example.notescompose.notes_feature.domain.use_cases.GetNotesUseCase
import com.example.notescompose.notes_feature.domain.use_cases.AddNoteUseCase
import com.example.notescompose.notes_feature.domain.use_cases.GetNoteUseCase
import com.example.notescompose.notes_feature.domain.use_cases.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(app : Application) : NoteDatabase{
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase) : NoteRepository{
        return NoteRepositoryImplementation(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository) : NoteUseCases{
        return NoteUseCases(
            getNotes = GetNotesUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            addNote = AddNoteUseCase(repository),
            getNote = GetNoteUseCase(repository)
        )
    }
}