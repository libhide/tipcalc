package com.ratik.tipcalculator.model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var repository: TipCalculationRepository

    @Before
    fun setup() {
        repository = TipCalculationRepository()
    }

    @Test
    fun testSaveTip() {
        val tip = TipCalculation(
                locationName = "Pancake Paradise",
                checkAmount = 700.00, tipPct = 15,
                tipAmount = 105.00, grandTotal = 805.00)
        repository.saveTipCalculation(tip)

        assertEquals(tip, repository.loadTipCalculationByName(tip.locationName))
    }

    @Test
    fun testLoadSavedTipCalculations() {
        val tip1 = TipCalculation(
                locationName = "Pancake Paradise",
                checkAmount = 700.00, tipPct = 15,
                tipAmount = 105.00, grandTotal = 805.00)
        val tip2 = TipCalculation(locationName = "Non-Veggie Sensation",
                checkAmount = 700.00, tipPct = 15,
                tipAmount = 105.00, grandTotal = 805.00)

        repository.saveTipCalculation(tip1)
        repository.saveTipCalculation(tip2)

        repository.loadSavedTipCalculations().observeForever { tipCalculations ->
            assertEquals(2, tipCalculations?.size)
        }
    }
}