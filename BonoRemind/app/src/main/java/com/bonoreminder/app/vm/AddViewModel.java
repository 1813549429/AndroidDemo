package com.bonoreminder.app.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bonoreminder.app.db.entity.Remind;

public class AddViewModel extends ViewModel {

    private MutableLiveData<Remind> remindLiveData = new MutableLiveData<>();
    private MutableLiveData<String> timeTextLiveData = new MutableLiveData<>();
    private MutableLiveData<String> advanceTextLiveData = new MutableLiveData<>();
    private MutableLiveData<String> repeatTextLiveData = new MutableLiveData<>();

    public AddViewModel() {
        super();
    }

    public MutableLiveData<Remind> getRemindLiveData() {
        return remindLiveData;
    }

    public void setRemindLiveData(MutableLiveData<Remind> remindLiveData) {
        this.remindLiveData = remindLiveData;
    }

    public MutableLiveData<String> getTimeTextLiveData() {
        return timeTextLiveData;
    }

    public void setTimeTextLiveData(MutableLiveData<String> timeTextLiveData) {
        this.timeTextLiveData = timeTextLiveData;
    }

    public MutableLiveData<String> getAdvanceTextLiveData() {
        return advanceTextLiveData;
    }

    public void setAdvanceTextLiveData(MutableLiveData<String> advanceTextLiveData) {
        this.advanceTextLiveData = advanceTextLiveData;
    }

    public MutableLiveData<String> getRepeatTextLiveData() {
        return repeatTextLiveData;
    }

    public void setRepeatTextLiveData(MutableLiveData<String> repeatTextLiveData) {
        this.repeatTextLiveData = repeatTextLiveData;
    }
}
