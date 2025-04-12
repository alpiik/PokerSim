package fp.sales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

public class Repository {

    private static final String FILE_PATH = "src/fp/sales/sales-data.csv";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<Entry> getEntries() {
        try (Stream<String> lines = Files.lines(Paths.get(FILE_PATH))) {
            return lines
                    .skip(1)
                    .map(line -> line.split("\t"))
                    .filter(parts -> parts.length >= 7)
                    .map(this::createEntry)
                    .toList();
        } catch (IOException e) {
            System.err.println("Error reading sales data file: " + e.getMessage());
            return List.of();
        } catch (RuntimeException e) {
            System.err.println("Error processing sales data stream: " + e.getMessage());
            return List.of();
        }
    }
    private Entry createEntry(String[] parts) {
        try {
            int rowNo = Integer.parseInt(parts[0].trim());
            LocalDate date = LocalDate.parse(parts[1].trim(), formatter);
            String state = parts[2].trim();
            String productId = parts[3].trim();
            String category = parts[4].trim();
            Double amount = Double.parseDouble(parts[6].trim().replace(',', '.'));
            return new Entry(rowNo, productId, date, state, category, amount);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            System.err.println("Skipping malformed line due to parsing error: " + String.join("\t", parts) + " | Error: " + e.getMessage());
            throw new RuntimeException("Failed to parse entry: " + String.join("\t", parts), e);
        }
    }
}