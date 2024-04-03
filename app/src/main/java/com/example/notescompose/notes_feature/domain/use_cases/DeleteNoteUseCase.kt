package com.example.notescompose.notes_feature.domain.use_cases

import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}