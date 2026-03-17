package com.example.cntrgssr.presentation.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cntrgssr.core.presentation.HeartImage
import com.example.cntrgssr.theme.AppTheme

@Composable
fun ResultsScreen(
    uiState: Results.UiState,
    onUserEvent: (Results.UserEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = when {
                uiState.isGaveUp -> "You gave up!"
                uiState.heartNumber == 3 -> "Perfect! You won!"
                uiState.heartNumber == 2 -> "Great! You won!"
                uiState.heartNumber == 1 -> "Good! You won!"
                else -> "Game Over!"
            },
            style = MaterialTheme.typography.headlineLarge,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        ) {
            if (!uiState.isGaveUp) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    HeartImage(uiState.heartNumber > 2)
                    HeartImage(uiState.heartNumber > 1)
                    HeartImage(uiState.heartNumber > 0)
                }
            }

            ElevatedCard(
                modifier = Modifier.padding(horizontal = 90.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    ResultRow(
                        label = "Correct answer:",
                        value = uiState.countryName,
                    )
                    if (!uiState.isGaveUp && uiState.heartNumber > 0) {
                        ResultRow(
                            label = "Your result:",
                            value = "${100 + uiState.heartPoints + uiState.hintNumber} pts",
                        )

                        ResultRow(
                            label = "${3 - uiState.heartNumber} lives used:",
                            value = "${uiState.heartPoints} pts",
                            valueColor = if (uiState.heartPoints < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onTertiaryFixedVariant,
                        )

                        ResultRow(
                            label = "${uiState.hintNumber} hints used:",
                            value = "${uiState.hintPoints} pts",
                            valueColor = if (uiState.hintNumber > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onTertiaryFixedVariant,
                        )
                    } else {
                        ResultRow(
                            label = "Your result:",
                            value = "0 pts",
                            valueColor = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 100.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Play Again") },
                    onClick = { onUserEvent(Results.UserEvent.OnPlayAgainButtonClicked) },
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Exit") },
                    onClick = { onUserEvent(Results.UserEvent.OnExitButtonClicked) },
                )
            }
        }
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ResultText(label)
        ResultText(value, color = valueColor)
    }
}

@Composable
private fun ResultText(text: String, color: Color = MaterialTheme.colorScheme.onSurface) {
    Text(text, style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp), color = color)
}

@Preview(showBackground = true)
@Composable
fun ResultsScreenPreview() {
    AppTheme {
        ResultsScreen(
            uiState = Results.UiState(
                countryName = "Italy",
            )
        )
    }
}