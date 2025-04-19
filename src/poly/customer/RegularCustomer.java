package poly.customer;

import java.time.LocalDate;
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
        if (order.total() >= 100) {
            LocalDate monthAfter = lastOrderDate.plusMonths(1);
            double bonusAmount = order.total();

            if (order.date().isBefore(monthAfter)) {
                bonusAmount *= 1.5;
            }

            this.bonusPoints += (int) Math.round(bonusAmount);  // <-- only 1 cast
        }

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