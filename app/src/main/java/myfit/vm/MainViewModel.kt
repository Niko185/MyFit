package myfit.vm

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import myfit.models.Exercise

class MainViewModel : ViewModel(){
    val mutableArrayExercises = MutableLiveData<ArrayList<Exercise>>() // Массив с готовыми элементами
    var preference: SharedPreferences? = null
    var currentDay = 0

    fun savePreference(key: String, value: Int){
        preference?.edit()?.putInt(key, value)?.apply()    // Key - название/номер дня. value - количество выполненных упражнений в данном дне
    }

    fun getExercisesCount(): Int {
        return preference?.getInt(currentDay.toString(),0) ?: 0
    }
}
