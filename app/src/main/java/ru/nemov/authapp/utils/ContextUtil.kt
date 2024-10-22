package ru.nemov.authapp.utils

import android.content.Context
import android.widget.Toast

fun Context.stringResource(id: Int) = this.resources.getString(id)

fun Context.toast(
    text: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, text, duration).show()
}

fun Context.stringResource(id: Int, vararg formatArgs: Any) =
    this.resources.getString(id, *formatArgs)