package com.bakikocak.stocktradersdk.Api;

import com.bakikocak.stocktradersdk.Model.Company;
import com.bakikocak.stocktradersdk.Model.CurrentStock;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getBaseRetrofit(String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;

    }

    /**
     * Network call to fetch company info such as name, website, CEO
     */
    public static Observable<Company> getCompanyInfoWithSymbolObservable(final String companySymbol) {

        final ApiService service = ApiClient
                .getBaseRetrofit(ApiService.BASE_URL)
                .create(ApiService.class);

        return service.getCompanyInfo(companySymbol)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Network call to get current stock value for a single company
     * This getCurrentStockValue returns a List of CurrentStock object,
     * first we checked if there is CurrentStock object for the given company.
     * If exists a CurrentStock object then we map it into that particular Company object.
     * map() operator is used to change return type to Company
     * so that client app would fetch the results of two network calls mapped at once.
     */

    public static Observable<Company> getCurrentStockValueWithSymbolObservable(final Company company) {

        final ApiService service = ApiClient
                .getBaseRetrofit(ApiService.BASE_URL)
                .create(ApiService.class);

        return service.getCurrentStockValue(company.getSymbol())
                .toObservable()
                .map(new Function<List<CurrentStock>, Company>() {
                    @Override
                    public Company apply(List<CurrentStock> currentStocks) throws Exception {
                        company.setCurrentStock(currentStocks.get(0));
                        return company;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
