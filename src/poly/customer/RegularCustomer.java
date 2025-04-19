package poly.customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class RegularCustomer extends AbstractCustomer {

    private LocalDate lastOrderDate;

    public RegularCustomer(String id, String name,
                           int bonusPoints, LocalDate lastOrderDate) {
        super(id, name, bonusPoints);
        this.lastOrderDate = lastOrderDate;
    }

    @Override
    public void collectBonusPointsFrom(Order order) {
        // Add braces to the outer if statement
        if (order.total() >= 100) {
            long daysBetween = ChronoUnit.DAYS.between(lastOrderDate, order.date());
            double bonusAmount; // Use a double to calculate bonus before casting

            // Add braces to the inner if statement
            if (daysBetween < 30) {
                // Calculate the bonus amount as a double
                bonusAmount = order.total() * 1.5;
            } else {
                // Calculate the bonus amount as a double
                bonusAmount = order.total();
            }
            // Cast only once when adding to the integer bonusPoints field
            this.bonusPoints += (int) bonusAmount;
        }
        // Add braces to this if statement if it's intended to contain the line below
        // Based on original code structure, the line below seems outside this if,
        // but adding braces to the outer if makes it clearer.
        // If the analysis tool flagged line 46 as the start of the outer if body,
        // then the code is correct as is regarding that point.
        // Let's just add braces to the inner if/else for sure.
        // Re-reading the error: lines 46, 47, 48 need braces.
        // This corresponds to the if(daysBetween < 30) { ... } else { ... } structure.
        // Let's apply braces there. The outer if already seems to implicitly cover the block.

        // Corrected structure based on error lines 46, 47, 48 and original code logic:
        if (order.total() >= 100) { // Assuming this is before line 46
            long daysBetween = ChronoUnit.DAYS.between(lastOrderDate, order.date());
            double bonusAmount; // Use a double to calculate bonus before casting

            if (daysBetween < 30) { // This is likely line 46 or shortly after
                // Add braces here
                bonusAmount = order.total() * 1.5;
            } else { // This is likely line 47 or shortly after
                // Add braces here
                bonusAmount = order.total(); // This line is likely line 48 or shortly after
            }
            // This line (adding to bonusPoints) is likely line 49, hence the cast warning
            this.bonusPoints += (int) bonusAmount; // Cast only once here
        }
        this.lastOrderDate = order.date(); // This line is outside the outer if
    }

    public LocalDate getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(LocalDate lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }


    @Override
    public String asString() {
        return "REGULAR;" + id + ";" + name + ";" + bonusPoints + ";" + lastOrderDate.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        RegularCustomer that = (RegularCustomer) o;
        return Objects.equals(lastOrderDate, that.lastOrderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lastOrderDate);
    }
}