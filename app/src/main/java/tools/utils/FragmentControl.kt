package tools.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.netology.myfit.R

object FragmentControl {
    var openedFragment: Fragment? = null


    fun openFragment(openableFragment: Fragment, neededActivity: AppCompatActivity) {
        val operation = neededActivity.supportFragmentManager.beginTransaction()
        operation.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        operation.replace(R.id.headActivity, openableFragment)
        operation.commit()

        openedFragment = openableFragment

    }


}