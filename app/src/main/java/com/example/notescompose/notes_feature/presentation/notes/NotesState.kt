package com.example.notescompose.notes_feature.presentation.notes

import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.util.NoteOrder
import com.example.notescompose.notes_feature.domain.util.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
