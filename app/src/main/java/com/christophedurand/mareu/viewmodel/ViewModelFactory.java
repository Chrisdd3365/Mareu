package com.christophedurand.mareu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.christophedurand.mareu.ui.add.AddMeetingViewModel;
import com.christophedurand.mareu.ui.list.ListMeetingViewModel;
import com.christophedurand.mareu.utils.MainApplication;
import com.christophedurand.mareu.repository.ListMeetingRepository;


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
        } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(listMeetingRepository);

        }
        throw new IllegalArgumentException("Unknown ViewModel class $modelClass");
    }

}
