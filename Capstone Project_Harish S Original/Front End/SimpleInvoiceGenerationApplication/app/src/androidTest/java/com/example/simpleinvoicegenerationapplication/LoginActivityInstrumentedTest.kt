package com.example.simpleinvoicegenerationapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityInstrumentedTest {

    private lateinit var activityScenario: ActivityScenario<LoginActivity>

    @Before
    fun setUp() {
        // Launch the LoginActivity before each test
        activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun testLoginSuccess() {
        // Fill in valid login information
        onView(withId(R.id.et_username)).perform(typeText("valid_username"), closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(typeText("valid_password"), closeSoftKeyboard())

        // Click on the login button
        onView(withId(R.id.btn_signup)).perform(click())

    }

}