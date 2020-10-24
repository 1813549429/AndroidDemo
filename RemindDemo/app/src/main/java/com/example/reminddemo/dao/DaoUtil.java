package com.example.reminddemo.dao;

import android.content.Context;

import androidx.room.Fts4;
import androidx.room.Room;
import androidx.room.TypeConverter;

import com.example.reminddemo.data.AllDataBean;
import com.example.reminddemo.db.RemindBefore;
import com.example.reminddemo.db.RemindDB;
import com.example.reminddemo.db.RemindItem;
import com.example.reminddemo.db.RepeatStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DaoUtil {

    private static DaoUtil INSTANCE;
    private Context context;
    private final RemindDao mRemindDao;
    private final RepeatDao mRepeatDao;
    private final RemindBeforeDao mRemindBeforeDao;
    private final RemindDB mRemindDB;

    private ExecutorService executor;

    private DaoUtil(Context context) {
        this.context = context;
        mRemindDB = Room.databaseBuilder(context, RemindDB.class, "remind_db").build();
        mRemindDao = mRemindDB.remindDao();
        mRepeatDao = mRemindDB.repeatDao();
        mRemindBeforeDao = mRemindDB.remindBeforeDao();
        executor = Executors.newCachedThreadPool();
    }

    public synchronized static DaoUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DaoUtil(context);
        }
        return INSTANCE;
    }

    public void insertItem(String title, String remark, long key, long startTime, RepeatStrategy repeatStrategy, List<RemindBefore> remindBeforeList) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                RemindItem remindItem = new RemindItem();
                remindItem.setTitle(title);
                remindItem.setRemark(remark);
                remindItem.setStartDate(startTime);
                remindItem.setKey(key);

                mRemindDao.insertRemindItem(remindItem);
                repeatStrategy.setItem_key(key);
                mRepeatDao.insertRepeat(repeatStrategy);

                for (RemindBefore remindBefore : remindBeforeList) {
                    remindBefore.setItem_key(key);
                    mRemindBeforeDao.insertRemindBefore(remindBefore);
                }
            }
        });

    }

    public void deleteItem(long key) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                RemindItem remindItem = mRemindDao.findByKey(key);
                mRepeatDao.deleteRepeatByItemKey(key);
                mRemindBeforeDao.deleteRemindBeforeByItemKey(key);
                mRemindDao.deleteRemindItem(remindItem);
            }
        });


    }

    public void updateItem(long key, String title, String remark,
                           long startTime, RepeatStrategy repeatStrategy,
                           List<RemindBefore> remindBeforeList) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                deleteItem(key);
                insertItem(title, remark, key, startTime, repeatStrategy, remindBeforeList);
            }
        });

    }
    //TODO 此方法需要子线程环境运行，请确保方法执行是在子线程中
    public List<AllDataBean> getAllData() {
        List<RemindItem> remindItems = mRemindDao.getAll();
        List<AllDataBean> allDataBeans = new ArrayList<>();
        AllDataBean allDataBean;
        for (RemindItem remindItem : remindItems) {
            long key = remindItem.getKey();
            List<RemindBefore> remindBeforeList = mRemindBeforeDao.findByItemKey(key);
            RepeatStrategy repeatStrategy = mRepeatDao.findByItemKey(key);
            allDataBean = new AllDataBean(remindItem.getTitle(), remindItem.getRemark(),
                    remindItem.getStartDate(), repeatStrategy, remindBeforeList);

            allDataBeans.add(allDataBean);
        }
        return allDataBeans;
    }

    /**
     * 频率，0代表一次性即不重复，1代表年   2代表月    3代表周    4代表日  不能为null
     */
    public static RepeatStrategy getRepeatStrategy(int frequency, int interval, String month, String week, String day) {
        RepeatStrategy repeatStrategy = new RepeatStrategy();
        repeatStrategy.setFrequency(frequency);
        repeatStrategy.setInterval(interval);
        repeatStrategy.setMonth(month);
        repeatStrategy.setWeek(week);
        repeatStrategy.setDay(day);
        return repeatStrategy;
    }


}
