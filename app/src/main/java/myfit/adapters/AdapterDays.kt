package myfit.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import myfit.models.Day
import ru.netology.myfit.R
import ru.netology.myfit.databinding.RecyclerItemDayBinding
import androidx.recyclerview.widget.DiffUtil

    class AdapterDays(var pressingItem: Pressing) : ListAdapter<Day, AdapterDays.HolderDays>(ComparatorItem()) {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDays {
            val layoutItemDay = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_day, parent, false)
            return HolderDays(layoutItemDay)
        }


        override fun onBindViewHolder(holder: HolderDays, position: Int) {
            return holder.setDataItemDayModel(getItem(position), pressingItem)
        }


        interface Pressing {
            fun clickItemDay(ItemDay: Day)

        }

        class HolderDays(elementViewItemDay: View) : RecyclerView.ViewHolder(elementViewItemDay) {
            private val binding = RecyclerItemDayBinding.bind(elementViewItemDay)


            @SuppressLint("SetTextI18n")
            fun setDataItemDayModel(dataDay: Day, firstFragment: AdapterDays.Pressing) = with(binding)
            {
                val startTrainingDayView = root.context.getString(R.string.view_tv_text_number_day_exercises)


                val countTrainingDayView = " ${adapterPosition + 1}"
                fFirstNumberDay.text = startTrainingDayView + countTrainingDayView

                val countPracticeDay = dataDay.cellWithKitExercises.split(",").size.toString()
                val oneBlockNameCountExercises = root.context.getString(R.string.view_tv_text_count_day_exercises)
                val threeBlockNameCountExercises = root.context.getString(R.string.view_tv_text_count_day_exercises_2)
                fFirstTimePractic.text = "$oneBlockNameCountExercises $countPracticeDay $threeBlockNameCountExercises"

                fFirstChekBox.isChecked = dataDay.positionChekBox

                itemView.setOnClickListener { firstFragment.clickItemDay(dataDay.copy(dayNumber = adapterPosition + 1)) }
            }
        }
        //final HolderExercises



        //Start Comparator
        class ComparatorItem : DiffUtil.ItemCallback<Day>() {

            override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
                return  oldItem == newItem
            }
        }
        //Final Comparator
    }
// final class AdapterHolderComparatorDays()









