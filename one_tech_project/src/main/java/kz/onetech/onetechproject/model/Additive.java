package kz.onetech.onetechproject.model;

import lombok.Getter;

@Getter
public enum Additive {
    SYRUP(100),
    ALTERNATIVE_MILK(300),
    DECAF(200);

    private final double price;

    Additive(double price) {
        this.price = price;
    }

}
