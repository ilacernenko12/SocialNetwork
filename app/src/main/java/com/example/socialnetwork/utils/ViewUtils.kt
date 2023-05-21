package com.example.socialnetwork.utils

import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class ViewUtils {
    companion object{
        fun clearField(editText : EditText){
            editText.setText("")
        }

        fun clearField(textView : TextView){
            textView.text = ""
        }
    }
}