package com.SeiElikA.awscertification.Model

import android.content.Context
import androidx.core.content.edit

class QuestionSaveModel(var context: Context) {
    private var sharedPreferences = context.getSharedPreferences("saveQuestion", Context.MODE_PRIVATE)

    var questionIndex: Int
    get() {
        return sharedPreferences.getInt("index", 0)
    }
    set(value) {
        sharedPreferences.edit {
            putInt("index", value)
        }
    }

    var questionIndex2: Int
        get() {
            return sharedPreferences.getInt("index1", 0)
        }
        set(value) {
            sharedPreferences.edit {
                putInt("index1", value)
            }
        }
}