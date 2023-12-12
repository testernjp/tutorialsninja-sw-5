package com.tutorialsninja.demo.testsuite;

import com.tutorialsninja.demo.customlisteners.CustomListeners;
import com.tutorialsninja.demo.pages.*;
import com.tutorialsninja.demo.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(CustomListeners.class)
public class LaptopsAndNotebooksTest extends BaseTest {

    HomePage homePage;
    LaptopAndNoteBookPage laptopAndNoteBookPage;
    MacBookPage macBookPage;
    ShoppingCartPage shoppingCartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod(alwaysRun = true)
    public void inIt() {
        homePage = new HomePage();
        laptopAndNoteBookPage = new LaptopAndNoteBookPage();
        macBookPage = new MacBookPage();
        shoppingCartPage = new ShoppingCartPage();
        checkoutPage = new CheckoutPage();
    }

    @Test(groups = {"sanity", "regression"})
    public void verifyProductsPriceDisplayHighToLowSuccessfully() {
        //1.1 Mouse hover on Laptops & Notebooks Tab.and click
        homePage.mouseHoverAndClickOnLaptopsAndNotebooks();
        //1.2 Click on “Show All Laptops & Notebooks”
        homePage.selectMenu("Laptops & Notebooks");
        //1.3 Select Sort By "Price (High > Low)"
        laptopAndNoteBookPage.selectPriceHighToLow("Price (High > Low)");
        //1.4 Verify the Product price will arrange in High to Low order.
        Assert.assertEquals(laptopAndNoteBookPage.afterSorting(), laptopAndNoteBookPage.beforeSorting(), "products not sorted by Price High to Low");
    }

    @Test(groups = {"smoke", "regression"})
    public void verifyThatUserPlaceOrderSuccessfully() throws InterruptedException {
        homePage.selectMenu("Laptops & Notebooks");
        laptopAndNoteBookPage.selectPriceHighToLow("Price (High > Low)");
        laptopAndNoteBookPage.clickOnMacbook();
        Assert.assertEquals(macBookPage.getTextFromMacBook(), "MacBook", "MacBook Product not display");
        macBookPage.clickOnAddToCart();
        Assert.assertTrue(macBookPage.isSuccessMessageAppearing(), "Message Doesn't Appear");
        macBookPage.clickOnShoppingCart();
        Assert.assertTrue(shoppingCartPage.isShoppingCartAppearing(), "Shopping Cart Doesn't Appear");
        Assert.assertEquals(shoppingCartPage.getProductName(), "MacBook", "Product Name Doesn't appear");
        shoppingCartPage.clearAndAddQuantity("2");
        shoppingCartPage.clickOnUpdate();
        Assert.assertTrue(shoppingCartPage.isSuccessMessageAppearing("Success: You have modified your shopping cart!"), "Cart not modified");
        Assert.assertEquals(shoppingCartPage.getTotalText(), "$1,204.00", "Total not matched");
        shoppingCartPage.clickOnCheckout();
        Assert.assertEquals(checkoutPage.getCheckoutText(), "Checkout", "Checkout not displayed");
        Assert.assertEquals(checkoutPage.getNewCustomerText(), "New Customer", "New Customer not displayed");
        checkoutPage.clickOnGuestCheckoutRadioButton();
        checkoutPage.clickOnContinueButton();
        checkoutPage.enterBillingDetailsFirstName("Hi");
        checkoutPage.enterBillingDetailsLastName("Patel");
        checkoutPage.enterBillingDetailsEmail("Patel" + getRandomAlphaNumericString(4) + "@gmail.com");
        checkoutPage.enterBillingDetailsTelephone("07654321234");
        checkoutPage.enterBillingDetailsAddress("11 Pinner Road");
        checkoutPage.enterBillingDetailsCity("Harrow");
        checkoutPage.enterBillingDetailsPostcode("HA61SY");
        checkoutPage.enterBillingDetailsCountry("United Kingdom");
        checkoutPage.enterBillingDetailsRegionOrState("Aberdeen");
        checkoutPage.clickOnContinueBillingButton();
        checkoutPage.enterComment("Nothing Specific.");
        checkoutPage.clickOnAgreeToTermsAndConditions();
        checkoutPage.clickOnContinueCommentButton();
        Assert.assertTrue(checkoutPage.isPaymentWarningAppearing(), "Payment Warning not displayed");
    }
}
