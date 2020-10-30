package com.example.bonoremind.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bonoremind.R;
import com.example.bonoremind.app.AppContext;


public class MainViewModel extends ViewModel {
    private MutableLiveData<String> titleLiveData = new MutableLiveData<>();
    AppContext context;

    public MainViewModel() {
        super();
        context = AppContext.getContext();
        titleLiveData.setValue(context.getString(R.string.inbox));
    }

    public MutableLiveData<String> getTitleLiveData() {
        return titleLiveData;
    }

    public void setTitleLiveData(MutableLiveData<String> titleLiveData) {
        this.titleLiveData = titleLiveData;
    }
}
