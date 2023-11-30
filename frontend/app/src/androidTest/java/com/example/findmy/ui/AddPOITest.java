package com.example.findmy.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.findmy.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
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
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.CAMERA");

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule;

    @Before
    public void before() {
        Intents.init();
    }

    @After
    public void after() {
        Intents.release();
    }

    @Test
    public void addPOITest() {
        // Setup test bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap placeholder = Bitmap.createBitmap(1, 1, conf);

        // Build a result to return from camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", placeholder);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // stub out camera
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);


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

        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.newPOIButton), withContentDescription("Add POI"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_home),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.perform(click());

//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.spinner),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(com.google.android.material.R.id.design_bottom_sheet),
//                                        0),
//                                2),
//                        isDisplayed()));
//        appCompatSpinner.perform(click());
//
//        DataInteraction materialTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(3);
//        materialTextView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addImageButton), withText("Change Image"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.design_bottom_sheet),
                                        0),
                                6),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.submit_button),
                        isDisplayed()));
        materialButton2.perform(click());
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
