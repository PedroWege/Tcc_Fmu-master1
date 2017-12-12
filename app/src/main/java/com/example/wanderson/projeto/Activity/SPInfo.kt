package com.example.wanderson.projeto.Activity

import android.content.Context

/**
 * Created by Pedro Wege on 25/11/2017.
 */
    class SPInfo(val context: Context) {

        fun updateIntroStatus(status : Boolean){
            context
                    .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("status", status)
                    .apply()
        }

        fun isIntroShown() = context
                .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                .getBoolean("status", false)
}