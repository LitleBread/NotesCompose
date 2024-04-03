package com.example.notescompose.notes_feature.presentation.add_edit_note

data class NoteTextFieldState(
    val text: String = "",
    val hintText: String = "",
    val isHintVisible: Boolean = true
)
