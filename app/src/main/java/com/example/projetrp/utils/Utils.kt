package com.example.projetrp.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal

object Utils {
    fun closeKeyboard(activity:Activity){
        if(activity.currentFocus == null){
            return
        }
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    fun checkInput(inputEditText: TextInputEditText) {
        if (inputEditText.text.toString().trim().isEmpty())
            inputEditText.error = "Entrer une valeur!"
    }

    fun isInputNotEmpty(inputEditText: TextInputEditText) :Boolean{
        return inputEditText.text.toString().trim().isNotEmpty()
    }

    fun checkInput(inputEditText: TextInputEditText, msg:String) {
        if (inputEditText.text.toString().trim().isEmpty())
            inputEditText.error = msg
    }

    fun checkInput(autoCompleteTextView: AutoCompleteTextView) {
        if (autoCompleteTextView.text.toString().trim().isEmpty())
            autoCompleteTextView.error = "Choisir parmis la liste!"
        else
            autoCompleteTextView.error = null
    }

    fun round(v:Double, decimal: Int = 2):Double = "%.${decimal}f".format(v).toDouble()

    @JvmStatic
    fun getPreference(context: Context):SharedPreferences{
        val appContex = context.applicationContext
        return PreferenceManager.getDefaultSharedPreferences(appContex)
    }

    fun fromHTML(text: String): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(text)
        }
    }
}