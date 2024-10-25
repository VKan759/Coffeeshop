package kz.onetech.onetechproject.model;

import lombok.Getter;

@Getter
public enum Size {
    S(1),
    M(1.5),
    L(2),
    DEFAULT(1);
    private final double sizeCoefficient;

    Size(double sizeCoefficient) {
        this.sizeCoefficient = sizeCoefficient;
    }
}
