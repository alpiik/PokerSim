package inheritance.analyser;

import java.time.LocalDate;
import java.util.List;

public final class FinnishSalesAnalyser extends AbstractSalesAnalyser {

    public FinnishSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double getVatRate(LocalDate date) {
        if (date.isBefore(LocalDate.of(1994, 6, 1))) {
            return 0.0; // No VAT before 01.06.1994
        } else if (date.isBefore(LocalDate.of(2010, 7, 1))) {
            return 22.0; // 22% VAT from 01.06.1994 to 30.06.2010
        } else if (date.isBefore(LocalDate.of(2013, 1, 1))) {
            return 23.0; // 23% VAT from 01.07.2010 to 31.12.2012
        } else if (date.isBefore(LocalDate.of(2024, 9, 1))) {
            return 24.0; // 24% VAT from 01.01.2013 to 31.08.2024
        } else {
            return 25.5; // 25.5% VAT from 01.09.2024 onwards
        }
    }
}