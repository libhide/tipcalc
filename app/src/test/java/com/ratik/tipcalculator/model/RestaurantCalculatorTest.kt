package com.ratik.tipcalculator.model

import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class RestaurantCalculatorTest {
    lateinit var calculator: RestaurantCalculator

    @Before
    fun setup() {
        calculator = RestaurantCalculator()
    }

    @Test
    fun testCalculateTip() {
        val baseTc = TipCalculation(checkAmount = 1176.0)

        val testCalculations = listOf(
                baseTc.copy(tipPct = 10, tipAmount = 117.6, grandTotal = 1293.6),
                baseTc.copy(tipPct = 5, tipAmount = 58.8, grandTotal = 1234.8),
                baseTc.copy(tipPct = 13, tipAmount = 152.88, grandTotal = 1328.88)
        )

        testCalculations.forEach {
            Assert.assertEquals(it, calculator.calculateTip(it.checkAmount, it.tipPct))
        }
    }
}