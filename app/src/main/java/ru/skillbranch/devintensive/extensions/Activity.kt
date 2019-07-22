package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard(){
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardClosed(): Boolean{
    val th = 100
    val displayedSize = Rect()
    val displaySize = Rect()
    this.findViewById<View>(android.R.id.content).getWindowVisibleDisplayFrame( displayedSize )
    this.windowManager.defaultDisplay.getRectSize( displaySize )
    //Log.d("M_Activity","$displayedSize $displaySize")
    return displaySize.height() - displayedSize.height() < th
            && displaySize.width() - displayedSize.width() < th
}

fun Activity.isKeyboardOpen() = this.isKeyboardClosed().not()