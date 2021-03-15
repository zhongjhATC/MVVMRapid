package com.zhongjh.mvvmrapid.base.activity;

/**
 * 区分相关方法，方便维护
 */
public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
