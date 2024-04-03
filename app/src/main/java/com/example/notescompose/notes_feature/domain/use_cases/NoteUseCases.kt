package com.example.notescompose.notes_feature.domain.use_cases

data class NoteUseCases(
    val getNotes : GetNotesUseCase,
    val deleteNote : DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val getNote : GetNoteUseCase
)
