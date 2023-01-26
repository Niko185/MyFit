package adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import data.ItemDayModel
import ru.netology.myfit.R
import androidx.recyclerview.widget.DiffUtil
import data.ItemExercisesModel
import pl.droidsonroids.gif.GifDrawable
import ru.netology.myfit.databinding.RecyclerItemExercisessBinding


// start class AdapterHolderComparatorDays()

    //Start AdapterDays
    class AdapterExercises() : ListAdapter<ItemExercisesModel, AdapterExercises.HolderExercises>(ComparatorItem()) {


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
            fun setDataItemExercisesModel(itemDataExercisesModel: ItemExercisesModel) = with(binding) {
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
        class ComparatorItem : DiffUtil.ItemCallback<ItemExercisesModel>() {

            override fun areItemsTheSame(oldItem: ItemExercisesModel, newItem: ItemExercisesModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemExercisesModel, newItem: ItemExercisesModel): Boolean {
                return  oldItem == newItem
            }
        }
        //Final Comparator
    }


// final class AdapterHolderComparatorDays()









