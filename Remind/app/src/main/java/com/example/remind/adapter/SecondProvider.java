package com.example.remind.adapter;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.remind.R;
import com.example.remind.db.entity.CheckListEntity;
import com.example.remind.db.entity.Remind;


public class SecondProvider extends BaseNodeProvider {

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_item_second;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseNode data) {
        SecondNode entity = (SecondNode) data;
        Remind remindData = entity.getRemindData();
        CheckListEntity checkListEntityData = entity.getCheckListEntityData();

        helper.setText(R.id.tv_item_title, remindData.getTitle());
        helper.setText(R.id.tv_item_date, remindData.getTime()+"");
        helper.setText(R.id.tv_item_checklist, checkListEntityData.getTitle());

    }

    @Override
    public void onClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;

    }
}
