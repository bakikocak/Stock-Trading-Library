package com.bakikocak.stocktradersdk;

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

public class StockTrader {

    public static final String TAG = StockTrader.class.getSimpleName();

    private StockTraderDataContract.View mActivity;
    private CompositeDisposable disposable = new CompositeDisposable();

    public StockTrader(StockTraderDataContract.View activity) {
        mActivity = activity;
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
}


