package com.example.simpleinvoicegenerationapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpActivityInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun testSignUpSuccess() {
        // Fill in valid sign-up information
        onView(withId(R.id.etFullName)).perform(typeText("Patrick"), closeSoftKeyboard())
        onView(withId(R.id.etEmailSignUp)).perform(
            typeText("patrick@gmail.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.etPasswordSignUp)).perform(typeText("patrick"), closeSoftKeyboard())

        // Click on the sign-up button
        onView(withId(R.id.btnSignUp)).perform(click())

        // Check if the login activity is launched
        onView(withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }
}