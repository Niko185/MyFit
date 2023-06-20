package myfit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import myfit.adapters.AdapterDays
import android.view.*
import androidx.appcompat.app.ActionBar
import myfit.models.Day
import myfit.models.Exercise
import ru.netology.myfit.R
import myfit.dialogs.DialogManager
import myfit.dialogs.FragmentControl
import myfit.vm.MainViewModel
import ru.netology.myfit.databinding.FragmentDaysBinding


class DaysFragment : Fragment(), AdapterDays.Pressing {
    private lateinit var binding : FragmentDaysBinding
    private var actionBarOne : ActionBar? = null
    private val mutableModel : MainViewModel by activityViewModels()
    private var daysDoneCounter = 0
    private lateinit var connector: AdapterDays
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mutableModel.currentDay = 0
        connectRcv()
        actionBarOne = (activity as AppCompatActivity).supportActionBar
        getTextActionBarOne()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDel) {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.alert_message,
                object : DialogManager.Listener{
                    override fun onClick() {
                        mutableModel.preference?.edit()?.clear()?.apply()
                        connector.submitList(getCollectArrayItemDayModel())

                    }

                })
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTextActionBarOne(){
        var text = getString(R.string.app_name)
        actionBarOne?.title = text
    }

    private fun getCollectArrayItemDayModel(): ArrayList<Day>{
        val dayArray = ArrayList<Day>()                     // Создали Экземпляр класса
        resources.getStringArray(R.array.kit_day_exercise).forEach{
            mutableModel.currentDay++
            val exCounter = it.split(",").size
            dayArray.add(Day(it, 0,mutableModel.getExercisesCount() == exCounter))
        }
        binding.fFirstProgressBar.max = dayArray.size
        dayArray.forEach {
            if(it.positionChekBox) daysDoneCounter++
        }
        progressBarUI(dayArray.size - daysDoneCounter, dayArray.size)
        return dayArray
    }

    private fun progressBarUI(restDays: Int, days: Int) = with(binding){
        val text = "$restDays " + getString(R.string.first_bar_name)
        fFirstProgressDay.text = text
        fFirstProgressBar.progress = days - restDays
    }

    private fun connectRcv() = with(binding) {

        connector = AdapterDays(this@DaysFragment)
        fFirstRcView.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        fFirstRcView.adapter = connector
        connector.submitList(getCollectArrayItemDayModel())
    }

    companion object {
        @JvmStatic
        fun toFix() = DaysFragment()
    }

    override fun clickItemDay(ItemDay: Day) {
        if (!ItemDay.positionChekBox) {
            fillArrayExercises(ItemDay)
            mutableModel.currentDay = ItemDay.dayNumber
            FragmentControl.openFragment(
                KitExercisesFragment.toFix(),
                activity as AppCompatActivity)
        } else {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.alert_message_day,
                object : DialogManager.Listener{
                    override fun onClick() {
                        mutableModel.savePreference(ItemDay.dayNumber.toString(), 0)
                        fillArrayExercises(ItemDay)
                        mutableModel.currentDay = ItemDay.dayNumber
                        FragmentControl.openFragment(
                            KitExercisesFragment.toFix(),
                            activity as AppCompatActivity)
                    }
                })
        }
    }

    private fun fillArrayExercises(ItemDay: Day) {
        val list = ArrayList<Exercise>()
        ItemDay.cellWithKitExercises.split(",").forEach{
            val listExercises = resources.getStringArray(R.array.exercise)
            val elementExercise = listExercises[it.toInt()]
            val finalArrayExercises = elementExercise.split("|")
            list.add(Exercise(finalArrayExercises[0],finalArrayExercises[1], false , finalArrayExercises[2]))
        }
        mutableModel.mutableArrayExercises.value = list
    }
}




