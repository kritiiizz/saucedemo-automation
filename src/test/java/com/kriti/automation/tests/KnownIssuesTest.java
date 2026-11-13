package com.kriti.automation.tests;

import com.kriti.automation.base.BaseTest;
import com.kriti.automation.config.ConfigReader;
import com.kriti.automation.pages.InventoryPage;
import com.kriti.automation.pages.InventoryPage.SortOption;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static com.kriti.automation.data.TestData.PROBLEM_USER;
import static com.kriti.automation.data.TestData.PRODUCT_COUNT;

/**
 * Automated checks for defects logged in docs/BUG_REPORTS.md.
 * Each test asserts the CORRECT behaviour, so it fails while the bug is open
 * and starts passing when the bug is fixed - that is the signal to re-test and close it.
 * Excluded from testng.xml; run with: mvn clean test -DsuiteXmlFile=known-issues.xml
 */
public class KnownIssuesTest extends BaseTest {

    private InventoryPage loginAsProblemUser() {
        return openLoginPage().loginAs(PROBLEM_USER, ConfigReader.get("password"));
    }

    @Test(groups = {"known-issues"}, description = "BUG-001: Every product shows its own image (problem_user)")
    public void eachProductHasItsOwnImage() {
        List<String> images = loginAsProblemUser().getProductImageSources();

        Assert.assertEquals(images.size(), PRODUCT_COUNT, "Number of product images");
        Assert.assertEquals(new HashSet<>(images).size(), PRODUCT_COUNT,
                "Each product should have a different image, but got: " + images);
    }

    @Test(groups = {"known-issues"}, description = "BUG-002: Sorting by price low to high works (problem_user)")
    public void sortByPriceWorksForProblemUser() {
        List<BigDecimal> prices = loginAsProblemUser().sortBy(SortOption.PRICE_LOW_TO_HIGH).getProductPrices();

        List<BigDecimal> expected = new ArrayList<>(prices);
        expected.sort(Comparator.naturalOrder());
        Assert.assertEquals(prices, expected, "Prices should be in ascending order");
    }
}
