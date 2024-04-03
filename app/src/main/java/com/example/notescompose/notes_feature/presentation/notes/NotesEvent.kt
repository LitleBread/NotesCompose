package com.example.notescompose.notes_feature.presentation.notes

import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class Delete(val note: Note) : NotesEvent()
    data object RestoreNote : NotesEvent()
    data object ToggleOrderSection : NotesEvent()
}