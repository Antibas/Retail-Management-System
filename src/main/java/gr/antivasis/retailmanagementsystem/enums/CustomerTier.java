package gr.antivasis.retailmanagementsystem.enums;

public enum CustomerTier {
    SILVER(1), GOLD(1.5), PLATINUM(2);

    public final double pointsMultiplier;

    CustomerTier(double pointsMultiplier) {
        this.pointsMultiplier = pointsMultiplier;
    }

    public static CustomerTier fromPoints(double points) {
        if (points >= 0d && points <= 499d) {
            return SILVER;
        } else if (points >= 500d && points <= 1999d) {
            return GOLD;
        } else if (points >= 2000d) {
            return PLATINUM;
        }
        throw new IllegalArgumentException("Invalid points: " + points);
//        return switch (points) {
//            case 0 -> SILVER;
//            case 100 -> GOLD;
//            case 200 -> PLATINUM;
//            default -> throw new IllegalArgumentException("Invalid points: " + points);
//        };
    }
}
