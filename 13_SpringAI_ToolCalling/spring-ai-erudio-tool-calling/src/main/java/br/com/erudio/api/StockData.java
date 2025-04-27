package br.com.erudio.api;

import java.util.List;

public class StockData {

    private List<DailyStockData> values;

    public StockData() {}

    public List<DailyStockData> getValues() {
        return values;
    }

    public void setValues(List<DailyStockData> values) {
        this.values = values;
    }
}
