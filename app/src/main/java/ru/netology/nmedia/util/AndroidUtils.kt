package ru.netology.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.truncate

object AndroidUtils {

    fun getCountStr(n: Int): String {
        return when (n) {
            in 0..999 -> n.toString() //100
            in 1000..1099 -> "1K"
            in 1100..9999 -> (truncate(n.toDouble() / 1000 * 10) / 10).toString() + "K" //1.1K
            in 10000..999999 -> (n / 1000).toString() + "K" //10K
            else -> (n / 1000000).toString() + "M" //1.1M
        }
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}