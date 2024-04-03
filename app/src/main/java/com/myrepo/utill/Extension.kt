package com.myrepo.utill

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.myrepo.R

fun Context.hideKeyboard(view: View) {
    try {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }catch (e:Exception){
        e.printStackTrace()
    }
}

fun View.showSnackBar() {
    val snackBar = Snackbar.make(
        this, "No Internet Connection",
        Snackbar.LENGTH_LONG
    )/*.setAction("Ok") {
    }*/
    snackBar.setActionTextColor(Color.BLACK)
    val snackBarView = snackBar.view
    snackBarView.setBackgroundColor(Color.RED)
    val textView: TextView =
        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
    textView.setTextColor(Color.WHITE)
    textView.gravity = Gravity.CENTER_HORIZONTAL
    snackBar.show()
}

fun Context.showCircularImage(imageView:AppCompatImageView,url:String){
    Glide.with(this).load(url).placeholder(R.drawable.ic_launcher_foreground).circleCrop().into(imageView)
}

fun String.getColorBasedOnLang():Int{
    return  when(this){
        "Python" -> {Color.MAGENTA}
        "C++" -> {Color.CYAN}
        "Java" -> {Color.RED}
        "Ruby" -> {Color.YELLOW}
        else -> {Color.BLUE}
    }
}