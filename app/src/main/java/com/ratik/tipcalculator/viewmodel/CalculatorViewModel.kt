package com.ratik.tipcalculator.viewmodel

import android.app.Application
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.model.Calculator
import com.ratik.tipcalculator.model.TipCalculation

class CalculatorViewModel @JvmOverloads constructor(
        app: Application, val calculator: Calculator = Calculator()) : ObservableViewModel(app) {

    private var lastTipCalculate = TipCalculation()

    var inputCheckAmount = ""
    var inputTipPercentage = ""

    val outputCheckAmount
        get() = getApplication<Application>().getString(R.string.rupee_amount, lastTipCalculate.checkAmount)

    val outputTipAmount
        get() = getApplication<Application>().getString(R.string.rupee_amount, lastTipCalculate.tipAmount)

    val outputGrandTotal
        get() = getApplication<Application>().getString(R.string.rupee_amount, lastTipCalculate.grandTotal)

    val locationName
        get() = lastTipCalculate.locationName

    init {
        updateOutputs(TipCalculation())
    }

    private fun updateOutputs(tc: TipCalculation) {
        lastTipCalculate = tc
        notifyChange()
    }

    fun calculateTip() {
        val checkAmount = inputCheckAmount.toDoubleOrNull()
        val tipPct = inputTipPercentage.toIntOrNull()

        if (checkAmount != null && tipPct != null) {
            updateOutputs(calculator.calculateTip(checkAmount, tipPct))
        }
    }

    fun saveCurrentTip(name: String) {
        val tipToSave = lastTipCalculate.copy(locationName = name)
        calculator.saveTipCalculation(tipToSave)
        updateOutputs(tipToSave)
    }
}