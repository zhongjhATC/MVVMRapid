package com.zhongjh.mvvmrapid.base.viewmodel;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.zhongjh.mvvmrapid.base.BaseModel;
import com.zhongjh.mvvmrapid.livedata.SingleLiveEvent;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 继承于AndroidViewModel
 * 实现IBaseViewModel即是lifecycle
 * @param <M>
 * @param <T>
 */
public class BaseViewModel<M extends BaseModel, T> extends AndroidViewModel implements IBaseViewModel {

    private UIChangeLiveData<T> uc;
    // 弱引用持有
    private WeakReference<LifecycleProvider<Context>> lifecycle;
    // 用于RxJava异步操作销毁，防止内存泄漏
    private CompositeDisposable mCompositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * 用于RxJava加入mCompositeDisposable后面统一销毁
     *
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
     */
    public void injectLifecycleProvider(LifecycleProvider<Context> lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider<Context> getLifecycleProvider() {
        return lifecycle.get();
    }

    public UIChangeLiveData<T> getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData<>();
        }
        return uc;
    }

    /**
     * 默认标题 请稍后... 的等待界面
     */
    public void showDialog() {
        showDialog("请稍后...");
    }

    /**
     * 关闭dialog
     */
    public void dismissDialog() {
        uc.dismissDialogEvent.call();
    }

    /**
     * 自定义标题的界面
     * @param title 标题
     */
    public void showDialog(String title) {
        uc.showDialogEvent.postValue(title);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uc.startActivityEvent.postValue(params);
    }

    /**
     * 关闭界面
     */
    public void finish() {
        uc.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uc.onBackPressedEvent.call();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    // region Lifecycle 生命周期

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }

    // endregion

    public static final class UIChangeLiveData<T> extends SingleLiveEvent<T> {

        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private <T2> SingleLiveEvent<T2> createLiveData(SingleLiveEvent<T2> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }

}
