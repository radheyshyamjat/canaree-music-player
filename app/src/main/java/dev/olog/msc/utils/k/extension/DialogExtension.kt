package dev.olog.msc.utils.k.extension

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.support.v4.content.ContextCompat
import dev.olog.msc.R
import dev.olog.msc.theme.AppTheme

private fun AlertDialog.tintPositiveButton(){
    val color = if (AppTheme.isDarkTheme()) Color.WHITE else ContextCompat.getColor(context!!, R.color.item_selected)
    getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(color)
}

private fun AlertDialog.tintNegativeButton(){
    val color = if (AppTheme.isDarkTheme()) Color.WHITE else ContextCompat.getColor(context!!, R.color.dark_grey)
    getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(color)
}

private fun AlertDialog.tintNeutralButton(){
    val color = if (AppTheme.isDarkTheme()) Color.WHITE else ContextCompat.getColor(context!!, R.color.dark_grey)
    getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(color)
}

fun AlertDialog.Builder.makeDialog(): AlertDialog{
    val dialog = this.show()
    dialog.tintPositiveButton()
    dialog.tintNegativeButton()
    dialog.tintNeutralButton()
    return dialog
}