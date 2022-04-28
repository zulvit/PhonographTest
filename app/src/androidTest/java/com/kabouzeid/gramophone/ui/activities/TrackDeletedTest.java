package com.kabouzeid.gramophone.ui.activities;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.kabouzeid.gramophone.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TrackDeletedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void trackDeletedTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withText("Get started"),
                        childAtPosition(
                                allOf(withId(R.id.mi_button_cta),
                                        childAtPosition(
                                                withId(R.id.mi_frame),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction tabView = onView(
                allOf(withContentDescription("PLAYLISTS"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tabs),
                                        0),
                                4),
                        isDisplayed()));
        tabView.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler_view),
                                childAtPosition(
                                        withId(R.id.container),
                                        0)),
                        3),
                        isDisplayed()));
        frameLayout.perform(click());

        ViewInteraction iconImageView = onView(
                allOf(withId(R.id.menu),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        1),
                                2),
                        isDisplayed()));
        iconImageView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("Remove from playlist"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction mDButton = onView(
                allOf(withId(R.id.md_buttonDefaultPositive), withText("Remove"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        mDButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(android.R.id.empty), withText("Empty playlist"),
                        withParent(withParent(withId(R.id.content_container))),
                        isDisplayed()));
        textView.check(matches(withText("Empty playlist")));
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
