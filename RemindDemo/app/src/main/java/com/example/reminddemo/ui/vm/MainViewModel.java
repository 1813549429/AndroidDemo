package com.example.reminddemo.ui.vm;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public MutableLiveData<String> nowDate = new MutableLiveData<>();

    public MainViewModel() {

    }

}
