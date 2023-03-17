package com.tntt.page.datasource

interface RemotePageDataSource {
    fun createPage(pageId: String): String

}