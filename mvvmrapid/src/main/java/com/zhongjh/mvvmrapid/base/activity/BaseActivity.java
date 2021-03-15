package com.zhongjh.mvvmrapid.base.activity;

import android.app.Activity;

import androidx.databinding.ViewDataBinding;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhongjh.mvvmrapid.base.BaseModel;
import com.zhongjh.mvvmrapid.base.viewmodel.BaseViewModel;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel<BaseModel, Activity>> extends RxAppCompatActivity implements IBaseView {

    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;



}
