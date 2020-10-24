package com.example.reminddemo.data;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.reminddemo.utils.DateUtil;

import java.util.List;

public class MainPageDataSource extends PageKeyedDataSource<Long, AllDataBean> {


    MainDataRepository mainDataRepository;

    MainPageDataSource(MainDataRepository mainDataRepository) {
        this.mainDataRepository = mainDataRepository;
    }

    //初始加载数据
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, AllDataBean> callback) {
        //拿到当前时间,统计当前月
        int[] currentTime = DateUtil.getDay(System.currentTimeMillis());
        long endTime;

        long startTime = DateUtil.strToLong(currentTime[0] + "-" + currentTime[1] +
                "-01 00:00","yyyy-MM-dd HH:mm");

        if (currentTime[1] == 12) {
            endTime = DateUtil.strToLong((currentTime[0]+1) + "-01"  +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }else {
            endTime = DateUtil.strToLong(currentTime[0] + "-" + (currentTime[1]+1) +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }

        List<AllDataBean> allDataBeanList = mainDataRepository.loadData(startTime, endTime, params.requestedLoadSize);
        callback.onResult(allDataBeanList, null, endTime);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, AllDataBean> callback) {

        int[] startTime = DateUtil.getDay(params.key);
        long endTime;


        if (startTime[1] == 12) {
            endTime = DateUtil.strToLong((startTime[0]+1) + "-01"  +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }else {
            endTime = DateUtil.strToLong(startTime[0] + "-" + (startTime[1]+1) +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }

        List<AllDataBean> allDataBeanList = mainDataRepository.loadData(params.key, endTime, params.requestedLoadSize);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, AllDataBean> callback) {

        int[] startTime = DateUtil.getDay(params.key);
        long endTime;


        if (startTime[1] == 12) {
            endTime = DateUtil.strToLong((startTime[0]+1) + "-01"  +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }else {
            endTime = DateUtil.strToLong(startTime[0] + "-" + (startTime[1]+1) +
                    "-01 00:00","yyyy-MM-dd HH:mm");
        }

        List<AllDataBean> allDataBeanList = mainDataRepository.loadData(params.key, endTime, params.requestedLoadSize);
        callback.onResult(allDataBeanList, endTime);
    }


}
