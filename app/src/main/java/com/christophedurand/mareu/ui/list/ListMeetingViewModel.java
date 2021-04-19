package com.christophedurand.mareu.ui.list;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.christophedurand.mareu.R;
import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ListMeetingViewModel extends ViewModel {

    private final Application mApplication;

    private final ListMeetingRepository mListMeetingRepository;

    private final MediatorLiveData<MeetingViewState> mMeetingViewStateLiveData = new MediatorLiveData<>();

    private final MutableLiveData<String> mPlaceFilterMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> mDateFilterMutableLiveData = new MutableLiveData<>();


    public ListMeetingViewModel(Application application, ListMeetingRepository listMeetingRepository) {

        mApplication = application;

        mListMeetingRepository = listMeetingRepository;

        mMeetingViewStateLiveData.addSource(listMeetingRepository.getMeetingsList(), meetingsList ->
                combineMeetingsAndSorting(
                        meetingsList,
                        mPlaceFilterMutableLiveData.getValue(),
                        mDateFilterMutableLiveData.getValue()
                )
        );

        mMeetingViewStateLiveData.addSource(mPlaceFilterMutableLiveData, placeFilter ->
                combineMeetingsAndSorting(
                        listMeetingRepository.getMeetingsList().getValue(),
                        placeFilter,
                        mDateFilterMutableLiveData.getValue()
                )
        );

        mMeetingViewStateLiveData.addSource(mDateFilterMutableLiveData, dateFilter ->
                combineMeetingsAndSorting(
                        listMeetingRepository.getMeetingsList().getValue(),
                        mPlaceFilterMutableLiveData.getValue(),
                        dateFilter
                )
        );
    }

    private void combineMeetingsAndSorting(List<Meeting> meetingsList, String placeFilter, LocalDate dateFilter) {
        List<MeetingViewStateItem> filteredMeetingsList = new ArrayList<>();

        if ((placeFilter == null || placeFilter.isEmpty()) && (dateFilter == null)) {
            filteredMeetingsList = meetingsList.stream().map(meeting ->
                    new MeetingViewStateItem(meeting.getId(),
                            getMeetingTitle(meeting),
                            meeting.getAvatar(),
                            meeting.getParticipants(),
                            meeting.getPlace(),
                            meeting.getDate())).collect(Collectors.toList());
        } else {
            for (int i = 0; i<meetingsList.size(); i++) {
                Meeting filteredMeeting = meetingsList.get(i);
                String filteredMeetingPlace = filteredMeeting.getPlace();
                LocalDate filteredMeetingDate = filteredMeeting.getDate();

                if (filteredMeetingPlace.equals(placeFilter)
                        || filteredMeetingDate.isAfter(dateFilter)
                        || filteredMeetingDate.equals(dateFilter)) {
                    MeetingViewStateItem filteredMeetingViewStateItem = new MeetingViewStateItem(
                            filteredMeeting.getId(),
                            getMeetingTitle(filteredMeeting),
                            filteredMeeting.getAvatar(),
                            filteredMeeting.getParticipants(),
                            filteredMeeting.getPlace(),
                            filteredMeeting.getDate());

                    filteredMeetingsList.add(filteredMeetingViewStateItem);
                }
            }
        }
        mMeetingViewStateLiveData.setValue(new MeetingViewState(filteredMeetingsList));
    }

    public LiveData<MeetingViewState> getMeetingViewStateLiveData() {
        return mMeetingViewStateLiveData;
    }

    public String getMeetingTitle(Meeting meeting) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        return mApplication.getString(
                R.string.meeting_title_preset,
                meeting.getTopic(),
                meeting.getDate().format(formatter),
                meeting.getPlace()
        );
    }

    public void onDeleteMeetingButtonClicked(String meetingId) {
        mListMeetingRepository.deleteMeeting(meetingId);
    }

    public void onFilterMeetingsByDateButtonClicked(LocalDate localDate) {
        mDateFilterMutableLiveData.setValue(localDate);
    }

    public void onFilterMeetingsByPlaceButtonClicked(String titlePlace) {
        mPlaceFilterMutableLiveData.setValue(titlePlace);
    }

}
