package com.github.sewerina.reek;

import android.content.pm.ActivityInfo;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.startsWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UIMainTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSpinner() {
        onView(withId(R.id.spinner))
                .check(matches(instanceOf(Spinner.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .perform(click());

        onView(withText("Гарь"))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withText(endsWith("тухлых яиц)")))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withText(containsString("свалк")))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withText(startsWith("Химическ")))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));

        onView(withText(startsWith("Выхлоп")))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());

        // Проверить выбор после поворота экрана
        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withText(startsWith("Выхлоп")))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()));
    }

    @Test
    public void testCheckboxes() {
        onView(withText(containsString("Росприрод")))
                .check(matches(instanceOf(CheckBox.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .check(matches(isNotChecked()))
                .perform(click())
                .check(matches(isChecked()));

        onView(withText(startsWith("Губер")))
                .check(matches(instanceOf(CheckBox.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .check(matches(isNotChecked()))
                .perform(click())
                .check(matches(isChecked()));

        // Проверить состояния после поворота экрана
        activityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withText(containsString("Росприрод")))
                .check(matches(isDisplayed()))
                .check(matches(isChecked()));

        onView(withText(startsWith("Губер")))
                .check(matches(isDisplayed()))
                .check(matches(isChecked()));
    }

}
