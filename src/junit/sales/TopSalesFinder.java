package junit.sales;

public class TopSalesFinder {

    private SalesRecord[] records = new SalesRecord[10];
    private int count = 0;

    public void registerSale(SalesRecord record) {
        if (count == records.length) {
            SalesRecord[] newRecords = new SalesRecord[records.length * 2];
            System.arraycopy(records, 0, newRecords, 0, records.length);
            records = newRecords;
        }
        records[count++] = record;
    }

    public SalesRecordResult[] findItemsSoldOver(int amount) {
        ProductTotal[] productTotals = calculateProductTotals();
        return filterResultsAboveAmount(productTotals, amount);
    }

    private ProductTotal[] calculateProductTotals() {
        ProductTotal[] entries = new ProductTotal[10];
        int entryCount = 0;

        for (int i = 0; i < count; i++) {
            SalesRecord r = records[i];
            String id = r.productId();
            int total = r.productPrice() * r.itemsSold();

            int existingIndex = findProductIndex(entries, entryCount, id);
            if (existingIndex >= 0) {
                entries[existingIndex].total += total;
            } else {
                if (entryCount == entries.length) {
                    ProductTotal[] newEntries = new ProductTotal[entries.length * 2];
                    System.arraycopy(entries, 0, newEntries, 0, entries.length);
                    entries = newEntries;
                }
                entries[entryCount++] = new ProductTotal(id, total);
            }
        }
        return entries;
    }

    private int findProductIndex(ProductTotal[] entries, int entryCount, String productId) {
        for (int j = 0; j < entryCount; j++) {
            if (entries[j].productId.equals(productId)) {
                return j;
            }
        }
        return -1;
    }

    private SalesRecordResult[] filterResultsAboveAmount(ProductTotal[] productTotals, int amount) {
        int resultCount = countProductsAboveAmount(productTotals, amount);
        SalesRecordResult[] result = new SalesRecordResult[resultCount];
        int pos = 0;

        for (ProductTotal entry : productTotals) {
            if (entry != null && entry.total > amount) {
                result[pos++] = new SalesRecordResult(entry.productId, entry.total);
            }
        }
        return result;
    }

    private int countProductsAboveAmount(ProductTotal[] productTotals, int amount) {
        int count = 0;
        for (ProductTotal entry : productTotals) {
            if (entry != null && entry.total > amount) {
                count++;
            }
        }
        return count;
    }

    public void removeSalesRecordsFor(String id) {
        int newCount = 0;
        for (int i = 0; i < count; i++) {
            if (!records[i].productId().equals(id)) {
                records[newCount++] = records[i];
            }
        }
        count = newCount;
    }

    public SalesRecord[] getAllRecordsPaged(int pageNumber, int pageSize) {
        int start = pageNumber * pageSize;
        if (start >= count) {
            return new SalesRecord[0];
        }
        int end = Math.min(start + pageSize, count);
        SalesRecord[] result = new SalesRecord[end - start];
        System.arraycopy(records, start, result, 0, end - start);
        return result;
    }

    public int getRecordCount() {
        return count;
    }

    public void removeRecord(String id) {
        removeSalesRecordsFor(id);
    }

    private static class ProductTotal {
        String productId;
        int total;

        ProductTotal(String productId, int total) {
            this.productId = productId;
            this.total = total;
        }
    }
}