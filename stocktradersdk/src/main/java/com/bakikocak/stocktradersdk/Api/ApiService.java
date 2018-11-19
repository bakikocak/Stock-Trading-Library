package com.bakikocak.stocktradersdk.Api;

import com.bakikocak.stocktradersdk.Model.Company;
import com.bakikocak.stocktradersdk.Model.CurrentStock;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "https://api.iextrading.com/1.0/";

    /**
     * To be able to get company info following params are required:
     * <p>
     * companySymbol such as "aapl" for Apple.
     */

    @GET("stock/{companySymbol}/company")
    Single<Company> getCompanyInfo(@Path("companySymbol") String companySymbol);


    /**
     * To be able to get company current stock info following params are required:
     * <p>
     * companySymbol such as "aapl" for Apple , "tsla" for Tesla
     * <p>
     * example: https://api.iextrading.com/1.0/tops/last?symbols=tsla
     */

    @GET("tops/last")
    Single<List<CurrentStock>> getCurrentStockValue(@Query("symbols") String companySymbol);

}

