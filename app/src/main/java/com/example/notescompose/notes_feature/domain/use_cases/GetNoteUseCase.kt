package com.example.notescompose.notes_feature.domain.use_cases

import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.repository.NoteRepository

class GetNoteUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note?{
        return repository.getNoteById(id)
    }
}