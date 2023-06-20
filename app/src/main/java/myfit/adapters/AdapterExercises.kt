package myfit.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myfit.R
import androidx.recyclerview.widget.DiffUtil
import myfit.models.Exercise
import pl.droidsonroids.gif.GifDrawable
import ru.netology.myfit.databinding.RecyclerItemExercisessBinding


// start class AdapterHolderComparatorDays()

    //Start AdapterDays
    class AdapterExercises() : ListAdapter<Exercise, AdapterExercises.HolderExercises>(ComparatorItem()) {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderExercises {
            val layoutItemDay = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_exercisess, parent, false)
            return HolderExercises(layoutItemDay)
        }


        override fun onBindViewHolder(holder: HolderExercises, position: Int) {
            return holder.setDataItemExercisesModel(getItem(position))
        }
        //final AdapterDays


        // start HolderExercises
        class HolderExercises(elementViewItemDay: View) : RecyclerView.ViewHolder(elementViewItemDay) {
            private val binding = RecyclerItemExercisessBinding.bind(elementViewItemDay)




            @SuppressLint("SetTextI18n")
            fun setDataItemExercisesModel(itemDataExercisesModel: Exercise) = with(binding) {
                var timeNaming = itemDataExercisesModel.timeExercises

                if(itemDataExercisesModel.timeExercises.startsWith("x")){
                    fSecondCount.text = "$timeNaming repeat"
                } else {
                    fSecondCount.text = "$timeNaming seconds"
                }

                fSecondName.text = itemDataExercisesModel.nameExercises
                fSecondImage.setImageDrawable(GifDrawable(root.context.assets, itemDataExercisesModel.gifExercises))
                fSecondChekBox.isChecked = itemDataExercisesModel.checkBoxDone



            }
        }
        //final HolderExercises


        //Start Comparator
        class ComparatorItem : DiffUtil.ItemCallback<Exercise>() {

            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return  oldItem == newItem
            }
        }
        //Final Comparator
    }


// final class AdapterHolderComparatorDays()









