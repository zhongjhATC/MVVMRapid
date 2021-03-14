package com.zhongjh.mvvmrapid.base;

import android.app.Application;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends AndroidViewModel {

    // 用于RxJava异步操作销毁，防止内存泄漏
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 用于RxJava加入mCompositeDisposable后面统一销毁
     * @param disposable rx的disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 注入RxLifecycle生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }



}
