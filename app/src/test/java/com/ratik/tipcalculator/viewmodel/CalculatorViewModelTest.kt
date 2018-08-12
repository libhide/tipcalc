package com.ratik.tipcalculator.viewmodel

import android.app.Application
import com.ratik.tipcalculator.R
import com.ratik.tipcalculator.model.Calculator
import com.ratik.tipcalculator.model.TipCalculation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {
    lateinit var calculatorViewModel: CalculatorViewModel

    @Mock
    lateinit var mockCalculator: Calculator

    @Mock
    lateinit var app: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        stubResource(0.0, "₹0.00")
        calculatorViewModel = CalculatorViewModel(app, mockCalculator)
    }

    private fun stubResource(given: Double, returnStub: String) {
        `when`(app.getString(R.string.rupee_amount, given)).thenReturn(returnStub)
    }

    @Test
    fun testCalculateTip() {
        calculatorViewModel.inputCheckAmount = "700.00"
        calculatorViewModel.inputTipPercentage = "15"

        val stub = TipCalculation(checkAmount = 700.00, tipPct = 15, tipAmount = 105.00, grandTotal = 805.00)
        `when`(mockCalculator.calculateTip(700.00, 15)).thenReturn(stub)
        stubResource(700.0, "₹700.00")
        stubResource(105.0, "₹105.00")
        stubResource(805.0, "₹805.00")

        calculatorViewModel.calculateTip()

        assertEquals("₹700.00", calculatorViewModel.outputCheckAmount)
        assertEquals("₹105.00", calculatorViewModel.outputTipAmount)
        assertEquals("₹805.00", calculatorViewModel.outputGrandTotal)
    }

    @Test
    fun testCalculateTipBadTipPercent() {
        calculatorViewModel.inputCheckAmount = "700.00"
        calculatorViewModel.inputTipPercentage = ""

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt());
    }

    @Test
    fun testCalculateTipBadCheckAmount() {
        calculatorViewModel.inputCheckAmount = ""
        calculatorViewModel.inputTipPercentage = "15"

        calculatorViewModel.calculateTip()

        verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt());
    }

    @Test
    fun testSaveCurrentTip() {
        val stub = TipCalculation(checkAmount = 700.00, tipAmount = 105.00, grandTotal = 805.00)
        val stubLocationName = "Green Eggs and Bacon"

        fun setupTipCalculation() {
            calculatorViewModel.inputCheckAmount = "700.00"
            calculatorViewModel.inputTipPercentage = "15"

            `when`(mockCalculator.calculateTip(700.00, 15)).thenReturn(stub)
        }

        setupTipCalculation()
        calculatorViewModel.calculateTip()
        calculatorViewModel.saveCurrentTip(stubLocationName)

        verify(mockCalculator).saveTipCalculation(stub.copy(locationName = stubLocationName))
        assertEquals(stubLocationName, calculatorViewModel.locationName)
    }
}