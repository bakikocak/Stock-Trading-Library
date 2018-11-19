package com.bakikocak.stocktradersdk;

import com.bakikocak.stocktradersdk.Model.Company;

public class StockTraderDataContract {

    /**
     * An activity in the client app should override following methods.
     */
    public interface View {

        void onCompanyDataRetrieved(Company company);

        void onObservableError(Throwable error);
    }
}

