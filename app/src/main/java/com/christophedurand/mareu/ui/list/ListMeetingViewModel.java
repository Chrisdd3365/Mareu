package com.christophedurand.mareu.ui.list;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class ListMeetingViewModel extends ViewModel {

    private final ListMeetingRepository mListMeetingRepository;

    private final MediatorLiveData<MeetingViewState> mMeetingsListLiveData = new MediatorLiveData<>();

    private final MutableLiveData<String> mPlaceFilterMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<LocalDate> mDateFilterMutableLiveData = new MutableLiveData<>();


    public ListMeetingViewModel(ListMeetingRepository listMeetingRepository) {

        this.mListMeetingRepository = listMeetingRepository;

        mMeetingsListLiveData.addSource(listMeetingRepository.getMeetingsList(), meetingsList ->
                combineMeetingsAndSorting(
                        meetingsList,
                        mPlaceFilterMutableLiveData.getValue(),
                        mDateFilterMutableLiveData.getValue()));

        mMeetingsListLiveData.addSource(mPlaceFilterMutableLiveData, placeFilter ->
                combineMeetingsAndSorting(
                        listMeetingRepository.getMeetingsList().getValue(),
                        placeFilter,
                        mDateFilterMutableLiveData.getValue()));

        mMeetingsListLiveData.addSource(mDateFilterMutableLiveData, dateFilter ->
                combineMeetingsAndSorting(
                        listMeetingRepository.getMeetingsList().getValue(),
                        mPlaceFilterMutableLiveData.getValue(),
                        dateFilter));
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

                if (filteredMeetingPlace.equals(placeFilter) || dateFilter.isAfter(filteredMeetingDate)) {
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
        mMeetingsListLiveData.setValue(new MeetingViewState(filteredMeetingsList));
    }

    public LiveData<MeetingViewState> getMeetingsListLiveData() {
        return mMeetingsListLiveData;
    }

    public String getMeetingTitle(Meeting meeting) {
        String myFormat = "dd/MM/yyyy kk:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        String date = sdf.format(meeting.getDate());

        return meeting.getTopic() + " - " + date + " - " + meeting.getPlace();
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
