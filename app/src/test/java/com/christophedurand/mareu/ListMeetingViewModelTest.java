package com.christophedurand.mareu;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;
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

@RunWith(MockitoJUnitRunner.class)
public class ListMeetingViewModelTest {

    private static final String TOPIC = "TOPIC";
    private static final String PARTICIPANTS = "PARTICIPANTS";
    private static final String PLACE = "PLACE";

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final ListMeetingRepository listMeetingRepository = Mockito.mock(ListMeetingRepository.class);

    private final MutableLiveData<List<Meeting>> meetingsLiveData = new MutableLiveData<>();

    private ListMeetingViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(meetingsLiveData).when(listMeetingRepository).getMeetingsList();

        viewModel = new ListMeetingViewModel(listMeetingRepository);
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
        MeetingViewState meetingViewState = LiveDataTestUtils.getOrAwaitValue(viewModel.getMeetingsListLiveData());

        // Then
        assertEquals(getDefaultMeetingViewState(), meetingViewState);
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
