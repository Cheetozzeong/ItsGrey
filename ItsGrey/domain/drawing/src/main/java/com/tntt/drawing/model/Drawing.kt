package com.tntt.drawing.model

data class Drawing(val id: String,
                   val pen: Pen,
                   val eraser: Eraser,
                   val penColor: String,
                   val recentColors: List<String>)