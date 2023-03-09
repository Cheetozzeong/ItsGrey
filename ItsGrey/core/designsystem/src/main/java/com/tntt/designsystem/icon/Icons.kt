package com.tntt.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import itsgrey.core.designsystem.R

object IgIcons {

    val Add = R.drawable.icon_add_button_48
    val GoBack = R.drawable.icon_back_button_48
    val Close = R.drawable.icon_close_button_48
    val Next = R.drawable.icon_next_button_48
    val Redo = R.drawable.icon_redo_button_48
    val Undo = R.drawable.icon_undo_button_48
    val Preview = R.drawable.icon_preview_button_48
    val Template = R.drawable.icon_template_button
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}