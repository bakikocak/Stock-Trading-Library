package com.bakikocak.stocktradersdk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentStock {

    @SerializedName("symbol")
    @Expose
    private String symbol;

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("time")
    @Expose
    private Float time;

    public String getSymbol() {
        return symbol;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getSize() {
        return size;
    }

    public Float getTime() {
        return time;
    }
}
