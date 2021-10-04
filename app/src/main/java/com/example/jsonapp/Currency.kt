package com.example.jsonapp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

class Currency {

    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Curr? = null

    class Curr {
        @SerializedName("sar")
        var sar: String? = null

        @SerializedName("usd")
        var usd: String? = null

        @SerializedName("cad")
        var cad: String? = null

        @SerializedName("jpy")
        var jpy: String? = null


    }


}

