package com.tntt.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tntt.designsystem.component.IgTabsMain

@Preview
@Composable
fun Home(){
    IgTabsMain(listOf("출판","작성중"))
}