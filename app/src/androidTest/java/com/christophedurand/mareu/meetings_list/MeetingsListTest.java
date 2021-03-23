package com.christophedurand.mareu.meetings_list;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.christophedurand.mareu.ui.ListMeetingActivity;
import com.christophedurand.mareu.R;
import com.christophedurand.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.christophedurand.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeetingsListTest {
    //-- PROPERTIES
    private ListMeetingActivity mListMeetingActivity;

    //-- RULE
    @Rule
    public ActivityTestRule<ListMeetingActivity> mListMeetingActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    //-- SETUP
    // INIT ACTIVITY
    @Before
    public void setUp() {
        mListMeetingActivity = mListMeetingActivityRule.getActivity();

        assertThat(mListMeetingActivity, notNullValue());
    }

    //-- UNIT TESTS METHODS
    /**
     * We test that a meeting is created and then is displayed in our recyclerview
     */
    @Test
    public void myMeetingsList_createAction_shouldNotBeEmpty() {
        // Given: We perform a click on add meeting button, it displays the add meeting activity layout
        onView(ViewMatchers.withId(R.id.add_meeting) ).perform(ViewActions.click() );

        // When: We type the topic of the meeting
        onView(withId(R.id.topic) ).perform(ViewActions.click() );
        onView(withId(R.id.topic) ).perform(ViewActions.typeText("Meeting 1"), ViewActions.closeSoftKeyboard() );
        // We set the date of the meeting
        onView(withId(R.id.date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 03));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click() );
        // We set the time of the meeting
        onView(withId(R.id.time) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName() ) ) ).
                perform(PickerActions.setTime(11, 30) );
        onView(withId(android.R.id.button1) ).perform(ViewActions.click() );
        // We set the place of the meeting
        onView(withId(R.id.place) ).perform(ViewActions.click() );
        onView(withId(R.id.radiobutton_roomC) ).perform(ViewActions.click() );
        onView(withId(R.id.button_ok) ).perform(ViewActions.click() );
        // We type the participants of the meeting
        onView(withId(R.id.add_meeting_scroll_view) ).perform(ViewActions.swipeUp() );
        onView(withId(R.id.participants) ).perform(ViewActions.click() );
        onView(withId(R.id.participants) ).perform(ViewActions.typeText("c.durand@gmail.com"),
                ViewActions.closeSoftKeyboard() );
        // We perform a click on the create button to add the meeting into our list
        onView(withId(R.id.create) ).perform(ViewActions.click() );

        // Then: the meeting is displayed into our recyclerview
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(1) );
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        // Given: We perform a click on add meeting button, it displays the add meeting activity layout
        onView(withId(R.id.add_meeting) ).perform(ViewActions.click() );

        // When: We type the topic of the meeting
        onView(withId(R.id.topic) ).perform(ViewActions.click() );
        onView(withId(R.id.topic) ).perform(ViewActions.typeText("Meeting to delete"),
                ViewActions.closeSoftKeyboard() );
        // We set the date of the meeting
        onView(withId(R.id.date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 03));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click());
        // We set the time of the meeting
        onView(withId(R.id.time) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName() ) ) ).
                perform(PickerActions.setTime(11, 30) );
        onView(withId(android.R.id.button1) ).perform(ViewActions.click());
        // We set the place of the meeting
        onView(withId(R.id.place) ).perform(ViewActions.click() );
        onView(withId(R.id.radiobutton_roomC) ).perform(ViewActions.click() );
        onView(withId(R.id.button_ok) ).perform(ViewActions.click() );
        // We type the participants of the meeting
        onView(withId(R.id.add_meeting_scroll_view) ).perform(ViewActions.swipeUp() );
        onView(withId(R.id.participants) ).perform(ViewActions.click() );
        onView(withId(R.id.participants) ).perform(ViewActions.typeText("c.durand@gmail.com"),
                ViewActions.closeSoftKeyboard() );
        // We perform a click on the create button to add the meeting into our list
        onView(withId(R.id.create) ).perform(ViewActions.click() );
        // The meeting is displayed into our recyclerview
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(2) );
        // We perform a click on the delete button
        onView(withId(R.id.recycler_view_meetings) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction() ) );

        // Then: It removes the meeting from the list
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(1) );

    }

    /**
     * When we tapped the filter date radio button, only the filtered item is shown
     */
    @Test
    public void myMeetingsList_filterAction_shouldFilterItemsByDate() {
        // SECOND MEETING CREATED
        // Given: We perform a click on add meeting button, it displays the add meeting activity layout
        onView(withId(R.id.add_meeting) ).perform(ViewActions.click() );
        // We type the topic of the meeting
        onView(withId(R.id.topic) ).perform(ViewActions.click() );
        onView(withId(R.id.topic) ).perform(ViewActions.typeText("Meeting 2"), ViewActions.closeSoftKeyboard() );
        // We set the date of the meeting
        onView(withId(R.id.date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 04));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click());
        // We set the time of the meeting
        onView(withId(R.id.time) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName() ) ) ).
                perform(PickerActions.setTime(10, 30) );
        onView(withId(android.R.id.button1) ).perform(ViewActions.click());
        // We set the place of the meeting
        onView(withId(R.id.place) ).perform(ViewActions.click() );
        onView(withId(R.id.radiobutton_roomD) ).perform(ViewActions.click() );
        onView(withId(R.id.button_ok) ).perform(ViewActions.click() );
        // We type the participants of the meeting
        onView(withId(R.id.add_meeting_scroll_view) ).perform(ViewActions.swipeUp() );
        onView(withId(R.id.participants) ).perform(ViewActions.click() );
        onView(withId(R.id.participants) ).perform(ViewActions.typeText("christophe.dd@gmail.com"),
                ViewActions.closeSoftKeyboard() );
        // We perform a click on the create button to add the meeting into our list
        onView(withId(R.id.create) ).perform(ViewActions.click() );

        // When: We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );
        // We set the filter by date
        onView(withId(R.id.radiobutton_date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 03));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click() );

        // Then: It only displays the filtered meeting into the list
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(1) );
    }

    /**
     * When we tapped the filter place radio button, only the filtered item is shown
     */
    @Test
    public void myMeetingsList_filterAction_shouldFilterItemsByPlace() {
        // Given: We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );

        // When: We set the filter by place
        onView(withId(R.id.radiobutton_place) ).perform(ViewActions.click() );
        onView(withId(R.id.radiobutton_roomD) ).perform(ViewActions.click() );
        onView(withId(R.id.button_ok) ).perform(ViewActions.click() );

        // Then: It only displays the filtered meeting into the list
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(1) );
    }

    /**
     * When we tapped the no filter radio button, all the items are shown
     */
    @Test
    public void myMeetingsList_filterAction_shouldNotFilterItems() {
        // Given: We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );
        // We set the filter by date
        onView(withId(R.id.radiobutton_date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 03));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click() );
        // We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );
        // We set the filter by place
        onView(withId(R.id.radiobutton_place) ).perform(ViewActions.click() );
        onView(withId(R.id.radiobutton_roomD) ).perform(ViewActions.click() );
        onView(withId(R.id.button_ok) ).perform(ViewActions.click() );

        // When: We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );
        // We don't set any filter
        onView(withId(R.id.radiobutton_without_filter) ).perform(ViewActions.click() );

        // Then: It displays all the meetings into the list
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(2) );
    }

    /**
     * When we tapped the item delete button, while the items are filtered,
     * it only removes the selected item and updates dynamically the meetings list
     */
    @Test
    public void myMeetingsList_filterAndDeleteActions_shouldUpdateList() {
        // Given: We perform a click on the filter button
        onView(withId(R.id.filter_image_button) ).perform(ViewActions.click() );
        // We set the filter by date
        onView(withId(R.id.radiobutton_date) ).perform(ViewActions.click() );
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName() ) ) ).
                perform(PickerActions.setDate(2021, 06, 04));
        onView(withId(android.R.id.button1) ).perform(ViewActions.click() );

        // When: We perform a click on the delete button
        onView(withId(R.id.recycler_view_meetings) )
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction() ) );

        // Then: It displays all the meetings into the list
        onView(withId(R.id.recycler_view_meetings) ).check(withItemCount(1) );
    }

}
