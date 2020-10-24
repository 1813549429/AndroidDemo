package com.example.kotlindemo.view

import android.app.Dialog
import android.content.Context
import com.example.kotlindemo.R

//加载框，object不能设置构造方法
object LoadingDialog {

    fun show1(){}

    fun show2(){}

    private var dialog: Dialog ? = null

    fun show(context: Context) {
        cancel()
        dialog = Dialog(context)
        dialog?.setContentView(
            R.layout.dialog_loading
        )
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        // .....
        dialog?.show()
    }

    fun cancel() {
        dialog?.dismiss()
    }
}