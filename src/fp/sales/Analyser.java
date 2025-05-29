    package fp.sales;

    import java.time.LocalDate;
    import java.util.Comparator;
    import java.util.List;
    import java.util.Map;
    import java.util.function.Function;
    import java.util.stream.Collectors;

    public class Analyser {

        private final AccountingService accountingService;
        private final List<Entry> entries;
        public Analyser(Repository repository, AccountingService accountingService) {
            this.accountingService = accountingService;
            this.entries = repository.getEntries();
        }
        public Double getTotalSales() {
            return entries.stream()
                    .mapToDouble(Entry::amount)
                    .sum();
        }
        public Double getSalesByCategory(String category) {
            return entries.stream()
                    .filter(entry -> entry.category().equals(category))
                    .mapToDouble(Entry::amount)
                    .sum();
        }
        public Double getSalesBetween(LocalDate start, LocalDate end) {
            return entries.stream()
                    .filter(entry -> !entry.date().isBefore(start) && !entry.date().isAfter(end))
                    .mapToDouble(Entry::amount)
                    .sum();
        }
        public String mostExpensiveItems() {
            return entries.stream()
                    .sorted(Comparator.comparingDouble(Entry::amount).reversed())
                    .limit(3)
                    .map(Entry::productId)
                    .sorted()
                    .collect(Collectors.joining(", "));
        }
        public String statesWithBiggestSales() {
            return entries.stream()
                    .collect(Collectors.groupingBy(
                            Entry::state,
                            Collectors.summingDouble(Entry::amount)
                    ))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
        }
        public String findMostProfitableItems() {
            Map<String, Double> profitMarginMap = entries.stream()
                    .map(Entry::productId)
                    .distinct()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            accountingService::getProfitMargin
                    ));
            return entries.stream()
                    .collect(Collectors.groupingBy(
                            Entry::productId,
                            Collectors.summingDouble(entry ->
                                    entry.amount() * profitMarginMap.getOrDefault(entry.productId(), 0.0))
                    ))
                    .entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                    .limit(3)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
        }
        public List<Entry> getAllRecordsPaged(int pageNumber, int pageSize) {
            return entries.stream()
                    .sorted(Comparator.comparing(Entry::date).thenComparingInt(Entry::rowNo))
                    .skip((long) pageNumber * pageSize)
                    .limit(pageSize)
                    .toList();
        }
        public List<String> getCategoryList() {
            return entries.stream()
                    .map(Entry::category)
                    .distinct()
                    .sorted()
                    .toList();
        }
        public int getRecordCount() {
            return entries.size();
        }
    }