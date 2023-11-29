package com.example.findmy.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.findmy.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddPOITest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void addPOITest() {
        ViewInteraction fx = onView(
                allOf(withText("Sign in"),
                        childAtPosition(
                                allOf(withId(R.id.sign_in_button),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                0),
                        isDisplayed()));
        fx.perform(click());

        ViewInteraction view = onView(
                allOf(withContentDescription("Google Map"),
                        withParent(withParent(withId(R.id.map))),
                        isDisplayed()));
        view.check(matches(isDisplayed()));

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.newPOIButton), withContentDescription("Add POI"),
                        withParent(withParent(withId(R.id.nav_host_fragment_activity_home))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.newPOIButton), withContentDescription("Add POI"),
                        withParent(withParent(withId(R.id.nav_host_fragment_activity_home))),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.newPOIButton), withContentDescription("Add POI"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_home),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withParent(allOf(withId(com.google.android.material.R.id.design_bottom_sheet),
                                withParent(withId(com.google.android.material.R.id.coordinator)))),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextText), withText("POI Name"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.design_bottom_sheet),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Test Add POI"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextText), withText("Test Add POI"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.design_bottom_sheet),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(closeSoftKeyboard());

        Matcher editTextMatcher =
                allOf(withId(R.id.editTextText), withText("Test Add POI"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.design_bottom_sheet),
                                        0),
                                1),
                        isDisplayed()
                );

        ViewInteraction appCompatEditText3 = onView(editTextMatcher);
        appCompatEditText3.perform();

        ViewInteraction ratingBar = onView(
                allOf(withId(R.id.ratingBar), isDisplayed())
        );

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button), withText("Submit"),
                        isDisplayed()));
        materialButton.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
