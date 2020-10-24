package com.example.reminddemo.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.reminddemo.utils.ClassUtil;

public abstract class BaseActivity<VM extends ViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected abstract int getLayoutId();
    protected VM viewModel;
    protected DB bindingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, getLayoutId());
        bindingView.setLifecycleOwner(this);

        try {
            getSupportActionBar().hide();
        }catch (Exception e) {
            e.printStackTrace();
        }

        initViewModel();
    }

    protected void initViewModel(){
        Class<VM> viewModelClass = ClassUtil.getViewModel(this);
        if (viewModelClass != null) {
            viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
    }

    public VM getViewModel() {
        return viewModel;
    }

}
