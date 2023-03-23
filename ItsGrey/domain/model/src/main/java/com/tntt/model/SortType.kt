package com.tntt.model

import com.google.firebase.firestore.Query

enum class SortType(val order: Query.Direction, val by: String) {
    TITLE(Query.Direction.ASCENDING, "title"),
    SAVE_DATE(Query.Direction.DESCENDING, "saveDate"),
}