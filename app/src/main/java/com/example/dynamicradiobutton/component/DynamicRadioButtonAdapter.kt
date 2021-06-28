package com.example.dynamicradiobutton.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.dynamicradiobutton.R

class DynamicRadioButtonAdapter(
    private val onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<DynamicRadioButtonAdapter.BaseViewHolder<*>>() {

    var selectedItem = -1
        private set

    private val options: MutableList<String> = mutableListOf()

    fun updateOptions(optionsList: MutableList<String>) {
        options.clear()
        options.addAll(optionsList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): BaseViewHolder<Any?> = RadioButtonViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflateLayout(parent, R.layout.view_dynamic_radio_button_item)
    )

    private fun LayoutInflater.inflateLayout(parent: ViewGroup, layoutId: Int): View =
        inflate(layoutId, parent, false)

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = options[position]
        holder.bind(element, position)
    }

    abstract class BaseViewHolder<T>(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(
            radioDescription: String,
            position: Int
        )
    }

    inner class RadioButtonViewHolder(itemView: View) : DynamicRadioButtonAdapter.BaseViewHolder<Any?>(itemView) {
        override fun bind(
            radioDescription: String,
            position: Int
        ) {
            with(itemView) {
                findViewById<RadioButton>(R.id.optionRadiobutton).run {
                    isChecked = position == selectedItem
                    text = options[position]
                    setOnClickListener { onRadioClicked() }
                }
            }
        }

        private fun onRadioClicked() {
            onItemSelected(adapterPosition)
            updateSelectedItem(adapterPosition)
        }
    }

    private fun updateSelectedItem(position: Int) {
        selectedItem = position

        if (position > 0) {
            notifyItemRangeChanged(0, position)
        }
        if (position < options.size) {
            notifyItemRangeChanged(position + OFFSET_POSITION, options.size - position)
        }
    }

    companion object {
        const val OFFSET_POSITION = 1
    }
}