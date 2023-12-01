package com.example.findmy.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.findmy.model.POI;
import com.example.findmy.model.POIRequest;
import com.example.findmy.model.User;
import com.example.findmy.network.FindMyService;

import org.hamcrest.Matcher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Response;

public class FindMyTest {
    static private Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    static Bitmap placeholderBitmap = Bitmap.createBitmap(1, 1, conf);

    public final static String testEmail = "test_user@gmail.com";

    public final static String testPOIDescription = "testMyPOI";


    public static final FindMyService findMyServiceTest = new FindMyService();
    private static String testBathroomDescription = "testBathroom";

    public static File getPlaceholderImage(){
        File poiImageFile;
        OutputStream poiImageStream;
        try {
            poiImageFile = File.createTempFile("poi", ".jpeg");
            poiImageStream = new FileOutputStream(poiImageFile);
        } catch (IOException | SecurityException e) {
            throw new RuntimeException(e);
        }


        if (placeholderBitmap != null) {
            placeholderBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , poiImageStream);
            try {
                poiImageStream.flush();
                poiImageStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return poiImageFile;
    }

    public static void deleteUser(User user) throws IOException {
        Call<User> userCall = findMyServiceTest.deleteUser(user.getId());

        Response<User> userResponse = userCall.execute();

        assertTrue(userResponse.isSuccessful());
    }

    public static User createTestUser(String email) throws IOException {
        Call<User> currentUserCall = FindMyTest.findMyServiceTest.getUserByEmail(email, true);

        Response<User> response = currentUserCall.execute();

        assertTrue(response.isSuccessful());

        User testUser = response.body();

        return testUser;
    }

    public static POI createBathroomPOI(User testUser) throws IOException {
        int ownerId = testUser.getId();
        int rating = 3;

        float lat = 49.2615F;
        float lng = -123.2493F;
        POIRequest poi = new POIRequest(lat, lng, "Bathroom", "verified", testBathroomDescription, ownerId, rating, FindMyTest.getPlaceholderImage());

        Call<POI> poiCall = FindMyTest.findMyServiceTest.createPOI(poi);

        Response<POI> response = poiCall.execute();

        return response.body();
    }

    public static POI createMyPOI(User testUser) throws IOException {

        int ownerId = testUser.getId();
        int rating = 3;

        float lat = 49.2615F;
        float lng = -123.2493F;
        POIRequest poi = new POIRequest(lat, lng, "myPOI", "verified", testPOIDescription, ownerId, rating, FindMyTest.getPlaceholderImage());

        Call<POI> poiCall = FindMyTest.findMyServiceTest.createPOI(poi);

        Response<POI> response = poiCall.execute();

        assertTrue(response.isSuccessful());
        return response.body();
    }

    public static void deleteMyPOI(int id) throws IOException {
        Call<POI> poiCall = FindMyTest.findMyServiceTest.deletePOI(id);

        Response<POI> poiResponse = poiCall.execute();

//        assertTrue(poiResponse.isSuccessful());
    }

    public static String getText(final Matcher<View> matcher) {
        try {
            final String[] stringHolder = {null};
            onView(matcher).perform(new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return isAssignableFrom(TextView.class);
                }

                @Override
                public String getDescription() {
                    return "get text";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    TextView tv = (TextView) view;
                    stringHolder[0] = tv.getText().toString();
                }
            });
            if (stringHolder[0] == null || stringHolder[0].equals("")) {
                fail("no text found");
            }
            return stringHolder[0];
        } catch (Exception e) {
            fail("null found");
            return null;
        }

    }
}
