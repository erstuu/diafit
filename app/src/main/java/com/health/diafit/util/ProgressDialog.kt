package com.health.diafit.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.health.diafit.R

class ProgressDialog(context: Context) {
    private val progressBarDialog: Dialog = Dialog(context)

    init {
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_loader, null as ViewGroup?, false)

        progressBarDialog.apply {
            setCancelable(false)
            setContentView(dialogLayout)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    fun showLoading() {
        progressBarDialog.show()
    }

    fun hideLoading() {
        progressBarDialog.dismiss()
    }
}