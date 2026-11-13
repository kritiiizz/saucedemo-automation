package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.pages.InventoryPage;
import com.kriti.automation.pages.InventoryPage.SortOption;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.kriti.automation.data.TestData.PRODUCTS_A_TO_Z;
import static com.kriti.automation.data.TestData.PRODUCT_COUNT;

public class SortTest extends BaseTest {

    /**
     * A to Z is the default order, so sorting A to Z straight after login would pass even if
     * the dropdown did nothing. Sorting Z to A first proves the A to Z sort really re-orders the list.
     */
    @Test(groups = {"regression"}, description = "TC_SORT_01: Sort products by name A to Z")
    public void sortByNameAscending() {
        List<String> names = openLoginPage().loginAsStandardUser()
                .sortBy(SortOption.NAME_Z_TO_A)
                .sortBy(SortOption.NAME_A_TO_Z)
                .getProductNames();

        Assert.assertEquals(names, PRODUCTS_A_TO_Z);
    }

    @Test(groups = {"regression"}, description = "TC_SORT_02: Sort products by name Z to A")
    public void sortByNameDescending() {
        List<String> names = sortedPage(SortOption.NAME_Z_TO_A).getProductNames();

        Assert.assertEquals(names, sortedCopy(PRODUCTS_A_TO_Z, Comparator.reverseOrder()));
    }

    @Test(groups = {"smoke", "regression"}, description = "TC_SORT_03: Sort products by price low to high")
    public void sortByPriceAscending() {
        List<BigDecimal> prices = sortedPage(SortOption.PRICE_LOW_TO_HIGH).getProductPrices();

        Assert.assertEquals(prices.size(), PRODUCT_COUNT, "Number of products shown");
        Assert.assertEquals(prices, sortedCopy(prices, Comparator.naturalOrder()));
    }

    @Test(groups = {"regression"}, description = "TC_SORT_04: Sort products by price high to low")
    public void sortByPriceDescending() {
        List<BigDecimal> prices = sortedPage(SortOption.PRICE_HIGH_TO_LOW).getProductPrices();

        Assert.assertEquals(prices.size(), PRODUCT_COUNT, "Number of products shown");
        Assert.assertEquals(prices, sortedCopy(prices, Comparator.reverseOrder()));
    }

    private InventoryPage sortedPage(SortOption option) {
        return openLoginPage().loginAsStandardUser().sortBy(option);
    }

    private static <T> List<T> sortedCopy(List<T> list, Comparator<? super T> order) {
        List<T> copy = new ArrayList<>(list);
        copy.sort(order);
        return copy;
    }
}
