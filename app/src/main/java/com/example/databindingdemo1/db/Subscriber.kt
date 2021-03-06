package com.example.databindingdemo1.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id:Int,

    @ColumnInfo(name = "user_name")
    val name:String,

    @ColumnInfo(name = "user_email")
    val email:String
)