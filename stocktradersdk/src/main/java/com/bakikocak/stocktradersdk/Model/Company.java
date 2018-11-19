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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public CurrentStock getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(CurrentStock currentStock) {
        this.currentStock = currentStock;
    }
}