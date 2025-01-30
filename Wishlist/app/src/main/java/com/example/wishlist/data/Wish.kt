package com.example.wishlist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "wish_title")
    val title: String = "",
    @ColumnInfo(name = "wish_description")
    val description: String = "",
)

object DummyWish {
    val wishList = listOf(
        Wish(
            id = 1,
            title = "test 1",
            description = "test description 1"
        ),
        Wish(
            id = 2,
            title = "test 2",
            description = "test description 2"
        ),
        Wish(
            id = 3,
            title = "test 3",
            description = "test description 3"
        ),
        Wish(
            id = 4,
            title = "test 4",
            description = "test description 4"
        ),
    )
}

