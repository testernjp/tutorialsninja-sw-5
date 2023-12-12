package com.tutorialsninja.demo.testsuite;

import com.tutorialsninja.demo.customlisteners.CustomListeners;
import com.tutorialsninja.demo.pages.DesktopPage;
import com.tutorialsninja.demo.pages.HomePage;
import com.tutorialsninja.demo.pages.ProductPage;
import com.tutorialsninja.demo.pages.ShoppingCartPage;
import com.tutorialsninja.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(CustomListeners.class)
public class DesktopsTest extends BaseTest {

    HomePage homePage;
    DesktopPage desktopPage;
    ProductPage productPage;
    ShoppingCartPage shoppingCartPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        homePage = new HomePage();
        desktopPage = new DesktopPage();
        productPage = new ProductPage();
        shoppingCartPage = new ShoppingCartPage();
    }

    @Test(groups = {"sanity", "regression"})
    public void verifyProductArrangeInAlphabeticalOrder() {
        //1.1 Mouse hover on Desktops Tab. and Click
        homePage.mouseHoverAndClickOnDesktop();
        //1.2 Click on “Show All Desktops”
        homePage.selectMenu("Desktops");
        //1.3 Select Sort By position "Name: Z to A"
        desktopPage.clickOnSortByPosition();
        desktopPage.selectSortByZToA("Name (Z - A)");
        //1.4 Verify the Product will arrange in Descending order.
        Assert.assertEquals(desktopPage.afterSorting(), desktopPage.beforeSorting(), "Product not sorted into Z to A order");
    }

    @Test(groups = {"smoke", "regression"})
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
        //2.1 Mouse hover on Currency Dropdown and click
        //2.2 Mouse hover on £Pound Sterling and click
        homePage.chooseGBP();
        //2.3 Mouse hover on Desktops Tab.
        //2.4 Click on “Show All Desktops”
        homePage.selectMenu("Desktops");
        //2.5 Select Sort By position "Name: A to Z"
        desktopPage.selectSortByAToZ("Name (A - Z)");
        //2.6 Select product <product>
        desktopPage.clickOnHPLP3065();
        //2.7 Enter Qty <qty> using Select class.
        String expectedProduct = "HP LP3065";
        String actualProduct = productPage.getHPLP3065text();
        Assert.assertEquals(actualProduct, expectedProduct, "HP LP3065 not displayed");
        productPage.selectDate("27", "January", "2023");
        productPage.clearAndAddQuantity("1");
        //2.8 Click on “Add to Cart” button
        productPage.clickAddToCart();
        //2.9 Verify the Message <successMessage>
        Assert.assertTrue(productPage.isSuccessMessageAppearing(), "Message Doesn't Appear");
        //2.10 Click on link “shopping cart” display into success message
        productPage.clickOnShoppingCart();
        //2.11 Verify the text "Shopping Cart"
        Assert.assertTrue(shoppingCartPage.isShoppingCartAppearing(), "Shopping Cart Doesn't Appear");
        //2.12 Verify the Product name <productName>
        Assert.assertEquals(shoppingCartPage.getProductName(), "HP LP3065", "Product Name Doesn't appear");
        Assert.assertTrue(shoppingCartPage.isDeliveryDateAppearing("2022-11-30"), "Delivery Date Doesn't Appear");
        //2.13 Verify the Model <model>
        Assert.assertEquals(shoppingCartPage.getModelText(), "Product 21", "Model Name Doesn't appear");
        //2.14 Verify the Total <total>
        Assert.assertEquals(shoppingCartPage.getTotalText(), "£74.73", "Total Doesn't appear");
    }
}