package com.bakikocak.stocktradersdk;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.bakikocak.stocktradersdk.Api.ApiClient;
import com.bakikocak.stocktradersdk.Model.Company;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StockTrader implements LifecycleObserver {

    private static final String TAG = StockTrader.class.getSimpleName();
    private static StockTrader instance;

    private StockTraderDataContract.View mActivity;
    private CompositeDisposable disposable = new CompositeDisposable();

    public StockTrader(StockTraderDataContract.View activity) {
        mActivity = activity;
        instance = this;
    }

    public static StockTrader getInstance() {
        return instance;
    }

    public void getCompanyData(final String companySymbol) {

        ConnectableObservable<Company> companyObservable = ApiClient.getCompanyInfoWithSymbolObservable(companySymbol).replay();

        /**
         * Fetching company data first from API (getCompanyInfo) "stock/{companySymbol}/company"
         * Observable emits Company at once
         */
        disposable.add(
                companyObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Company>() {

                            @Override
                            public void onNext(Company company) {
                                //Log.d(TAG, "onNext: " + company.getCompanyName());
                            }

                            @Override
                            public void onError(Throwable e) {
                                mActivity.onObservableError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));

        /**
         * Fetching current stock value for an individual Company object
         * FlatMap makes HTTP call on each Company emission
         */
        disposable.add(
                companyObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        /**
                         * Fetching CurrentStock on each Company emission
                         * */
                        .flatMap(new Function<Company, ObservableSource<Company>>() {
                            @Override
                            public ObservableSource<Company> apply(Company company) {
                                return ApiClient.getCurrentStockValueWithSymbolObservable(company);
                            }
                        })
                        .subscribeWith(new DisposableObserver<Company>() {

                            @Override
                            public void onNext(Company company) {
                                mActivity.onCompanyDataRetrieved(company);
                            }

                            @Override
                            public void onError(Throwable e) {
                                mActivity.onObservableError(e);
                            }

                            @Override
                            public void onComplete() {

                            }
                        }));


        // connect to start emission
        companyObservable.connect();

    }
    /**
     * Since we need to dispose the disposable onDestroy of the activity for safety reasons,
     * Lifecycle Arch Components are integrated and listening to onDestroy method of the activity below
     */

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        Log.d(TAG, "init: called onCreate() of Activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void LibOnStart() {
        Log.d(TAG, "LibOnStart: called onStart() of Activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void LibOnStop() {
        Log.d(TAG, "LibOnStop: called onStop() of Activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void LibOnResume() {
        Log.d(TAG, "LibOnResume: called onResume() of Activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void LibOnPause() {
        Log.d(TAG, "LibOnPause: called onPause() of Activity");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup() {
        Log.d(TAG, "cleanup: called onDestroy() of Activity");
        disposable.dispose();
    }
}


