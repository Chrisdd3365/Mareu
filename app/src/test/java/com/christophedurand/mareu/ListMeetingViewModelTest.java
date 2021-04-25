package com.christophedurand.mareu;


import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;
import com.christophedurand.mareu.ui.add.AddMeetingViewModel;
import com.christophedurand.mareu.ui.list.ListMeetingViewModel;
import com.christophedurand.mareu.ui.list.MeetingViewState;
import com.christophedurand.mareu.ui.list.MeetingViewStateItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class ListMeetingViewModelTest {

    private static final String TOPIC = "TOPIC";
    private static final String PARTICIPANTS = "PARTICIPANTS";
    private static final String PLACE = "PLACE";


    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final Application application = Mockito.mock(Application.class);
    private final ListMeetingRepository listMeetingRepository = Mockito.mock(ListMeetingRepository.class);

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

    private ListMeetingViewModel listMeetingViewModel;
    private AddMeetingViewModel addMeetingViewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(meetingsLiveData).when(listMeetingRepository).getMeetingsList();
        Mockito.doReturn("%s - %s - %s")
                .when(application)
                .getString(R.string.meeting_title_preset, any(), any(), any());

        listMeetingViewModel = new ListMeetingViewModel(application, listMeetingRepository);

        addMeetingViewModel = new AddMeetingViewModel(listMeetingRepository);
    }

    @Test
    public void nominal_case() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            meetings.add(new Meeting(
                    "" + i,
                    LocalDate.of(2021, 4, 12),
                    PLACE + i,
                    TOPIC + i,
                    PARTICIPANTS + i,
                    R.drawable.date_white_24dp
                )
            );
        }
        meetingsLiveData.setValue(meetings);

        // When
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(listMeetingViewModel.getMeetingViewStateLiveData());

        // Then
        assertEquals(getDefaultMeetingViewState(), meetingViewState);
    }

    @Test
    public void when_addButtonClicked_should_display_new_meeting() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        int i;
        for (i = 0; i < 3; i++) {
            meetings.add(new Meeting(
                            "" + i,
                            LocalDate.of(2021, 4, 12),
                            PLACE + i,
                            TOPIC + i,
                            PARTICIPANTS + i,
                            R.drawable.date_white_24dp
                    )
            );
        }
        meetingsLiveData.setValue(meetings);

        // When
        Meeting meeting = new Meeting(
                "" + i,
                LocalDate.of(2021, 4, 12),
                PLACE + i,
                TOPIC + i,
                PARTICIPANTS + i,
                R.drawable.date_white_24dp
        );
        addMeetingViewModel.onCreateMeetingButtonClicked(meeting);
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(listMeetingViewModel.getMeetingViewStateLiveData());

        // Then
        List<MeetingViewStateItem> expected = new ArrayList<>();
        for (i = 0; i < 4; i++) {
            expected.add(
                    new MeetingViewStateItem(
                            "" + i,
                            TOPIC + i + " - 2021/04/12 - " + PLACE + i,
                            R.drawable.date_white_24dp,
                            PARTICIPANTS + i,
                            PLACE + i,
                            LocalDate.of(2021, 4, 12)
                    )
            );
        }

        assertEquals(new MeetingViewState(expected), meetingViewState);
    }

    @Test
    public void when_deleteButtonClicked_should_remove_selected_meeting() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        int i;
        for (i = 0; i < 3; i++) {
            meetings.add(new Meeting(
                            "" + i,
                            LocalDate.of(2021, 4, 12),
                            PLACE + i,
                            TOPIC + i,
                            PARTICIPANTS + i,
                            R.drawable.date_white_24dp
                    )
            );
        }
        meetingsLiveData.setValue(meetings);

        // When
        listMeetingViewModel.onDeleteMeetingButtonClicked("" + 2);
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(listMeetingViewModel.getMeetingViewStateLiveData());

        // Then
        List<MeetingViewStateItem> expected = new ArrayList<>();
        for (i = 0; i < 2; i++) {
            expected.add(
                    new MeetingViewStateItem(
                            "" + i,
                            TOPIC + i + " - 2021/04/12 - " + PLACE + i,
                            R.drawable.date_white_24dp,
                            PARTICIPANTS + i,
                            PLACE + i,
                            LocalDate.of(2021, 4, 12)
                    )
            );
        }
        assertEquals(new MeetingViewState(expected), meetingViewState);
    }

    @Test
    public void when_filterByPlaceButtonClicked_should_display_filtered_meetings_by_place() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        String meetingPlace = PLACE + 2;

        int i;
        for (i = 0; i < 3; i++) {
            meetings.add(new Meeting(
                            "" + i,
                            LocalDate.of(2021, 4, 12),
                            PLACE + i,
                            TOPIC + i,
                            PARTICIPANTS + i,
                            R.drawable.date_white_24dp
                    )
            );

        }

        meetings.add(new Meeting(
                        "" + i,
                        LocalDate.of(2021, 4, 12),
                        meetingPlace,
                        TOPIC + i,
                        PARTICIPANTS + i,
                        R.drawable.date_white_24dp
                )
        );
        meetingsLiveData.setValue(meetings);

        // When
        listMeetingViewModel.onFilterMeetingsByPlaceButtonClicked(meetingPlace);
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(listMeetingViewModel.getMeetingViewStateLiveData());

        // Then
        List<MeetingViewStateItem> expected = new ArrayList<>();
        expected.add(
                new MeetingViewStateItem(
                        "3",
                        TOPIC + 3 + " - 2021/04/19 - " + PLACE + 3,
                        R.drawable.date_white_24dp,
                        PARTICIPANTS + 3,
                        meetingPlace,
                        LocalDate.of(2021, 4, 12)
                )
        );
        assertEquals(new MeetingViewState(expected), meetingViewState);
    }

    @Test
    public void when_filterByDateButtonClicked_should_display_filtered_meetings_by_date() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        LocalDate meetingDate = LocalDate.of(2021,4,19);

        int i;
        for (i = 0; i < 3; i++) {
            meetings.add(new Meeting(
                            "" + i,
                            LocalDate.of(2021, 4, 12),
                            PLACE + i,
                            TOPIC + i,
                            PARTICIPANTS + i,
                            R.drawable.date_white_24dp
                    )
            );

        }

        meetings.add(new Meeting(
                        "" + i,
                        meetingDate,
                        PLACE + i,
                        TOPIC + i,
                        PARTICIPANTS + i,
                        R.drawable.date_white_24dp
                )
        );
        meetingsLiveData.setValue(meetings);

        // When
        listMeetingViewModel.onFilterMeetingsByDateButtonClicked(meetingDate);
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(listMeetingViewModel.getMeetingViewStateLiveData());

        // Then
        List<MeetingViewStateItem> expected = new ArrayList<>();
        expected.add(
                new MeetingViewStateItem(
                        "3",
                        TOPIC + 3 + " - 2021/04/19 - " + PLACE + 3,
                        R.drawable.date_white_24dp,
                        PARTICIPANTS + 3,
                        PLACE + 3,
                        meetingDate
                )
        );
        assertEquals(new MeetingViewState(expected), meetingViewState);
    }


    private MeetingViewState getDefaultMeetingViewState() {
        List<MeetingViewStateItem> items = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            items.add(
                new MeetingViewStateItem(
                    "" + i,
                    TOPIC + i + " - 2021/04/12 - " + PLACE + i,
                    R.drawable.date_white_24dp,
                    PARTICIPANTS + i,
                    PLACE + i,
                    LocalDate.of(2021, 4, 12)
                )
            );
        }

        return new MeetingViewState(items);
    }

}
