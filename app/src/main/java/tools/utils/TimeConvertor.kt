package tools.utils

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat

object TimeConvertor {

    @SuppressLint("SimpleDateFormat")
    val formatTime = SimpleDateFormat("mm:ss")

    @RequiresApi(Build.VERSION_CODES.N)
    fun getTime(timeM: Long): String{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeM
        return formatTime.format(calendar.time)
    }

    fun showTime() {

    }
}