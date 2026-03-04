package com.example.cntrgssr.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cntrgssr.R

@Composable
fun HeartImage(isFull: Boolean) {
    Image(
        painter = painterResource(if (isFull) R.drawable.full_heart else R.drawable.empty_heart),
        contentDescription = "",
        modifier = Modifier.size(40.dp)
    )
}