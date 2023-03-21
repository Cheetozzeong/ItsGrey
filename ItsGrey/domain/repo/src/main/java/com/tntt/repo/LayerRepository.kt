package com.tntt.repo

import android.graphics.Bitmap

interface LayerRepository {
    fun getSumLayer(pageId: String): Bitmap
}