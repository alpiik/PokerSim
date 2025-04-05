package generics.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCart<T extends CartItem> {

    private final List<CartEntry> items = new ArrayList<>();
    private final List<Double> discounts = new ArrayList<>();
    private Double couponDiscount = 0.0;

    private final Map<String, Double> couponToDiscount = Map.of(
            "Sale5", 5.0,
            "Sale8", 8.0,
            "Sale10", 10.0);

    public void add(T item) {
        Optional<CartEntry> existingEntry = findEntryById(item.id());

        if (existingEntry.isPresent()) {
            existingEntry.get().quantity++;
        } else {
            items.add(new CartEntry(item, 1));
        }
    }
    public void removeById(String id) {
        items.removeIf(entry -> entry.item.id().equals(id));
    }
    public Double getTotal() {
        double rawTotal = items.stream()
                .mapToDouble(entry -> entry.item.price() * entry.quantity)
                .sum();

        double discountedTotal = rawTotal;
        for (Double discountPercentage : discounts) {
            discountedTotal *= (1.0 - discountPercentage / 100.0);
        }
        discountedTotal *= (1.0 - couponDiscount / 100.0);

        return discountedTotal;
    }

    public List<CartEntry> getContents() {
        return items;
    }
    public void increaseQuantity(String id) {
        findEntryById(id).ifPresent(entry -> entry.quantity++);
    }
    public void applyDiscountPercentage(Double discount) {
        if (discount != null && discount > 0) {
            discounts.add(discount);
        }
    }

    public boolean applyCoupon(String coupon) {
        Double discount = couponToDiscount.get(coupon);
        if (discount != null) {
            this.couponDiscount = discount;
            return true;
        }
        return false;
    }

    public Double getTotalDiscount() {
        double finalMultiplier = 1.0;

        for (Double discountPercentage : discounts) {
            finalMultiplier *= (1.0 - discountPercentage / 100.0);
        }

        finalMultiplier *= (1.0 - couponDiscount / 100.0);

        return (1.0 - finalMultiplier) * 100.0;
    }
    public void removeLastDiscount() {
        if (!discounts.isEmpty()) {
            discounts.remove(discounts.size() - 1);
        }
    }

    public void addAll(List<T> itemsToAdd) {
        if (itemsToAdd != null) {
            for (T item : itemsToAdd) {
                this.add(item);
            }
        }
    }
    private Optional<CartEntry> findEntryById(String id) {
        return items.stream()
                .filter(entry -> entry.item.id().equals(id))
                .findFirst();
    }
    @Override
    public String toString() {
        return items.stream()
                .map(CartEntry::toString) // Kasutab CartEntry enda toString() meetodit
                .collect(Collectors.joining(", "));
    }
}