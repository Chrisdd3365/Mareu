package com.christophedurand.mareu.ui.add;


import androidx.lifecycle.ViewModel;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;


public class AddMeetingViewModel extends ViewModel {

    private final ListMeetingRepository mListMeetingRepository;


    public AddMeetingViewModel(ListMeetingRepository mListMeetingRepository) {
        this.mListMeetingRepository = mListMeetingRepository;
    }


    public void onCreateMeetingButtonClicked(Meeting meeting) {
        mListMeetingRepository.createMeeting(meeting);
    }

}

