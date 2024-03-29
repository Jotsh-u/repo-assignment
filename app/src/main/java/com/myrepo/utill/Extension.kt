package com.myrepo.utill

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun showSnackBar(view: View) {
    val snackBar = Snackbar.make(
        view, "No Internet Connection",
        Snackbar.LENGTH_LONG
    ).setAction("Ok") {
    }
    snackBar.setActionTextColor(Color.BLACK)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.RED)
    val textView: TextView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.BLACK)
    snackBar.show()
}