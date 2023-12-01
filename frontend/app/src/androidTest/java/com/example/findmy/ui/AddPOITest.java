package com.example.findmy.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

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
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.findmy.R;
import com.example.findmy.model.User;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private User testUser;
    private int poiId;

    @Before
    public void before() throws IOException {
        Intents.init();

        testUser = FindMyTest.createTestUser();
    }

    @After
    public void after() throws IOException {
        Intents.release();
        FindMyTest.deleteMyPOI(poiId);
    }

    @Test
    public void addPOITest() throws UiObjectNotFoundException {
        // Setup test bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap placeholder = Bitmap.createBitmap(1, 1, conf);

        // Build a result to return from camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", placeholder);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // stub out camera
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        reachAddPOISheet();

        // Check POI name editText exists and replace text
        ViewInteraction nameText = onView(
                allOf(
                        withId(R.id.editTextText),
                        isDisplayed()
                )
        );

        nameText.check(matches(isDisplayed()));
        nameText.perform(replaceText(FindMyTest.testPOIDescription), closeSoftKeyboard(), pressImeActionButton());

        // Check change photo button exists
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.addImageButton), withText("Change Image"),
                        isDisplayed()));
        materialButton.perform(click());
        materialButton.perform(click());

        // check submit button exists and click
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.submit_button),
                        isDisplayed()));
        materialButton2.check(matches(isDisplayed()));
        materialButton2.perform(click());

        // Check new POI marker exists
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiObject poiMarker = device.findObject(new UiSelector().descriptionContains(FindMyTest.testPOIDescription));

        poiMarker.click();

        String idText = FindMyTest.getText(allOf(
                        withId(R.id.poi_id_text),
                        isDisplayed()
                )
        );

        poiId = Integer.parseInt(idText);
    }

    @Test
    public void testNoImageFail() {
        reachAddPOISheet();
        // check submit button exists and click
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.submit_button),
                        isDisplayed()));
        materialButton2.check(matches(isDisplayed()));
        materialButton2.perform(click());

        // Test toast message is displayed
        onView(withText(R.string.err_missing_img)).inRoot(new ToastMatcher()).check(matches(isDisplayed()));
    }

    private void reachAddPOISheet() {
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

        // Check and click Add Button
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.newPOIButton), withContentDescription("Add POI"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_home),
                                        0),
                                1),
                        isDisplayed()));
        floatingActionButton.check(matches(isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction bottomSheet = onView(
                allOf(
                        withId(R.id.add_poi_bottom_sheet),
                        isDisplayed()
                )
        );
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

    public void resetTestUser() throws IOException {
        URL url = new URL("https://findastar.westus2.cloudapp.azure.com/user/email/zhao1939@gmail.com/all");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();

        assertEquals(204, responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        System.out.println("Response: " + response.toString());
    }
}
