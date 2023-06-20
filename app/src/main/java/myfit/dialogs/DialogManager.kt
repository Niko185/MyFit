package myfit.dialogs

import android.app.AlertDialog
import android.content.Context
import ru.netology.myfit.R

object DialogManager {
    fun showDialog(context: Context, messageId: Int, listener: Listener){
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null

        builder.setTitle(R.string.alert_tittle)
        builder.setMessage(messageId)

        builder.setPositiveButton(R.string.alert_positive) {_,_ ->
            listener.onClick()
            dialog?.dismiss()
        }

        builder.setNegativeButton(R.string.alert_negative) {_,_ ->
            dialog?.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onClick()
    }
}