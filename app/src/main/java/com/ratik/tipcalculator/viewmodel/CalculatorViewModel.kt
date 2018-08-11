package com.ratik.tipcalculator.viewmodel

import android.databinding.BaseObservable
import com.ratik.tipcalculator.model.Calculator
import com.ratik.tipcalculator.model.TipCalculation

class CalculatorViewModel(val calculator: Calculator = Calculator()): BaseObservable() {
    var inputCheckAmount = ""
    var inputTipPercentage = ""
    var tipCalculation = TipCalculation()

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
            tipCalculation = calculator.calculateTip(checkAmount, tipPct)
            clearInputs()
        }
    }

    private fun clearInputs() {
        inputCheckAmount = "0.00"
        inputTipPercentage = "0"
        notifyChange()
    }
}