package inheritance.analyser;

import java.time.LocalDate;
import java.util.List;

public final class EstonianTaxSalesAnalyser extends AbstractSalesAnalyser {

    public EstonianTaxSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double getVatRate(LocalDate date) {
        if (date.isBefore(LocalDate.of(2009, 7, 1))) {
            return 0.0; // No VAT before 01.07.2009
        } else if (date.isBefore(LocalDate.of(2024, 1, 1))) {
            return 20.0; // 20% VAT from 01.07.2009 to 31.12.2023
        } else if (date.isBefore(LocalDate.of(2025, 7, 1))) {
            return 22.0; // 22% VAT from 01.01.2024 to 30.06.2025
        } else {
            return 24.0; // 24% VAT from 01.07.2025 onwards
        }
    }
}