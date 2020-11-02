package com.bonoreminder.app.vm;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bonoreminder.app.R;
import com.bonoreminder.app.app.AppContext;

public class SetTimeViewModel extends ViewModel {

    private MutableLiveData<String> timeTextLiveData = new MutableLiveData<>();
    private MutableLiveData<String> remindTextLiveData = new MutableLiveData<>();
    private MutableLiveData<String> repeatTextLiveData = new MutableLiveData<>();
    private MutableLiveData<String> monthTextLiveData = new MutableLiveData<>();

    public SetTimeViewModel() {
        super();
        Context context = AppContext.getContext();
        remindTextLiveData.setValue(context.getString(R.string.no_reminder));
        repeatTextLiveData.setValue(context.getString(R.string.no_repeat));
    }

    public MutableLiveData<String> getTimeTextLiveData() {
        return timeTextLiveData;
    }

    public void setTimeTextLiveData(MutableLiveData<String> timeTextLiveData) {
        this.timeTextLiveData = timeTextLiveData;
    }

    public MutableLiveData<String> getRemindTextLiveData() {
        return remindTextLiveData;
    }

    public void setRemindTextLiveData(MutableLiveData<String> remindTextLiveData) {
        this.remindTextLiveData = remindTextLiveData;
    }

    public MutableLiveData<String> getRepeatTextLiveData() {
        return repeatTextLiveData;
    }

    public void setRepeatTextLiveData(MutableLiveData<String> repeatTextLiveData) {
        this.repeatTextLiveData = repeatTextLiveData;
    }

    public MutableLiveData<String> getMonthTextLiveData() {
        return monthTextLiveData;
    }

    public void setMonthTextLiveData(MutableLiveData<String> monthTextLiveData) {
        this.monthTextLiveData = monthTextLiveData;
    }
}
