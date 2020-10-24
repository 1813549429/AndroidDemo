package com.example.reminddemo.ui.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reminddemo.data.RemindBeforeListBean;
import com.example.reminddemo.data.LiveDataBus;
import com.example.reminddemo.db.RepeatStrategy;


public class EditViewModel extends ViewModel {

    public MutableLiveData<RepeatStrategy> repeatData;
    public MutableLiveData<String> startTimeRemark;
    public MutableLiveData<String> repeatRemark;
    public MutableLiveData<RemindBeforeListBean> remindBefore;

    public EditViewModel() {

        super();

        LiveDataBus liveDataBus = LiveDataBus.get();
        repeatData = liveDataBus.getChannel("repeatData", RepeatStrategy.class);
        startTimeRemark = liveDataBus.getChannel("startTimeRemark", String.class);
        repeatRemark = liveDataBus.getChannel("repeatRemark", String.class);
        remindBefore = liveDataBus.getChannel("remindBefore", RemindBeforeListBean.class);
    }
}
