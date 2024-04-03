package com.example.notescompose.notes_feature.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notescompose.ui.theme.*

@Entity
data class Note(
    val title : String,
    val content : String,
    val timestamp : Long,
    val color: Int,
    @PrimaryKey val id : Int? = null
){
    companion object{
        val noteColors = listOf(Red, Blue, Yellow, Purple, Green)
    }
}

class InvalidNoteException(message: String) : Exception(message){}
