package com.ratik.tipcalculator.viewmodel

import android.app.Application
import android.databinding.BaseObservable
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.model.Calculator
import com.ratik.tipcalculator.model.TipCalculation

class CalculatorViewModel(val app: Application, val calculator: Calculator = Calculator()): BaseObservable() {
    var inputCheckAmount = ""
    var inputTipPercentage = ""

    var outputCheckAmount = ""
    var outputTipAmount = ""
    var outputGrandTotal = ""

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        outputCheckAmount = app.getString(R.string.rupee_amount, tc.checkAmount)
        outputTipAmount = app.getString(R.string.rupee_amount, tc.tipAmount)
        outputGrandTotal = app.getString(R.string.rupee_amount, tc.grandTotal)
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
            clearInputs()
        }
    }

    private fun clearInputs() {
        inputCheckAmount = "0.00"
        inputTipPercentage = "0"
        notifyChange()
    }
}