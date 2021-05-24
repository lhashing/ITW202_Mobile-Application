package edu.gcit.todo_20_i;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("edu.gcit.todo_4", appContext.getPackageName());
    }

    @Test
    public void activityLaunch() {
        onView(withId(R.id.Button)).perform(click());
        onView(withId(R.id.Header_Message)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.ReplyHeader)).check(matches(isDisplayed()));
    }

    @Test
    public void textInputOutput() {
        onView(withId(R.id.editText_send)).perform(typeText("This is a test."));
        onView(withId(R.id.Button)).perform(click());
        onView(withId(R.id.textRecieved)).check(matches(withText("This is a test.")));
    }
}