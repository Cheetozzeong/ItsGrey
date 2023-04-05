package com.tntt.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import itsgrey.core.designsystem.R

object IgIcons {

    val NavigateBefore = Icons.Rounded.NavigateBefore
    val Template = Icons.Rounded.AutoAwesomeMosaic
    val PreviewOne = Icons.Rounded.AutoStories
    val PreviewTwo = Icons.Rounded.MenuBook
    val Add = Icons.Rounded.Add
    val Remove = Icons.Rounded.Remove
    val Close = Icons.Rounded.Close
    val Check = Icons.Rounded.Check
    val ArrowForward = Icons.Rounded.ArrowForward
    val Undo = Icons.Rounded.Undo
    val Redo = Icons.Rounded.Redo
    val AddImageBox = Icons.Rounded.Image
    val AddTextBox = Icons.Rounded.TextFields
    val Brush = Icons.Filled.Brush
    val Eraser = R.drawable.icon_eraser_48
    val Edit = Icons.Rounded.Edit
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}