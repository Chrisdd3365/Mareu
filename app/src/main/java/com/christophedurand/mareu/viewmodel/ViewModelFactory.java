package com.christophedurand.mareu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.christophedurand.mareu.MainApplication;
import com.christophedurand.mareu.repository.ListMeetingRepository;
import com.christophedurand.mareu.service.DummyMeetingApiService;
import com.christophedurand.mareu.service.MeetingApiService;


public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sInstance;
    private final Application application;
    private final ListMeetingRepository listMeetingRepository;


    public static ViewModelFactory getInstance() {
        if (sInstance == null) {
            sInstance = new ViewModelFactory();
        }
        return sInstance;
    }

    private ViewModelFactory() {
        this.application = MainApplication.getApplication();
        this.listMeetingRepository = new ListMeetingRepository();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ListMeetingViewModel.class)) {
            return (T) new ListMeetingViewModel(application, listMeetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class $modelClass");
    }

}
