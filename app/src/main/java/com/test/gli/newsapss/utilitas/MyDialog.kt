package com.test.gli.newsapss.utilitas

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import com.test.gli.newsapss.R

class MyDialog {

    companion object{

        fun showDialogMessage(context: Context?, message: String, listener:DialogInfoListener?){
            showDialogMessage(context, message, null, listener)
        }

        fun showDialogMessage(context: Context?, message:String, title:String?, listener:DialogInfoListener? ){

            val dialog = Dialog(context!!)
            val view = View.inflate(context, R.layout.dialog_alert_info, null)
            val textTitle = view.findViewById<TextView>(R.id.textTitle)
            val textMessage = view.findViewById<TextView>(R.id.textMessage)
            val btnOk = view.findViewById<View>(R.id.textOk)

            if(title != null) textTitle.text = title
//            textMessage.text = message
            textMessage.text = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
            }else{
                Html.fromHtml(message)
            }

            btnOk.setOnClickListener {
                dialog.dismiss()
                listener?.onClickOk()
            }

            dialog.setContentView(view)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.create()
            dialog.show()
        }

        fun showDialogDecisions(context: Context?, message: String, listener: DialogInfoDecisionListener?){
            val dialog = Dialog(context!!)
            val view = View.inflate(context, R.layout.dialog_alert_decision, null)
            val textMessage = view.findViewById<TextView>(R.id.textMessage)
            val btnYes = view.findViewById<View>(R.id.textYes)
            val btnNo = view.findViewById<View>(R.id.textNo)

            textMessage.text = message
            btnYes.setOnClickListener {
                dialog.dismiss()
                listener?.onPositive()
            }
            btnNo.setOnClickListener {
                dialog.dismiss()
                listener?.onNegative()
            }

            dialog.setContentView(view)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.create()
            dialog.show()
        }
    }

    interface DialogInfoListener{
        fun onClickOk()
    }

    interface DialogInfoDecisionListener{
        fun onPositive()
        fun onNegative()
    }
}