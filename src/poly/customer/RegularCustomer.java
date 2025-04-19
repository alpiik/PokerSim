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
        // Outer if statement
        if (order.total() >= 100) {
            long daysBetween = ChronoUnit.DAYS.between(lastOrderDate, order.date());

            // Inner if statement - Add braces
            if (daysBetween < 30) {
                // Body of inner if - Add braces
                // Assuming order.total() returns int
                // Integer arithmetic, no unnecessary cast, no useless parentheses
                this.bonusPoints += order.total() * 3 / 2;
            } else { // Else for inner if - Add braces
                // Body of else - Add braces
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