package com.bakikocak.stocktradersdk.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("companyName")
    @Expose
    private String companyName;

    @SerializedName("exchange")
    @Expose
    private String exchange;

    @SerializedName("industry")
    @Expose
    private String industry;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("CEO")
    @Expose
    private String CEO;

    @SerializedName("issueType")
    @Expose
    private String issueType;

    @SerializedName("sector")
    @Expose
    private String sector;

    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    // Note that we have added this one since we will map CurrentStock data after getCurrentStockValue() call.
    private CurrentStock currentStock;

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getCEO() {
        return CEO;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getSector() {
        return sector;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setCurrentStock(CurrentStock currentStock) {
        this.currentStock = currentStock;
    }

    public CurrentStock getCurrentStock() {
        return currentStock;
    }
}