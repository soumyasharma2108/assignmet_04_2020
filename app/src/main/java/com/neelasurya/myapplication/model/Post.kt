package com.neelasurya.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

class UserData(var results: ArrayList<Results>, var info: Info?)
@Entity
data class Results(
        @ColumnInfo(name = "primary_id")
        @PrimaryKey(autoGenerate = true)
        var objectId: Int = 0,
        var gender: String?,
        @Embedded
        var name: Name?,
        @Embedded
        var location: Location?,
        var email: String?,
        @Embedded
        var login: Login?,
        @Embedded
        var dob: Dob?,
        @Embedded
        var registered: Registered?,
        var phone: String?,
        var cell: String?,
        @Embedded
        var id: Id?,
        @Embedded
        var picture: Picture?,
        var nat: String?,
        var isAccepted: Boolean,
        var isRejected: Boolean)

data class Picture(
        var large: String?,
        var medium: String?,
        var thumbnail: String?
)

data class Id(
        @ColumnInfo(name = "id_name")
        var name: String?,
        var value: String?
)

data class Registered(
        @ColumnInfo(name = "registered_date")
        var date: String?,
        @ColumnInfo(name = "registered_age")
        var age: String?
)

data class Dob(
        @ColumnInfo(name = "dob_date")
        var date: String?,
        @ColumnInfo(name = "dob_age")

        var age: String?
)

data class Login(
        var uuid: String?,
        var username: String?,
        var password: String?,
        var salt: String?,
        var md5: String?,
        var sha1: String?,
        var sha256: String?
)

data class Location(
        @Embedded
        var street: Street?,
        var city: String?,
        var state: String?,
        var country: String?,
        var postcode: String?,
        @Embedded
        var coordinates: Coordinates?,
        @Embedded
        var timezone: Timezone?
)

data class Timezone(
        var offset: String?,
        var description: String?

)

data class Coordinates(
        var latitude: String?,
        var longitude: String?
)

data class Street(
        var number: String?,
        @ColumnInfo(name = "street_name")
        var name: String?
)

data class Name(
        var title: String?,
        var first: String?,
        var last: String?
)

data class Info(
        var seed: String?,
        var results: String?,
        var page: String?,
        var version: String?
)