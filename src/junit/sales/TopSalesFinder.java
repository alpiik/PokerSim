package junit.sales;

public class TopSalesFinder {

    private SalesRecord[] records = new SalesRecord[10];
    private int count = 0;

    public void registerSale(SalesRecord record) {
        if (count == records.length) {
            SalesRecord[] newRecords = new SalesRecord[records.length * 2];
            for (int i = 0; i < records.length; i++) {
                newRecords[i] = records[i];
            }
            records = newRecords;
        }
        records[count++] = record;
    }

    public SalesRecordResult[] findItemsSoldOver(int amount) {
        String[] productIds = new String[10];
        int[] totals = new int[10];
        int uniqueCount = 0;

        for (int i = 0; i < count; i++) {
            SalesRecord r = records[i];
            int index = -1;
            for (int j = 0; j < uniqueCount; j++) {
                if (productIds[j].equals(r.productId())) {
                    index = j;
                    break;
                }
            }
            if (index == -1) {
                if (uniqueCount == productIds.length) {
                    String[] newProductIds = new String[productIds.length * 2];
                    int[] newTotals = new int[totals.length * 2];
                    for (int j = 0; j < productIds.length; j++) {
                        newProductIds[j] = productIds[j];
                        newTotals[j] = totals[j];
                    }
                    productIds = newProductIds;
                    totals = newTotals;
                }
                productIds[uniqueCount] = r.productId();
                totals[uniqueCount] = r.productPrice() * r.itemsSold();
                uniqueCount++;
            } else {
                totals[index] += r.productPrice() * r.itemsSold();
            }
        }

        int resultCount = 0;
        for (int i = 0; i < uniqueCount; i++) {
            if (totals[i] > amount) resultCount++;
        }

        SalesRecordResult[] result = new SalesRecordResult[resultCount];
        int pos = 0;
        for (int i = 0; i < uniqueCount; i++) {
            if (totals[i] > amount) {
                result[pos++] = new SalesRecordResult(productIds[i], totals[i]);
            }
        }
        return result;
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
        int end = Math.min(start + pageSize, count);
        int length = Math.max(end - start, 0);
        SalesRecord[] result = new SalesRecord[length];
        for (int i = 0; i < length; i++) {
            result[i] = records[start + i];
        }
        return result;
    }

    public int getRecordCount() {
        return count;
    }

    public void removeRecord(String id) {
        removeSalesRecordsFor(id);
    }
}