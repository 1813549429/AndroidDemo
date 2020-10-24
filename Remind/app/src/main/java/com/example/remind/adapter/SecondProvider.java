package com.example.remind.adapter;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.remind.R;


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
        helper.setText(R.id.title, entity.getTitle());

    }

    @Override
    public void onClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        SecondNode entity = (SecondNode) data;

    }
}
