# Stock-Trader-Library

A library for stock trading and sample app using the library


# Including in your project
StockTrader is available in Jcenter so it is possible to get it as dependency:
```gradle
implementation 'com.bakikocak.stocktradersdk:stocktradersdk:1.0.1'
```

# Usage

1. Initialize StockTrader with the activity in which you would use it.

    > **_Java_**
    ```java
    StockTrader stockTrader = new StockTrader(this);
    ```
    
2. Make sure that your activity implements "StockTraderDataContract.View" and overrides following methods:

    ```java
    public class MainActivity extends AppCompatActivity implements StockTraderDataContract.View {
    
    private StockTrader stockTrader;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        // Initialize StockTrader library
        stockTrader = new StockTrader(this);
        // Fetch the company details asynchronously by providing company symbols such as "aapl" for Apple, "tsla" for Tesla.
        stockTrader.getCompanyData(companySymbol);
    }

    /**
     * This overriden method will be triggered whenever data fetching from API is completed
     * Note that CEO and website info could be null.
     */

    @Override
    public void onCompanyDataRetrieved(Company company) {
        
        nameTextView.setText(company.getCompanyName());
        valueTextView.setText(company.getCurrentStock().getPrice().toString());

        String websiteStr = company.getWebsite().length() > 0 ? company.getWebsite() : getString(R.string.no_data_text);
        websiteTextView.setText(websiteStr);

        String ceoStr = company.getCEO().length() > 0 ? company.getCEO() : getString(R.string.no_data_text);
        ceoTextView.setText(ceoStr);
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
            showErrorMessage(getString(R.string.company_not_found_text));
        }
    }

    ```
