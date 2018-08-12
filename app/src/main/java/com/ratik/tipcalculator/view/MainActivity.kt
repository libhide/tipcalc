package com.ratik.tipcalculator.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.databinding.ActivityMainBinding
import com.ratik.tipcalculator.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity(), SaveDialogFragment.Callback, LoadDialogFragment.Callback {
    override fun onSaveTip(name: String) {
        binding.vm?.saveCurrentTip(name)
        Snackbar.make(binding.root, "Saved $name", Snackbar.LENGTH_SHORT).show()
    }

    override fun onTipSelected(name: String) {
        binding.vm?.loadTipCalculation(name)
        Snackbar.make(binding.root, "Loaded $name", Snackbar.LENGTH_SHORT).show()
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tip_calculator, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                showSaveDialog()
                true
            }
            R.id.action_load -> {
                showLoadDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSaveDialog() {
        val saveFragment = SaveDialogFragment()
        saveFragment.show(supportFragmentManager, "Save Dialog")
    }

    private fun showLoadDialog() {
        val loadFragment = LoadDialogFragment()
        loadFragment.show(supportFragmentManager, "Load Dialog")
    }
}
