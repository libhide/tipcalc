package com.ratik.tipcalculator.viewmodel

import android.app.Application
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.model.Calculator
import com.ratik.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        app: Application, val calculator: Calculator = Calculator()) : ObservableViewModel(app) {

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    var outputCheckAmount = ""
    var outputTipAmount = ""
    var outputGrandTotal = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        outputCheckAmount = getApplication<Application>().getString(R.string.rupee_amount, tc.checkAmount)
        outputTipAmount = getApplication<Application>().getString(R.string.rupee_amount, tc.tipAmount)
        outputGrandTotal = getApplication<Application>().getString(R.string.rupee_amount, tc.grandTotal)
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
            notifyChange()
        }
    }
}