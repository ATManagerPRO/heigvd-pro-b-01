package com.heig.atmanager;

import androidx.annotation.ContentView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.heig.atmanager.ui.AddTaskGoalActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddTaskTest {

    @Rule
    public ActivityTestRule<AddTaskGoalActivity> activityActivityTestRule = new ActivityTestRule<>(AddTaskGoalActivity.class);

    @Test
    public void inputTest(){
        String s = "Title";
        onView(withId(R.id.frag_add_taks_task_title)).
                perform(typeText(s)).check(matches(withText(s)));

    }
}
