package com.theandroiddev.mywins.presentation.success_slider

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.theandroiddev.mywins.R
import com.theandroiddev.mywins.core.mvp.MvpDaggerAppCompatActivity
import com.theandroiddev.mywins.core.mvp.startActivity
import com.theandroiddev.mywins.presentation.edit_success.EditSuccessActivity
import com.theandroiddev.mywins.presentation.edit_success.EditSuccessBundle
import com.theandroiddev.mywins.presentation.successes.SuccessModel
import com.theandroiddev.mywins.utils.Constants.Companion.REQUEST_CODE_INSERT
import kotlinx.android.synthetic.main.activity_slider.*

class SuccessSliderActivity : MvpDaggerAppCompatActivity<SuccessSliderView,
        SuccessSliderBundle, SuccessSliderPresenter>(), SuccessSliderView, ActionHandler {

    private lateinit var adapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        slider_fab.setOnClickListener { sliderFabClicked() }
        adapter = ScreenSlidePagerAdapter(supportFragmentManager)

        initFab(slider_fab)
        slider_pager.adapter = adapter
        presenter.onAfterCreate()

    }

    private fun initFab(fab: FloatingActionButton) {

        val id = R.drawable.ic_create_black_24dp
        val color = R.color.white
        val myDrawable = ResourcesCompat.getDrawable(resources, id, null)

        fab.setImageDrawable(myDrawable)
        fab.setColorFilter(ContextCompat.getColor(this, color))

    }

    private fun sliderFabClicked() {

        presenter.sliderFabClicked(slider_pager.currentItem, adapter.successes)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_INSERT && data != null) {
            if (resultCode == Activity.RESULT_OK) {

                presenter.onRequestCodeInsert()
                Snackbar.make(slider_constraint, "Saved", Snackbar.LENGTH_SHORT).show()
            }
        }

        if (resultCode == RESULT_CANCELED) {

        }

    }

    override fun onBackPressed() {

        val returnIntent = Intent()
        returnIntent.putExtra("position", slider_pager.currentItem)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()

    }

    override fun displaySuccesses(successes: MutableList<SuccessModel>, position: Int) {

        adapter.successes = successes
        slider_pager.currentItem = position
        adapter.notifyDataSetChanged()

    }

    override fun displayEditSuccessActivity(id: Long) {
        startActivity<EditSuccessActivity>(EditSuccessBundle(id), REQUEST_CODE_INSERT)
    }

    override fun onAddImage() {
        sliderFabClicked()
    }

    class ScreenSlidePagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

        var successes = mutableListOf<SuccessModel>()

        override fun getItem(position: Int): androidx.fragment.app.Fragment {
            val bundle = Bundle()
            bundle.putLong("id", successes[position].id ?: 0)
            val frag = SuccessSliderFragment()
            frag.arguments = bundle
            return frag
        }

        override fun getCount(): Int {
            return successes.size
        }
    }
}
