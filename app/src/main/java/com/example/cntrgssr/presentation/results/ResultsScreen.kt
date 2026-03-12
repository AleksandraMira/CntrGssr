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
        Text(text = "You WON!", style = MaterialTheme.typography.headlineLarge)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                HeartImage(uiState.heartNumber > 2)
                HeartImage(uiState.heartNumber > 1)
                HeartImage(uiState.heartNumber > 0)
            }

            ElevatedCard(
                modifier = Modifier.padding(horizontal = 90.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Correct answer:",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                        Text(
                            uiState.countryName,
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Your result:",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                        Text(
                            "20 pts",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "X lives used:",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                        Text(
                            "${uiState.heartPoints} pts",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp),
                            color = if (uiState.heartPoints < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Y hints used:",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
                        )
                        Text(
                            "-10 pts",
                            style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp)
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