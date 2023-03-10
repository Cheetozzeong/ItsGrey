package com.tntt.designsystem.theme

import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.font.Font
import com.tntt.designsystem.R

object IgFont{

    val gangwon = FontFamily(
        Font(R.font.gangwon_kr_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.gangwon_kr_light, FontWeight.Normal, FontStyle.Normal)
    )

    val noto_sans = FontFamily(
        Font(R.font.noto_sans_kr_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.noto_sans_kr_regular, FontWeight.Normal, FontStyle.Normal)
    )
}
