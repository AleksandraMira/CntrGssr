package com.example.cntrgssr.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cntrgssr.R
import com.example.cntrgssr.theme.AppTheme

@Composable
fun HomeScreen(
    onUserEvent: (Home.UserEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
    ) {
        Text(stringResource(R.string.home_screen_app_name), color = MaterialTheme.colorScheme.inverseSurface, style = MaterialTheme.typography.headlineLarge.copy(fontSize = 44.sp, fontWeight = FontWeight.W600))
        Column(
            modifier = Modifier.fillMaxWidth(0.45f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                content = { Text(stringResource(R.string.home_screen_start_button)) },
                onClick = { onUserEvent(Home.UserEvent.OnStartButtonClicked) },
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                content = { Text(stringResource(R.string.home_screen_guide_button)) },
                onClick = { onUserEvent(Home.UserEvent.OnGuideButtonClicked) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}