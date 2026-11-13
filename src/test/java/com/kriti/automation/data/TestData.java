package com.kriti.automation.data;

import java.math.BigDecimal;
import java.util.List;

/** One place for test data, so a change (e.g. a renamed product) is made once. */
public final class TestData {

    private TestData() {
    }

    public record Customer(String firstName, String lastName, String postalCode) {
    }

    public static final Customer VALID_CUSTOMER = new Customer("Kriti", "Sharma", "700000");

    public static final String BACKPACK = "Sauce Labs Backpack";
    public static final String BIKE_LIGHT = "Sauce Labs Bike Light";

    /** The full SauceDemo catalogue in Name (A to Z) order. */
    public static final List<String> PRODUCTS_A_TO_Z = List.of(
            "Sauce Labs Backpack",
            "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt",
            "Sauce Labs Fleece Jacket",
            "Sauce Labs Onesie",
            "Test.allTheThings() T-Shirt (Red)");

    public static final int PRODUCT_COUNT = PRODUCTS_A_TO_Z.size();

    /** SauceDemo charges 8% tax on the item total. */
    public static final BigDecimal TAX_RATE = new BigDecimal("0.08");

    public static final String PROBLEM_USER = "problem_user";
}
