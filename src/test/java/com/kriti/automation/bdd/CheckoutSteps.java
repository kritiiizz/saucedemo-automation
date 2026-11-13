package com.kriti.automation.bdd;

import com.kriti.automation.data.TestData;
import com.kriti.automation.pages.CheckoutCompletePage;
import com.kriti.automation.pages.CheckoutInfoPage;
import com.kriti.automation.pages.CheckoutOverviewPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutSteps {

    private final ScenarioContext context;

    public CheckoutSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I proceed to checkout with {string}, {string}, {string}")
    public void iProceedToCheckoutWith(String first, String last, String zip) {
        CheckoutInfoPage info = context.inventoryPage().openCart().checkout().enterDetails(first, last, zip);
        context.setCheckoutInfoPage(info);
        info.submit();
    }

    @Then("the order total should equal item total plus 8% tax")
    public void theOrderTotalShouldBeCorrect() {
        CheckoutOverviewPage overview = new CheckoutOverviewPage(context.driver());
        BigDecimal itemTotal = overview.getItemTotal();
        BigDecimal tax = overview.getTax();
        BigDecimal expectedTax = itemTotal.multiply(TestData.TAX_RATE).setScale(2, RoundingMode.HALF_UP);

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(itemTotal.compareTo(overview.sumOfItemPrices()), 0, "Item total vs sum of prices");
        soft.assertEquals(tax.compareTo(expectedTax), 0, "Tax " + tax + " vs expected " + expectedTax);
        soft.assertEquals(overview.getTotal().compareTo(itemTotal.add(tax)), 0, "Total vs item total + tax");
        soft.assertAll();
    }

    @When("I finish the order")
    public void iFinishTheOrder() {
        new CheckoutOverviewPage(context.driver()).finish();
    }

    @Then("I should see the confirmation {string}")
    public void iShouldSeeTheConfirmation(String expected) {
        Assert.assertEquals(new CheckoutCompletePage(context.driver()).getHeader(), expected);
    }

    @Then("I should see a checkout error containing {string}")
    public void iShouldSeeACheckoutError(String expected) {
        String actual = context.checkoutInfoPage().getErrorMessage();
        Assert.assertTrue(actual.contains(expected), "Got: " + actual);
    }
}
