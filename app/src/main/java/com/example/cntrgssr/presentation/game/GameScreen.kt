package com.example.cntrgssr.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cntrgssr.R
import com.example.cntrgssr.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    // TODO: Add navigate back handling
    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 6.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End)
        ) {
            HeartImage(true)
            HeartImage(true)
            HeartImage(false)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(modifier = Modifier.padding(bottom = 16.dp), text = stringResource(R.string.guide_screen_title), style = MaterialTheme.typography.headlineLarge)
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
            value = "",
            onValueChange = { /*TODO*/ },
            label = { Text("Your Guess", style = MaterialTheme.typography.labelLarge) },
            singleLine = true,
        )
            Column(
                modifier = Modifier.padding(top = 6.dp),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text("Submit") },
                    onClick = {},
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
}

@Composable
private fun HeartImage(isFull: Boolean) {
    Image(
        painter = painterResource(if (isFull) R.drawable.full_heart else R.drawable.empty_heart),
        contentDescription = "",
        modifier = Modifier.size(40.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        GameScreen()
    }
}