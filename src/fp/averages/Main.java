package fp.averages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<List<Integer>> rows = getData();

        double average = rows.stream()
                .flatMap(List::stream)
                .collect(Collectors.averagingInt(Integer::intValue));
        System.out.printf("Kõikide numbrite keskmine väärtus on: %.2f%n", average);
    }
    private static List<List<Integer>> getData() throws IOException {
        List<String> lines = Files.readAllLines(
                Path.of("src/fp/averages/data.csv"));
        return lines.stream()
                .map(line -> line.split(", "))
                .map(List::of)
                .map(Main::toNumberList)
                .toList();
    }
    private static List<Integer> toNumberList(List<String> numbers) {
        return numbers.stream().map(Integer::parseInt).toList();
    }

}