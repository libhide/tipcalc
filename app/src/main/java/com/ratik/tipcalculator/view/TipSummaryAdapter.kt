package com.ratik.tipcalculator.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.databinding.SavedTipCalculationsListItemBinding
import com.ratik.tipcalculator.viewmodel.TipCalculationSummaryItem

class TipSummaryAdapter(val onItemSelected: (item: TipCalculationSummaryItem) -> Unit)
    : RecyclerView.Adapter<TipSummaryAdapter.TipsSummaryViewHolder>() {

    private val tipCalculationSummaries = mutableListOf<TipCalculationSummaryItem>()

    fun updateList(updates: List<TipCalculationSummaryItem>) {
        tipCalculationSummaries.clear()
        tipCalculationSummaries.addAll(updates)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsSummaryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SavedTipCalculationsListItemBinding>(
                inflater, R.layout.saved_tip_calculations_list_item, parent, false
        )
        return TipsSummaryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tipCalculationSummaries.size
    }

    override fun onBindViewHolder(holder: TipsSummaryViewHolder, position: Int) {
        holder.bind(tipCalculationSummaries[position])
    }


    inner class TipsSummaryViewHolder(val binding: SavedTipCalculationsListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TipCalculationSummaryItem) {
            binding.item = item
            binding.root.setOnClickListener { onItemSelected(item) }
            binding.executePendingBindings()
        }

    }
}