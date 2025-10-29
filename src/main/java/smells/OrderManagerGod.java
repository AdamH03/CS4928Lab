package smells;
import common.Money;
import factory.ProductFactory;
import catalog.Product;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10; // Global/Static State
    public static String LAST_DISCOUNT_CODE = null; // Global/Static State
    public static String process(String recipe, int qty, String
            paymentType, String discountCode, boolean printReceipt) {
        ProductFactory factory = new ProductFactory(); // Feature Envy / Shotgun Surgery
        Product product = factory.create(recipe); // Feature Envy / Shotgun Surgery
        Money unitPrice;
        try {
            var priced = product instanceof catalog.Priced
                    p ? p.price() : product.basePrice();
            unitPrice = priced; // Feature Envy / Shotgun Surgery
        } catch (Exception e) {
            unitPrice = product.basePrice(); // Feature Envy / Shotgun Surgery
        }
        if (qty <= 0) qty = 1; // Primitive Obsession
        Money subtotal = unitPrice.multiply(qty); // Duplicated Logic
        Money discount = Money.zero();
        if (discountCode != null) {
            if (discountCode.equalsIgnoreCase("LOYAL5")) { // Primitive Obsession
                discount = Money.of(subtotal.asBigDecimal()
                        .multiply(java.math.BigDecimal.valueOf(5))
                        .divide(java.math.BigDecimal.valueOf(100))); // Duplicated Logic
            } else if (discountCode.equalsIgnoreCase("COUPON1")) { // Primitive Obsession
                discount = Money.of(1.00); // Duplicated Logic
            } else if (discountCode.equalsIgnoreCase("NONE")) { // Primitive Obsession
                discount = Money.zero(); // Duplicated Logic
            } else {
                discount = Money.zero(); // Duplicated Logic
            }
            LAST_DISCOUNT_CODE = discountCode; // Global/Static State
        }
        Money discounted =
                Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal())); // Duplicated Logic
        if (discounted.asBigDecimal().signum() < 0) discounted =
                Money.zero(); // Duplicated Logic
        var tax = Money.of(discounted.asBigDecimal().multiply(java.math.BigDecimal.valueOf(TAX_PERCENT)).divide(java.math.BigDecimal.valueOf(100))); // Feature Envy / Shotgun Surgery
        var total = discounted.add(tax); // Duplicated Logic
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) { // Primitive Obsession
                System.out.println("[Cash] Customer paid " + total + "EUR"); // Feature Envy / Shotgun Surgery
            } else if (paymentType.equalsIgnoreCase("CARD")) { // Primitive Obsession
                System.out.println("[Card] Customer paid " + total + "EUR with card ****1234"); // Feature Envy / Shotgun Surgery
            } else if (paymentType.equalsIgnoreCase("WALLET")) { // Primitive Obsession
                System.out.println("[Wallet] Customer paid " + total + "EUR via wallet user-wallet-789"); // Feature Envy / Shotgun Surgery
            } else {
                System.out.println("[UnknownPayment] " + total); // Feature Envy / Shotgun Surgery
            }
        }
        StringBuilder receipt = new StringBuilder(); // God Class & Long Method
        receipt.append("Order (").append(recipe).append(")x").append(qty).append("\n"); // God Class & Long Method
        receipt.append("Subtotal: ").append(subtotal).append("\n"); // God Class & Long Method
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n"); // God Class & Long Method
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n"); // God Class & Long Method
        receipt.append("Total: ").append(total); // God Class & Long Method
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out); // God Class & Long Method
        }
        return out;
    }
}
