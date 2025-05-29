package junit.sales;

import java.util.*;
import java.util.stream.Collectors;

public class TopSalesFinder {

    private final Map<String, List<SalesRecord>> salesRecords = new HashMap<>();

    public void registerSale(SalesRecord record) {
        salesRecords.computeIfAbsent(record.productId(), k -> new ArrayList<>()).add(record);
    }

    public SalesRecordResult[] findItemsSoldOver(int amount) {
        return salesRecords.entrySet().stream()
                .map(entry -> new SalesRecordResult(
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToInt(record -> record.productPrice() * record.itemsSold())
                                .sum()))
                .filter(result -> result.total() > amount)
                .toArray(SalesRecordResult[]::new);
    }

    public void removeSalesRecordsFor(String id) {
        salesRecords.remove(id);
    }

    public SalesRecord[] getAllRecordsPaged(int pageNumber, int pageSize) {
        List<SalesRecord> allRecords = salesRecords.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        int fromIndex = pageNumber * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allRecords.size());

        if (fromIndex >= allRecords.size()) {
            return new SalesRecord[0];
        }

        return allRecords.subList(fromIndex, toIndex).toArray(new SalesRecord[0]);
    }

    public int getRecordCount() {
        return salesRecords.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    public void removeRecord(String id) {
        salesRecords.keySet().removeIf(key -> key.equals(id));
    }
}


