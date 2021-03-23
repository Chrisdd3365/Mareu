package com.christophedurand.mareu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;

import java.util.List;


public class ListMeetingViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Meeting>> mMeetingsListLiveData = new MediatorLiveData<>();

    public ListMeetingViewModel(@NonNull Application application,
                                ListMeetingRepository listMeetingRepository) {
        super(application);

        mMeetingsListLiveData.addSource(listMeetingRepository.getMeetingsList(), meetings -> {
            combineMeetingsAndSorting(meetings);
        });
    }

    private void combineMeetingsAndSorting(List<Meeting> meetings) {
        // TODO !
        mMeetingsListLiveData.setValue(meetings);
    }

    public LiveData<List<Meeting>> getMeetingsListLiveData() {
        return mMeetingsListLiveData;
    }

}
