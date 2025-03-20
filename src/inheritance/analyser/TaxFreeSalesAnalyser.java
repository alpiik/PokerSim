package inheritance.analyser;

import java.time.LocalDate;
import java.util.List;

public final class TaxFreeSalesAnalyser extends AbstractSalesAnalyser {

    public TaxFreeSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double getVatRate(LocalDate date) {
        return 0.0;
    }
}