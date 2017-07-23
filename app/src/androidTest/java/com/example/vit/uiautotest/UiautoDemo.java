package com.example.vit.uiautotest;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by vit on 7/18/17.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UiautoDemo {
    private static final String BASIC_PACKAGE = "com.ss.android.article.news";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreem(){
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testArticle(){
        UiObject listview = mDevice.findObject(new UiSelector().resourceId("android:id/list").className("android.widget.ListView"));
        if (listview.exists()){
            try {
                listview.swipeUp(5);
            } catch (UiObjectNotFoundException e) {
                e.printStackTrace();
            }
        }

        UiObject video = mDevice.findObject(new UiSelector().resourceId("com.ss.android.article.news:id/b_n"));
        try {
            if(video.isClickable()){
                try {
                    video.clickAndWaitForNewWindow(LAUNCH_TIMEOUT);
                } catch (UiObjectNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.i("UiAutomator","the test case isnot running.");
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}
