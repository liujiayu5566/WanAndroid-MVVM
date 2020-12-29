package com.castiel.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

object ToastUtils {
    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    fun showToast(
        context: Context?,
        content: String?
    ) {
        if (toast == null) {
            toast = Toast.makeText(
                context,
                content,
                Toast.LENGTH_SHORT
            )
        } else {
            toast!!.setText(content)
        }
        toast!!.show()
    }

}