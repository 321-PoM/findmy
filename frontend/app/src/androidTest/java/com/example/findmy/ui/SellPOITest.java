package com.example.findmy.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
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
import com.example.findmy.model.POI;
import com.example.findmy.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class SellPOITest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    private POI testMyPOI;
    private final String testPrice = "100";
    private User testUser;

    @Before
    public void before() throws IOException {
        testUser = FindMyTest.createTestUser(FindMyTest.testEmail);
        testMyPOI = FindMyTest.createMyPOI(testUser);
    }

    @After
    public void after() throws IOException {
        FindMyTest.deleteMyPOI(testMyPOI.getId());
    }

    @Test
    public void sellPOITest() {
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

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.viewDetailsButton), withText("Details"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.myPOIRecycler),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.inputListingPrice),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.createListingLayout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText(testPrice), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.inputListingPrice), withText(testPrice),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.createListingLayout),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());


        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.list_button), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.createListingLayout),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        // goto marketplace
        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.navigation_marketplace), withContentDescription("Marketplace"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        // view marketplace listing
        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.viewDetailsButton), withText("Details"),
                        childAtPosition(
                                allOf(withId(R.id.myPOIRow),
                                        childAtPosition(
                                                withId(R.id.listingsRecylcer),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton3.perform(click());

        // Assert listing exists
        ViewInteraction textView = onView(
                allOf(withId(R.id.poi_name), withText(FindMyTest.testPOIDescription),
                        withParent(withParent(withId(com.google.android.material.R.id.design_bottom_sheet))),
                        isDisplayed()));
        textView.check(matches(withText(FindMyTest.testPOIDescription)));

        // Assert price is correct
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.existingListingPriceText), withText(testPrice),
                        withParent(withParent(withId(R.id.listingPriceLayout))),
                        isDisplayed()));
        textView2.check(matches(withText(testPrice)));

        ViewInteraction view = onView(
                allOf(withId(com.google.android.material.R.id.touch_outside),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.coordinator),
                                        childAtPosition(
                                                withId(com.google.android.material.R.id.container),
                                                0)),
                                0),
                        isDisplayed()));
        view.perform(click());

        // go back to profile
        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.navigation_profile), withContentDescription("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                3),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.viewDetailsButton), withText("Details"),
                        childAtPosition(
                                allOf(withId(R.id.myPOIRow),
                                        childAtPosition(
                                                withId(R.id.myPOIRecycler),
                                                0)),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.unlist_button), withText("Unlist"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.unlistLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton6.perform(click());
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
