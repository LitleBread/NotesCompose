package com.example.notescompose.notes_feature.presentation.util

sealed class Screen(val route: String){
    data object NoteScreen: Screen("note_screen")
    data object AddEditNoteScreen: Screen("add_edit_note_screen")
}