package com.example.notescompose.notes_feature.domain.use_cases

import com.example.notescompose.notes_feature.domain.model.InvalidNoteException
import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.insertNote(note)
    }
}