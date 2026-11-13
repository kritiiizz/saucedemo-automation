package com.kriti.automation.bdd;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class CartSteps {

    private final ScenarioContext context;

    public CartSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String product) {
        context.inventoryPage().addToCart(product);
    }

    @When("I remove {string} from the products page")
    public void iRemoveFromTheProductsPage(String product) {
        context.inventoryPage().removeFromCart(product);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        context.setCartPage(context.inventoryPage().openCart());
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String product) {
        context.cartPage().removeItem(product);
    }

    @Then("the cart badge should show {int}")
    public void theCartBadgeShouldShow(int expected) {
        int actual = context.inventoryPage().waitForCartCount(expected).getCartCount();
        Assert.assertEquals(actual, expected, "Cart badge");
    }

    @Then("the cart should contain only {string}")
    public void theCartShouldContainOnly(String product) {
        Assert.assertEquals(context.cartPage().getItemNames(), List.of(product));
    }
}
