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
        // Outer if statement - no brace warning here
        if (order.total() >= 100) {
            long daysBetween = ChronoUnit.DAYS.between(lastOrderDate, order.date());

            // Inner if statement - Add braces (likely lines 46)
            if (daysBetween < 30) { // Likely line 46
                // Body of inner if - Add braces
                // Assuming order.total() returns int
                // Integer arithmetic, remove unnecessary cast (likely line 23) and useless parentheses
                this.bonusPoints += order.total() * 3 / 2;
            } else { // Likely line 47
                // Body of else - Add braces (likely line 48)
                // Assuming order.total() returns int
                // Integer arithmetic, no cast needed
                this.bonusPoints += order.total();
            }
        }
        // This line updates the last order date regardless of bonus, it is outside the outer if
        this.lastOrderDate = order.date();
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