package com.example.reminddemo.data;

import com.example.reminddemo.app.AppContext;
import com.example.reminddemo.dao.DaoUtil;

import java.util.ArrayList;
import java.util.List;

public class MainDataRepository {
    private List<AllDataBean> dataBeanList = new ArrayList<>();

    private List<AllDataBean> allDataBeanList;

    public MainDataRepository(List<AllDataBean> allDataBeanList) {
        this.allDataBeanList = allDataBeanList;
    }


    /**
     * @param startDate     查询的开始日期
     * @param endDate       查询的结束日期
     * @param size          查询的数量
     * @return
     */
    public List<AllDataBean> loadData(long startDate, long endDate, int size) {
        List<AllDataBean> resultData = new ArrayList<>();
        for (AllDataBean dataBean : allDataBeanList) {
            if(dataBean.getStartTime() >= startDate && dataBean.getStartTime() < endDate) {
                resultData.add(dataBean);
            }
        }
        if(resultData.size() >= size) {
            return resultData.subList(0,size);
        }else {
            return resultData;
        }

    }

}
