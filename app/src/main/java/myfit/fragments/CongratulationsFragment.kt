package myfit.fragments



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifDrawable
import ru.netology.myfit.R
import myfit.dialogs.FragmentControl
import ru.netology.myfit.databinding.FragmentCongratulationsBinding


class CongratulationsFragment : Fragment() {
    private lateinit var binding: FragmentCongratulationsBinding
    private var actionBarFive : ActionBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCongratulationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionBarFive = (activity as AppCompatActivity).supportActionBar
        startGif()
        clickButton()
        textActionBar()
    }

    private fun startGif() = with(binding) {
        fFiveImageGif.setImageDrawable(GifDrawable(root.context.assets, "great.gif"))
    }

    private fun clickButton() = with(binding){
        fFiveButton.setOnClickListener(){
            FragmentControl.openFragment(DaysFragment.toFix(), activity as AppCompatActivity)
        }
    }

   private fun textActionBar(){
        val textTitle = getString(R.string.five_action_bar_text)
        actionBarFive?.title = textTitle
    }

    companion object {
        @JvmStatic
        fun toFix() = CongratulationsFragment()
    }
}


