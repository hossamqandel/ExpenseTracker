package dev.hossam.expensetracker.core.extension

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar


fun Context.showSnackBar(
    view: View,
    @StringRes message: Int,
    length: Int = Snackbar.LENGTH_LONG,
    @StringRes buttonTitle: Int,
    block: () -> Unit?
) {
    Snackbar.make(
        view,
        message,
        length
    ).setAction(buttonTitle) {
        block.invoke()
    }.show()

}