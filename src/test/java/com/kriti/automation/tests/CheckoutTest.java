package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.data.TestData.Customer;
import com.kriti.automation.pages.CheckoutCompletePage;
import com.kriti.automation.pages.CheckoutInfoPage;
import com.kriti.automation.pages.CheckoutOverviewPage;
import com.kriti.automation.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.kriti.automation.data.TestData.BACKPACK;
import static com.kriti.automation.data.TestData.BIKE_LIGHT;
import static com.kriti.automation.data.TestData.TAX_RATE;
import static com.kriti.automation.data.TestData.VALID_CUSTOMER;

public class CheckoutTest extends BaseTest {

    private CheckoutInfoPage startCheckoutWith(String... products) {
        InventoryPage inventory = openLoginPage().loginAsStandardUser();
        for (String product : products) {
            inventory.addToCart(product);
        }
        return inventory.openCart().checkout();
    }

    private CheckoutOverviewPage overviewWith(String... products) {
        return startCheckoutWith(products)
                .enterDetails(VALID_CUSTOMER.firstName(), VALID_CUSTOMER.lastName(), VALID_CUSTOMER.postalCode())
                .continueToOverview();
    }

    @Test(groups = {"smoke", "regression"}, description = "TC_CHK_01: Customer completes an order end to end")
    public void completeOrderEndToEnd() {
        CheckoutCompletePage complete = overviewWith(BACKPACK, BIKE_LIGHT).finish();

        Assert.assertEquals(complete.getHeader(), "Thank you for your order!");
    }

    @Test(groups = {"regression"}, description = "TC_CHK_02: Overview lists the products in the cart")
    public void overviewListsCartProducts() {
        CheckoutOverviewPage overview = overviewWith(BACKPACK, BIKE_LIGHT);

        Assert.assertEqualsNoOrder(overview.getItemNames(), List.of(BACKPACK, BIKE_LIGHT));
    }

    /** Soft assertions: one run reports every wrong number, not just the first. */
    @Test(groups = {"regression"}, description = "TC_CHK_03: Item total, 8% tax and order total are calculated correctly")
    public void totalsAreCalculatedCorrectly() {
        CheckoutOverviewPage overview = overviewWith(BACKPACK, BIKE_LIGHT);

        BigDecimal itemTotal = overview.getItemTotal();
        BigDecimal tax = overview.getTax();
        BigDecimal total = overview.getTotal();
        BigDecimal expectedTax = itemTotal.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(itemTotal.compareTo(overview.sumOfItemPrices()), 0,
                "Item total " + itemTotal + " should equal the sum of product prices " + overview.sumOfItemPrices());
        soft.assertEquals(tax.compareTo(expectedTax), 0,
                "Tax " + tax + " should be 8% of " + itemTotal + " = " + expectedTax);
        soft.assertEquals(total.compareTo(itemTotal.add(tax)), 0,
                "Total " + total + " should equal item total + tax = " + itemTotal.add(tax));
        soft.assertAll();
    }

    @DataProvider(name = "missingCustomerInfo")
    public Object[][] missingCustomerInfo() {
        Customer c = VALID_CUSTOMER;
        return new Object[][]{
                {"",            c.lastName(), c.postalCode(), "First Name is required"},   // TC_CHK_04
                {c.firstName(), "",           c.postalCode(), "Last Name is required"},    // TC_CHK_05
                {c.firstName(), c.lastName(), "",             "Postal Code is required"},  // TC_CHK_06
        };
    }

    @Test(dataProvider = "missingCustomerInfo", groups = {"regression"},
            description = "TC_CHK_04-06: Checkout form validates required fields")
    public void requiredFieldsAreValidated(String first, String last, String zip, String expectedError) {
        CheckoutInfoPage info = startCheckoutWith(BACKPACK).enterDetails(first, last, zip);
        info.submit();

        String actual = info.getErrorMessage();
        Assert.assertTrue(actual.contains(expectedError),
                "Expected error containing '" + expectedError + "' but got '" + actual + "'");
    }
}
