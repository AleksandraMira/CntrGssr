package com.example.cntrgssr.presentation.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cntrgssr.R
import com.example.cntrgssr.theme.AppTheme

@Composable
fun GuideScreen(
    onUserEvent: (Guide.UserEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 16.dp), text = stringResource(R.string.guide_screen_title), style = MaterialTheme.typography.headlineLarge)
        GuideCard(
            stringResource(R.string.guide_screen_hints_title),
            stringResource(R.string.guide_screen_hints_description),
        )
        GuideCard(
            stringResource(R.string.guide_screen_lives_title),
            stringResource(R.string.guide_screen_lives_description)
        )
        GuideCard(
            stringResource(R.string.guide_screen_give_up_title),
            stringResource(R.string.guide_screen_give_up_description)
        )
        Text(text = stringResource(R.string.guide_screen_good_luck), style = MaterialTheme.typography.bodyLarge)
        Button(
            modifier = Modifier.fillMaxWidth(0.45f),
            content = { Text(stringResource(R.string.guide_screen_back_button)) },
            onClick = { onUserEvent(Guide.UserEvent.OnBackClicked) },
        )
    }
}

@Composable
private fun GuideCard(title: String, description: String) {
    ElevatedCard {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(title, style = MaterialTheme.typography.labelLarge)
            HorizontalDivider(modifier = Modifier.padding(top = 2.dp, bottom = 4.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GuideScreenPreview() {
    AppTheme {
        GuideScreen()
    }
}