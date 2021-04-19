package com.christophedurand.mareu;


import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;
import com.christophedurand.mareu.ui.list.ListMeetingViewModel;
import com.christophedurand.mareu.ui.list.MeetingViewState;
import com.christophedurand.mareu.ui.list.MeetingViewStateItem;
import com.christophedurand.mareu.utils.MainApplication;

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

    private ListMeetingViewModel viewModel;


    @Before
    public void setUp() {
        Mockito.doReturn(meetingsLiveData).when(listMeetingRepository).getMeetingsList();
        Mockito.doReturn("%s - %s - %s")
                .when(application)
                .getString(R.string.meeting_title_preset, any(), any(), any());

        viewModel = new ListMeetingViewModel(application, listMeetingRepository);
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
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingViewStateLiveData());

        // Then
        assertEquals(getDefaultMeetingViewState(), meetingViewState);
    }

    //TODO: côté repo
    @Test
    public void when_addButtonClicked_should_display_new_meeting() throws InterruptedException {
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

        meetings.add(new Meeting("" + 3,
                                LocalDate.of(2021,4,12),
                                PLACE + 3,
                                TOPIC + 3,
                                PARTICIPANTS + 3,
                                R.drawable.date_white_24dp
                )
        );
        meetingsLiveData.setValue(meetings);

        // When
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingViewStateLiveData());

        // Then
        assertEquals(getAddedMeetingViewState(), meetingViewState);
    }

    //TODO: côté repo
    @Test
    public void when_deleteButtonClicked_should_remove_selected_meeting() throws InterruptedException {
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

        meetings.remove(new Meeting("" + 3,
                        LocalDate.of(2021,4,12),
                        PLACE + 3,
                        TOPIC + 3,
                        PARTICIPANTS + 3,
                        R.drawable.date_white_24dp
                )
        );
        meetingsLiveData.setValue(meetings);

        // When
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingViewStateLiveData());

        // Then
        assertEquals(getDeletedMeetingViewState(), meetingViewState);
    }

    @Test
    public void when_filterByPlaceButtonClicked_should_display_filtered_meetings_by_place() throws InterruptedException {
        // Given
        List<Meeting> meetings = new ArrayList<>();
        String meetingPlace = PLACE + 2;

        for (int i = 0; i < 4; i++) {
            meetings.add(new Meeting(
                            "" + i,
                            LocalDate.of(2021, 4, 12),
                            meetingPlace,
                            TOPIC + i,
                            PARTICIPANTS + i,
                            R.drawable.date_white_24dp
                    )
            );
        }
        meetingsLiveData.setValue(meetings);

        // When
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingViewStateLiveData());

        // Then
        assertEquals(getFilteredMeetingByPlaceViewState(meetingPlace), meetingViewState);
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
        viewModel.onFilterMeetingsByDateButtonClicked(meetingDate);
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingViewStateLiveData());

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

    private MeetingViewState getAddedMeetingViewState() {
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
        items.add(
                new MeetingViewStateItem(
                        "" + 3,
                        TOPIC + 3 + " - 2021/04/12 - " + PLACE + 3,
                        R.drawable.date_white_24dp,
                        PARTICIPANTS + 3,
                        PLACE + 3,
                        LocalDate.of(2021, 4, 12)
                )
        );

        return new MeetingViewState(items);
    }

    private MeetingViewState getDeletedMeetingViewState() {
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
        items.remove(
                new MeetingViewStateItem(
                        "" + 3,
                        TOPIC + 3 + " - 2021/04/12 - " + PLACE + 3,
                        R.drawable.date_white_24dp,
                        PARTICIPANTS + 3,
                        PLACE + 3,
                        LocalDate.of(2021, 4, 12)
                )
        );

        return new MeetingViewState(items);
    }

    private MeetingViewState getFilteredMeetingByPlaceViewState(String placeFilter) {
        List<Meeting> meetingsList = listMeetingRepository.getMeetingsList().getValue();
        List<MeetingViewStateItem> items = new ArrayList<>();

        for (int i = 0; i < meetingsList.size(); i++) {
            Meeting filteredMeeting = meetingsList.get(i);
            String filteredMeetingPlace = filteredMeeting.getPlace();

            if (filteredMeetingPlace.equals(placeFilter)) {
                items.add(
                        new MeetingViewStateItem(
                                filteredMeeting.getId(),
                                filteredMeeting.getTopic() + " - 2021/04/12 - " + filteredMeeting.getPlace(),
                                filteredMeeting.getAvatar(),
                                filteredMeeting.getParticipants(),
                                filteredMeeting.getPlace(),
                                filteredMeeting.getDate()
                        )
                );
            }
        }

        return new MeetingViewState(items);
    }

}
