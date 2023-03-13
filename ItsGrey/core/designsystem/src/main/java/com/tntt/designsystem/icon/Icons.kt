package com.tntt.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import itsgrey.core.designsystem.R

object IgIcons {

    val NavigateBefore = Icons.Rounded.NavigateBefore
    val Template = Icons.Rounded.AutoAwesomeMosaic
    val PreviewOne = Icons.Rounded.AutoStories
    val PreviewTwo = Icons.Rounded.MenuBook
    val Add = Icons.Rounded.Add
    val Close = Icons.Rounded.Close
    val Check = Icons.Rounded.Check
    val ArrowForward = Icons.Rounded.ArrowForward
    val Undo = Icons.Rounded.Undo
    val Redo = Icons.Rounded.Redo

//    val Add = R.drawable.icon_add_button_48
//    val GoBack = R.drawable.icon_back_button_48
//    val Close = R.drawable.icon_close_button_48
//    val Next = R.drawable.icon_next_button_48
//    val Preview = R.drawable.icon_preview_button_48
//    val Template = R.drawable.icon_template_button
//    val Redo = R.drawable.icon_redo_button_48
//    val Undo = R.drawable.icon_undo_button_48
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}