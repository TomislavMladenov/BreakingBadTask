package com.breaking.bad.framework.datasource.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(


    @SerializedName("char_id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("birthday")
    @Expose
    val birthday: String,

    @SerializedName("occupation")
    @Expose
    val occupation: ArrayList<String>,

    @SerializedName("img")
    @Expose
    val image: String,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("nickname")
    @Expose
    val nickname: String,

    @SerializedName("appearance")
    @Expose
    val appearance: ArrayList<Int>,

    @SerializedName("portrayed")
    @Expose
    val portrayed: String,

    @SerializedName("category")
    @Expose
    val category: String

) : Parcelable {


}