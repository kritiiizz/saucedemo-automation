package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.pages.CartPage;
import com.kriti.automation.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.kriti.automation.data.TestData.BACKPACK;
import static com.kriti.automation.data.TestData.BIKE_LIGHT;

public class CartTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, description = "TC_CART_01: Adding a product updates the cart badge")
    public void addSingleProductUpdatesBadge() {
        InventoryPage inventory = openLoginPage().loginAsStandardUser()
                .addToCart(BACKPACK)
                .waitForCartCount(1);

        Assert.assertEquals(inventory.getCartCount(), 1);
        Assert.assertEquals(inventory.getButtonText(BACKPACK), "Remove");
    }

    @Test(groups = {"regression"}, description = "TC_CART_02: Cart shows exactly the products that were added")
    public void cartContainsAddedProducts() {
        CartPage cart = openLoginPage().loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BIKE_LIGHT)
                .openCart();

        Assert.assertEqualsNoOrder(cart.getItemNames(), List.of(BACKPACK, BIKE_LIGHT));
    }

    @Test(groups = {"regression"}, description = "TC_CART_03: Removing a product from the inventory page")
    public void removeProductFromInventory() {
        InventoryPage inventory = openLoginPage().loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BIKE_LIGHT)
                .removeFromCart(BACKPACK)
                .waitForCartCount(1);

        Assert.assertEquals(inventory.getCartCount(), 1);
        Assert.assertEquals(inventory.getButtonText(BACKPACK), "Add to cart");
    }

    @Test(groups = {"regression"}, description = "TC_CART_04: Removing a product from the cart page")
    public void removeProductFromCartPage() {
        CartPage cart = openLoginPage().loginAsStandardUser()
                .addToCart(BACKPACK)
                .addToCart(BIKE_LIGHT)
                .openCart()
                .removeItem(BACKPACK);

        Assert.assertEquals(cart.getItemNames(), List.of(BIKE_LIGHT));
    }

    @Test(groups = {"regression"}, description = "TC_UI_02: Cart contents persist after logout and login")
    public void cartPersistsAfterLogoutAndLogin() {
        InventoryPage inventory = openLoginPage().loginAsStandardUser()
                .addToCart(BACKPACK)
                .waitForCartCount(1)
                .logout()
                .loginAsStandardUser()
                .waitForCartCount(1);

        Assert.assertEquals(inventory.getCartCount(), 1, "Cart badge after logging back in");
        Assert.assertEquals(inventory.openCart().getItemNames(), List.of(BACKPACK));
    }
}
