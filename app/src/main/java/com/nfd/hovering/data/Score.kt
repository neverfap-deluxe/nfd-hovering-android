package com.nfd.hovering.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audios")
data class Score(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "date") val date: String?
)