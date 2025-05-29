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
        class Entry {
            String productId;
            int total;
        }

        Entry[] entries = new Entry[10];
        int entryCount = 0;

        for (int i = 0; i < count; i++) {
            SalesRecord r = records[i];
            String id = r.productId();
            int total = r.productPrice() * r.itemsSold();

            int index = -1;
            for (int j = 0; j < entryCount; j++) {
                if (entries[j].productId.equals(id)) {
                    index = j;
                    break;
                }
            }

            if (index != -1) {
                entries[index].total += total;
            } else {
                if (entryCount == entries.length) {
                    Entry[] newEntries = new Entry[entries.length * 2];
                    for (int j = 0; j < entries.length; j++) {
                        newEntries[j] = entries[j];
                    }
                    entries = newEntries;
                }
                Entry e = new Entry();
                e.productId = id;
                e.total = total;
                entries[entryCount++] = e;
            }
        }

        int resultCount = 0;
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].total > amount) {
                resultCount++;
            }
        }

        SalesRecordResult[] result = new SalesRecordResult[resultCount];
        int pos = 0;
        for (int i = 0; i < entryCount; i++) {
            if (entries[i].total > amount) {
                result[pos++] = new SalesRecordResult(entries[i].productId, entries[i].total);
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
