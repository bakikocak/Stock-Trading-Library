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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

}
