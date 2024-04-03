package com.example.notescompose.notes_feature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notescompose.NoteApp
import com.example.notescompose.dependency_injection.AppModule
import com.example.notescompose.notes_feature.data.data_source.NoteDao
import com.example.notescompose.notes_feature.data.repository.NoteRepositoryImplementation
import com.example.notescompose.notes_feature.domain.use_cases.NoteUseCases
import com.example.notescompose.notes_feature.presentation.add_edit_note.AddEditNoteScreen
import com.example.notescompose.notes_feature.presentation.add_edit_note.AddEditNoteViewModel
import com.example.notescompose.notes_feature.presentation.notes.NotesScreen
import com.example.notescompose.notes_feature.presentation.notes.NotesViewModel
import com.example.notescompose.notes_feature.presentation.util.Screen
import com.example.notescompose.ui.theme.NotesComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.internal.GeneratedComponent
import dagger.hilt.internal.GeneratedComponentManager
import dagger.hilt.internal.TestSingletonComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity(), GeneratedComponent, TestSingletonComponent {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteScreen.route,
                    ) {
                        composable(route = Screen.NoteScreen.route) {
                            NotesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(navController = navController, noteColor = color)
                        }
                    }
                }
            }
        }
    }
}
