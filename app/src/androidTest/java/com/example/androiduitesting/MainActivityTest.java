package com.example.androiduitesting;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new
            ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Test
    public void testAddCity(){
        // Click on Add City button
        onView(withId(R.id.button_add)).perform(click());
        // Type "Edmonton" in the editText
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        // Click on Confirm
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if text "Edmonton" is matched with any of the text displayed on the screen
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity(){
        // Add first city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        //Add another city to the list
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());
        //Clear the list
        onView(withId(R.id.button_clear)).perform(click());
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }
    @Test
    public void testListView(){
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        // Check if in the Adapter view (given id of that adapter view), there is a data
        // (which is an instance of String) located at position zero.
        // If this data matches the text we provided then Voila! Our test should pass
        // You can also use anything() in place of is(instanceOf(String.class))
        onData(is(instanceOf(String.class)))
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0).check(matches((withText("Edmonton"))));
    }

    @Test
    public void testSwitchToShowActivity() {
        Intents.init();
        // Add a city
        try {
            onView(withId(R.id.button_add)).perform(click());
            onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Calgary"));
            onView(withId(R.id.button_confirm)).perform(click());

            // Click on newly created city
            onData(is("Calgary")).inAdapterView(withId(R.id.city_list)).perform(click());

            // Checks if correct intent is called
            intended(hasComponent(ShowActivity.class.getName()));
            onView(withId(R.id.backButton)).check(matches(isDisplayed()));
        } finally {
            Intents.release();
        }
    }

    @Test
    public void testConsistentCityName() {
        // Add a city
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click on newly created city
        onData(is("Vancouver")).inAdapterView(withId(R.id.city_list)).perform(click());

        // Check to see if specific city is displayed in ShowActivity
        onView(withText("Vancouver")).check(matches(isDisplayed()));

        // Click on back button
        onView(withId(R.id.backButton)).perform(click());

        // Check to see if specific city is still displayed in MainActivity
        onView(withText("Vancouver")).check(matches(isDisplayed()));

        onView(withId(R.id.button_clear)).perform(click());
    }

    @Test
    public void testBackButton() {
            onView(withId(R.id.button_add)).perform(click());
            onView(withId(R.id.editText_name)).perform(ViewActions.typeText("Edmonton"));
            onView(withId(R.id.button_confirm)).perform(click());

            onData(is("Edmonton")).inAdapterView(withId(R.id.city_list)).perform(click());
            onView(withId(R.id.backButton)).perform(click());

            // checking for presence of button that does not exist in ShowActivity, surely there must be a more robust way?
            onView(withId(R.id.button_clear)).check(matches(isDisplayed()));
    }

}