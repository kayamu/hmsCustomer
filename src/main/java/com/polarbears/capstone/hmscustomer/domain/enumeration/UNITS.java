package com.polarbears.capstone.hmscustomer.domain.enumeration;

/**
 * The UNITS enumeration.
 */
public enum UNITS {
    KG("Kg"),
    LB("lb");

    private final String value;

    UNITS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
