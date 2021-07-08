package com.example.health2u.utils

class Utils {

    companion object {
        const val base_Url = "https://newsapi.org/v2/"
        const val test_center_base_Url = "https://discover.search.hereapi.com/v1/"
        const val api_key = "dc7452fd7d464e98bce56dd9566034ec"
        const val test_center_api_key = "E42WNqBU5XsUJyuHWgBgRE7jcmGD4LJZW5kMlA01pJo"
        fun getMiles(distance: Int):Double
        {
            val number=distance*0.00062137112
            val roundoff=Math.round(number *1000.0)/1000.0
            return roundoff
        }

    }



}