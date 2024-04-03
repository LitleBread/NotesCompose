package com.example.notescompose.notes_feature.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescompose.notes_feature.domain.model.InvalidNoteException
import com.example.notescompose.notes_feature.domain.model.Note
import com.example.notescompose.notes_feature.domain.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel()  {
    private val _noteTitle = mutableStateOf<NoteTextFieldState>(NoteTextFieldState(hintText = "Enter title..."))
    val title : State<NoteTextFieldState> = _noteTitle;

    private val _noteContent = mutableStateOf<NoteTextFieldState>(NoteTextFieldState(hintText = "Enter some content..."))
    val noteContent : State<NoteTextFieldState> = _noteContent;

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor;

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init{
        savedStateHandle.get<Int?>("noteId")?.let { noteId ->
            if(noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {
                        currentNoteId = noteId
                        _noteTitle.value = _noteTitle.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                        _noteColor.intValue = it.color
                    }
                }
            }

        }
    }

    fun onEvent(event: AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitle -> _noteTitle.value = _noteTitle.value.copy(
                text = event.value
            )
            is AddEditNoteEvent.ChangeFocusTitle -> _noteTitle.value = _noteTitle.value.copy(isHintVisible = !event.focusState.isFocused && _noteTitle.value.text.isBlank())
            is AddEditNoteEvent.EnteredContent -> _noteContent.value = _noteContent.value.copy(
                text = event.value
            )
            is AddEditNoteEvent.ChangeFocusContent -> _noteContent.value = _noteContent.value.copy(isHintVisible = !event.focusState.isFocused && _noteContent.value.text.isBlank())
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(Note(
                            title = _noteTitle.value.text,
                            content = _noteContent.value.text,
                            color = _noteColor.intValue,
                            timestamp = System.currentTimeMillis(),
                            id = currentNoteId
                        ))
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException){
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = "Couldn't save note"))
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveNote : UiEvent()
    }
}