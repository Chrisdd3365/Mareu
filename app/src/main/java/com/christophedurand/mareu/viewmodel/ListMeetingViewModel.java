package com.christophedurand.mareu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.christophedurand.mareu.model.Meeting;
import com.christophedurand.mareu.repository.ListMeetingRepository;

import java.util.List;


public class ListMeetingViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Meeting>> mMeetingsListLiveData = new MutableLiveData<>();
    public LiveData<List<Meeting>> getMeetingsListLiveData() {
        return mMeetingsListLiveData;
    }


    public ListMeetingViewModel(@NonNull Application application,
                                ListMeetingRepository listMeetingRepository) {
        super(application);

        mMeetingsListLiveData.setValue(listMeetingRepository.getMeetingsList());
    }

}
