package com.ratik.tipcalculator

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.ratik.tipcalculator.view.TipCalculatorActivity
import org.junit.Rule
import org.junit.Test

class TipCalculatorActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(TipCalculatorActivity::class.java)

    @Test
    fun testTipCalculator() {
        // Calculate Tip
        enter(checkAmount = 1250.00, tipPercent = 15)
        calculateTip()
        assertOutput(name = "", checkAmount = "₹1250.00", tipAmount = "₹187.50", total = "₹1437.50")

        // Save Tip
        saveTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "₹1250.00", tipAmount = "₹187.50", total = "₹1437.50")

        // Clear Outputs
        clearOutputs()
        assertOutput(name = "", checkAmount = "₹0.00", tipAmount = "₹0.00", total = "₹0.00")

        // Load Tip
        loadTip(name = "BBQ Max")
        assertOutput(name = "BBQ Max", checkAmount = "₹1250.00", tipAmount = "₹187.50", total = "₹1437.50")
    }

    private fun enter(checkAmount: Double, tipPercent: Int) {
        onView(withId(R.id.input_check_amount)).perform(replaceText(checkAmount.toString()))
        onView(withId(R.id.input_tip_percentage)).perform(replaceText(tipPercent.toString()))
    }

    private fun calculateTip() {
        onView(withId(R.id.fab)).perform(click())
    }

    private fun assertOutput(name: String, checkAmount: String, tipAmount: String, total: String) {
        onView(withId(R.id.bill_amount)).check(matches(withText(checkAmount)))
        onView(withId(R.id.tip_amount)).check(matches(withText(tipAmount)))
        onView(withId(R.id.grand_total_amount)).check(matches(withText(total)))
        onView(withId(R.id.calculation_name)).check(matches((withText(name))))
    }

    private fun clearOutputs() {
        enter(checkAmount = 0.0, tipPercent = 0)
        calculateTip()
    }

    private fun saveTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText(R.string.action_save)).perform(click())
        onView(withHint(R.string.save_hint)).perform(replaceText(name))
        onView(withText(R.string.action_save)).perform(click())
    }

    private fun loadTip(name: String) {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getContext())
        onView(withText(R.string.action_load)).perform(click())
        onView(withText(name)).perform(click())
    }

}