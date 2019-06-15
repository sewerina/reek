package com.github.sewerina.reek;

import android.content.pm.ActivityInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.github.sewerina.reek.ui.AppInfoActivity;
import com.github.sewerina.reek.ui.MainActivity;
import com.github.sewerina.reek.ui.MapActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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

    @Test
    public void testMenu() {
        Intents.init();

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        onView(withText(R.string.menuItem_appInfo))
                .check(matches(instanceOf(AppCompatTextView.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .perform(click());

        intended(hasComponent(AppInfoActivity.class.getName()));

        onView(withId(R.id.tv_appInfo))
                .check(matches(instanceOf(TextView.class)))
                .check(matches(isDisplayed()));

        Intents.release();
    }

    @Test
    public void testSelectLocationBtn() {
        Intents.init();

        onView(withId(R.id.iv_mapScreen)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibtn_deleteScreen)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.btn_selectLocation))
                .check(matches(instanceOf(Button.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .perform(click());

        intended(hasComponent(MapActivity.class.getName()));

        onView(withText(R.string.tv_addMarker))
                .check(matches(instanceOf(TextView.class)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.map)).check(matches(isDisplayed()));

        onView(withId(R.id.fab_save))
                .check(matches(instanceOf(Button.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .perform(click());

        onView(withId(R.id.iv_mapScreen))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(instanceOf(ImageView.class)))
                .check(matches(isDisplayed()));

        onView(withId(R.id.ibtn_deleteScreen))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(matches(instanceOf(ImageButton.class)))
                .check(matches(isDisplayed()))
                .check(matches(isEnabled()))
                .check(matches(isClickable()))
                .perform(click());

        onView(withId(R.id.iv_mapScreen)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.ibtn_deleteScreen)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        Intents.release();
    }
}
