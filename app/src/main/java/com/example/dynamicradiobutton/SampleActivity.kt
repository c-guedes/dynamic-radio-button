package com.example.dynamicradiobutton

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicradiobutton.component.DynamicRadioButtonAdapter
import com.google.android.material.snackbar.Snackbar

class SampleActivity : AppCompatActivity() {
    private val radioList: RecyclerView by lazy { findViewById(R.id.dynamicRadio) }
    private val selectButton: Button by lazy { findViewById(R.id.btnSelectOption) }
    private val sampleOptions =
        mutableListOf("Option", "Option", "Option", "Option", "Option", "Option")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerList()
        setupClickListener()
    }

    private fun setupRecyclerList() {
        radioList.apply {
            adapter = DynamicRadioButtonAdapter(::toggleButtonState)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

        (radioList.adapter as DynamicRadioButtonAdapter).updateOptions(sampleOptions)
    }

    private fun toggleButtonState(onItemSelected: Int) {
        if (onItemSelected != -1) selectButton.isEnabled = true
    }

    private fun setupClickListener() {
        selectButton.setOnClickListener {
            val optionSelected = (radioList.adapter as DynamicRadioButtonAdapter).selectedItem
            Snackbar.make(it, "Selected option position $optionSelected", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}