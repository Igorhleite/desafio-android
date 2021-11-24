package com.picpay.desafio.android.utils

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.picpay.desafio.android.R
import java.io.IOException

fun Context.makeToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT)
        .show()
}

fun Context.getDefaultErrorMessage(): String {
    return this.getString(R.string.error)
}