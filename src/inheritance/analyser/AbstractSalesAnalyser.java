package inheritance.analyser;

import java.time.LocalDate;
import java.util.*;

public abstract sealed class AbstractSalesAnalyser permits EstonianTaxSalesAnalyser, FinnishSalesAnalyser, TaxFreeSalesAnalyser {

    protected final List<SalesRecord> records;

    public AbstractSalesAnalyser(List<SalesRecord> records) {
        this.records = new ArrayList<>(records);
    }

    protected abstract double getVatRate(LocalDate date);

    public Double getTotalSales() {
        double totalRevenue = 0.0;
        for (SalesRecord record : records) {
            double vatRate = getVatRate(record.date());
            double revenueWithoutVat = record.productPrice() * record.itemsSold() / (1 + vatRate / 100);
            totalRevenue += revenueWithoutVat;
        }
        return totalRevenue;
    }
    public Double getTotalSalesByProductId(String id) {
        double totalRevenue = 0.0;
        for (SalesRecord record : records) {
            if (record.productId().equals(id)) {
                double vatRate = getVatRate(record.date());
                double revenueWithoutVat = record.productPrice() * record.itemsSold() / (1 + vatRate / 100);
                totalRevenue += revenueWithoutVat;
            }
        }
        return totalRevenue;
    }

    public List<String> getTop3PopularItems() {
        Map<String, Integer> itemQuantities = new HashMap<>();
        for (SalesRecord record : records) {
            itemQuantities.put(record.productId(), itemQuantities.getOrDefault(record.productId(), 0) + record.itemsSold());
        }
        return itemQuantities.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Double getLargestTotalSalesAmountForSingleItem() {
        Map<String, Double> itemRevenues = new HashMap<>();
        for (SalesRecord record : records) {
            double vatRate = getVatRate(record.date());
            double revenueWithoutVat = record.productPrice() * record.itemsSold() / (1 + vatRate / 100);
            itemRevenues.put(record.productId(), itemRevenues.getOrDefault(record.productId(), 0.0) + revenueWithoutVat);
        }

        return itemRevenues.values().stream()
                .max(Double::compareTo)
                .orElse(0.0);
    }
}