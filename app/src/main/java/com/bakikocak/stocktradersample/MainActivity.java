package com.bakikocak.stocktradersample;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bakikocak.stocktradersdk.StockTraderDataContract;
import com.bakikocak.stocktradersdk.Model.Company;
import com.bakikocak.stocktradersdk.StockTrader;


public class MainActivity extends AppCompatActivity implements StockTraderDataContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextInputEditText companyEditText;
    private Button findButton;
    private StockTrader stockTrader;
    private TextView infoTextView, nameTextView, websiteTextView, ceoTextView, valueTextView;
    private String companySymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUiWidgets();

        // Initialize StockTrader library
        stockTrader = new StockTrader(this);
        // Add Lifecycle Observer
        getLifecycle().addObserver(StockTrader.getInstance());

    }

    private void setUpUiWidgets() {

        companyEditText = findViewById(R.id.textInputEditText_company);
        findButton = findViewById(R.id.btn_find);
        infoTextView = findViewById(R.id.tv_info_text);

        nameTextView = findViewById(R.id.tv_name);
        websiteTextView = findViewById(R.id.tv_website);
        ceoTextView = findViewById(R.id.tv_ceo);
        valueTextView = findViewById(R.id.tv_value);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTextFields();

                companySymbol = companyEditText.getText().toString();
                if (Utils.isEmpty(companySymbol)) {
                    showErrorMessage(getString(R.string.symbol_empty_error));
                } else {
                    companyEditText.setText("");
                    infoTextView.setText(getString(R.string.searching_text));
                    // Send data request for given company symbol
                    stockTrader.getCompanyData(companySymbol);
                }
            }
        });
    }

    /**
     * This overriden method will be triggered whenever data fetching from API is completed
     * Note that CEO and website info could be null
     */
    @Override
    public void onCompanyDataRetrieved(Company company) {
        infoTextView.setText("");

        nameTextView.setText(getString(R.string.company_name_text, company.getCompanyName()));
        valueTextView.setText(getString(R.string.value_text, company.getCurrentStock().getPrice().toString()));

        String websiteStr = company.getWebsite().length() > 0 ? company.getWebsite() : getString(R.string.no_data_text);
        websiteTextView.setText(getString(R.string.website_text, websiteStr));

        String ceoStr = company.getCEO().length() > 0 ? company.getCEO() : getString(R.string.no_data_text);
        ceoTextView.setText(getString(R.string.ceo_text, ceoStr));
    }

    /**
     * Show error messages if one of the following occurs:
     * If device does not have connection.
     * If company is not found, API returns "HTTP 404".
     */

    @Override
    public void onObservableError(Throwable error) {
        if (!Utils.isNetworkAvailable(this)) {
            showErrorMessage(getString(R.string.error_network));
        }

        if (error.getLocalizedMessage().contains("404")) {
            showErrorMessage(getString(R.string.company_not_found_text, companySymbol));
        }
    }

    public void showErrorMessage(String msg) {
        infoTextView.setText(msg);
    }

    private void clearTextFields() {
        nameTextView.setText("");
        websiteTextView.setText("");
        ceoTextView.setText("");
        valueTextView.setText("");
        infoTextView.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove Lifecycle Observer
        getLifecycle().removeObserver(StockTrader.getInstance());
    }
}
