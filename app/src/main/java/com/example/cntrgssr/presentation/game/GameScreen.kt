package com.example.cntrgssr.presentation.game

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    var isDropdownExpanded by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val controller = WindowInsetsControllerCompat(
            activity.window,
            activity.window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.statusBars())

        onDispose {
            controller.show(WindowInsetsCompat.Type.statusBars())
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    // TODO: Add navigate back handling
    Scaffold(
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
                        expanded = isDropdownExpanded,
                        onExpandedChange = { isDropdownExpanded = it },
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true),
                            value = when{
                                uiState.selectedHint != null -> stringResource(uiState.selectedHint.resId)
                                uiState.availableHintOptions.isEmpty() -> stringResource(R.string.game_screen_no_hints_available)
                                else -> stringResource(R.string.game_screen_select_a_hint)
                            },
                            onValueChange = {},
                            label = { Text(stringResource(R.string.game_screen_hint_label), style = MaterialTheme.typography.labelLarge) },
                            readOnly = true,
                            singleLine = true,
                            trailingIcon = {
                                Icon(
                                    if (isDropdownExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                    ""
                                )
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        ) {
                            uiState.availableHintOptions.forEach {
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .apply {
                                            if (uiState.selectedHint == it) {
                                                background(
                                                    MaterialTheme.colorScheme.inverseOnSurface,
                                                )
                                            }
                                        },
                                    text = { Text(stringResource(it.resId)) },
                                    onClick = {
                                        if (it != uiState.selectedHint) {
                                            onUserEvent(Game.UserEvent.OnHintOptionSelected(it))
                                        }
                                        isDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        content = { Text(stringResource(R.string.game_screen_hint_button)) },
                        onClick = { onUserEvent(Game.UserEvent.OnHintButtonClicked) },
                    )
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = uiState.hintLog.entries.mapIndexed { index, entry ->
                            stringResource(
                                R.string.game_screen_hint_entry,
                                index + 1,
                                stringResource(entry.key.resId),
                                entry.value
                            )
                        }.joinToString("\n"),
                        onValueChange = {},
                        label = { Text(stringResource(R.string.game_screen_hint_log), style = MaterialTheme.typography.labelLarge) },
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
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.onTertiaryFixedVariant,
                                contentColor = MaterialTheme.colorScheme.onTertiary,
                                disabledContainerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                disabledContentColor = MaterialTheme.colorScheme.onBackground,
                            )
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            content = { Text(stringResource(R.string.game_screen_give_up_button)) },
                            onClick = { onUserEvent(Game.UserEvent.OnGiveUpButtonClicked) },
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

            AnimatedVisibility(
                visible = uiState.isLoading,
                enter = fadeIn(animationSpec = tween(150)),
                exit = fadeOut(animationSpec = tween(150))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.scrim.copy(alpha = 0.4f)
                        )
                        .clickable(enabled = true, onClick = {}),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            LaunchedEffect(uiState.snackbarMessage) {
                uiState.snackbarMessage?.let { message ->
                    snackbarHostState.showSnackbar(message)
                    onUserEvent(Game.UserEvent.OnSnackbarShown)
                }
            }

            if (uiState.isGiveUpDialogVisible) {
                BasicAlertDialog(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),
                    onDismissRequest = { onUserEvent(Game.UserEvent.OnGiveUpDialogDismiss) },
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            text = stringResource(R.string.game_screen_give_up_dialog_title),
                        )
                        Text(
                            text = stringResource(R.string.game_screen_give_up_dialog_description),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Start,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(9.dp, Alignment.End),
                        ) {
                            TextButton(
                                content = { Text(stringResource(R.string.game_screen_no_button)) },
                                onClick = { onUserEvent(Game.UserEvent.OnGiveUpDialogDismiss) },
                            )
                            TextButton(
                                content = { Text(stringResource(R.string.game_screen_yes_button)) },
                                onClick = { onUserEvent(Game.UserEvent.OnGiveUpDialogConfirm) },
                            )
                        }
                    }
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