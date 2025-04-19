package poly.customer;

public final class GoldCustomer extends AbstractCustomer {

    public GoldCustomer(String id, String name, int bonusPoints) {
        super(id, name, bonusPoints);
    }

    @Override
    public void collectBonusPointsFrom(Order order) {
        if (order.total() >= 100) {
            this.bonusPoints += (order.total() * 3) / 2;
        }
    }

    @Override
    public String asString() {
        return "GOLD;" + id + ";" + name + ";" + bonusPoints + ";";
    }
}