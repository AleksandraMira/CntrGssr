package com.example.cntrgssr.presentation.game

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.cntrgssr.R
import com.example.cntrgssr.core.presentation.HeartImage
import com.example.cntrgssr.theme.AppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onUserEvent: (Game.UserEvent) -> Unit = {},
    uiState: Game.UiState,
) {
    val activity = LocalContext.current as Activity

    SideEffect {
        WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        ).hide(WindowInsetsCompat.Type.statusBars())
    }

    val snackbarHostState = remember { SnackbarHostState() }
    // TODO: Add navigate back handling
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        snackbarData = data,
                        contentColor = MaterialTheme.colorScheme.onError,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
                ) {
                    HeartImage(uiState.heartNumber > 2)
                    HeartImage(uiState.heartNumber > 1)
                    HeartImage(uiState.heartNumber > 0)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = stringResource(R.string.guide_screen_title),
                        style = MaterialTheme.typography.headlineLarge
                    )
                    ExposedDropdownMenuBox(
                        expanded = false,
                        onExpandedChange = { /*TODO*/ },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = "Select an option",
                            onValueChange = {},
                            label = { Text("Hint", style = MaterialTheme.typography.labelLarge) },
                            readOnly = true,
                            singleLine = true,
                            // TODO: Add trailing icon for dropdown (see ExposedDropdownMenuBox docs for example)
                            trailingIcon = {
                                Icon(Icons.Default.KeyboardArrowDown, "")
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = false,
                            onDismissRequest = { /*TODO remember boolean value */ },
                        ) {
                            DropdownMenuItem(
                                // TODO: Add modifier background for chosen option
                                text = { Text("Option 1") },
                                onClick = { /*TODO*/ }
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        content = { Text("Show Hint") },
                        onClick = {},
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = "1.XYZ \n2.ABC \n3.DEF",
                        onValueChange = { /*TODO*/ },
                        label = { Text("Hint Log", style = MaterialTheme.typography.labelLarge) },
                        singleLine = false,
                        readOnly = true,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.answer,
                        onValueChange = { onUserEvent(Game.UserEvent.OnAnswerChange(it)) },
                        label = {
                            Text(
                                stringResource(R.string.game_screen_your_guess),
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        singleLine = true,
                        trailingIcon = {
                            if (uiState.answer.isNotEmpty())
                                IconButton(onClick = { onUserEvent(Game.UserEvent.OnAnswerChange("")) }) {
                                    Icon(Icons.Default.Clear, "")
                                }
                        }
                    )
                    Column(
                        modifier = Modifier.padding(top = 6.dp),
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(stringResource(R.string.game_screen_submit_button)) },
                            onClick = { onUserEvent(Game.UserEvent.OnSubmitAnswer) },
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text("Give Up") },
                            onClick = {},
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onError,
                                disabledContainerColor = MaterialTheme.colorScheme.onErrorContainer,
                                disabledContentColor = MaterialTheme.colorScheme.onErrorContainer,
                            )
                        )
                    }
                }
            }

            LaunchedEffect(uiState.snackbarMessage) {
                uiState.snackbarMessage?.let { message ->
                    snackbarHostState.showSnackbar(message)
                    onUserEvent(Game.UserEvent.OnSnackbarShown)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        GameScreen(uiState = Game.UiState())
    }
}