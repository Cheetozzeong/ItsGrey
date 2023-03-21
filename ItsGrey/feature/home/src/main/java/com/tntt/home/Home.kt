package com.tntt.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.designsystem.component.IgTabsMain
import com.tntt.designsystem.component.IgTopAppBar

@Preview
@Composable
fun Home(){
    Scaffold(
        topBar = {
            IgTopAppBar(
                titleRes = android.R.string.untitled,
                actions = {
                    Text(text = "unknown")
                }
            )
        },
        backgroundColor = Color.Gray,

    ) {

    }
}