package com.kriti.automation.bdd;

import com.kriti.automation.data.TestData;
import com.kriti.automation.pages.InventoryPage;
import com.kriti.automation.pages.InventoryPage.SortOption;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortSteps {

    private final ScenarioContext context;

    public SortSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I sort the products by {string}")
    public void iSortTheProductsBy(String label) {
        context.inventoryPage().sortBy(SortOption.fromLabel(label));
    }

    @Then("the products should be sorted by {word} in {word} order")
    public void theProductsShouldBeSorted(String field, String direction) {
        InventoryPage page = context.inventoryPage();
        switch (field) {
            case "name" -> {
                List<String> expected = new ArrayList<>(TestData.PRODUCTS_A_TO_Z);
                if (direction.equals("descending")) {
                    expected.sort(Comparator.reverseOrder());
                }
                Assert.assertEquals(page.getProductNames(), expected);
            }
            case "price" -> assertSorted(page.getProductPrices(), direction);
            default -> throw new IllegalArgumentException("Unknown sort field: " + field);
        }
    }

    private static <T extends Comparable<T>> void assertSorted(List<T> actual, String direction) {
        Assert.assertEquals(actual.size(), TestData.PRODUCT_COUNT, "Number of products shown");
        List<T> expected = new ArrayList<>(actual);
        expected.sort(direction.equals("descending") ? Comparator.reverseOrder() : Comparator.naturalOrder());
        Assert.assertEquals(actual, expected);
    }
}
