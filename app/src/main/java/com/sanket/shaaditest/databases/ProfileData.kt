package com.sanket.shaaditest.databases

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "profile_data")
class ProfileData {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    @NonNull
    @ColumnInfo(name = "name")
    var name: String = ""

    @NonNull
    @ColumnInfo(name = "age")
    var age: String = ""

    @NonNull
    @ColumnInfo(name = "gender")
    var gender: String = ""

    @NonNull
    @ColumnInfo(name = "country")
    var country: String = ""

    @NonNull
    @ColumnInfo(name = "state")
    var state: String = ""

    @ColumnInfo(name = "profile_picture")
    var profile_picture: String = ""

    // 0 - natural , 1 - accepted , 2 - rejected
    @NonNull
    @ColumnInfo(name = "acception_state")
    var acception_state: Int = 0

}