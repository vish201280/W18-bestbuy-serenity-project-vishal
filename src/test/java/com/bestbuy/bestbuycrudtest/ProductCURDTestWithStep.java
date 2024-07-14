package com.bestbuy.bestbuycrudtest;

import com.bestbuy.bestbuyinfo.ProductSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ProductCURDTestWithStep extends TestBase {
    static String name = "Battery Portable" + TestUtils.getRandomValue();
    static String type = "Chargable" + TestUtils.getRandomValue();
    static double price = 5.50;
    static String upc = "123456789";
    static int shipping = 5;
    static String description = "Long Life Guarantee";
    static String manufacturer = "Duracell";
    static String model = "AK 47";
    static String url = "http://www.bestbuy.com";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";

    static int productID;
    @Steps
    ProductSteps steps;

    @Title("This will create a new Product")
    @Test
    public void test001() {
        ValidatableResponse response = steps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(201);
        productID = response.log().all().extract().path("id");

    }

    @Title("Verify if the product was added to application")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = steps.getProductInfoByProductName(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Update the product information and verify the updated information")
    @Test
    public void test003() {
        ValidatableResponse response = steps.updateProduct(productID, name, type, price, upc, shipping, description, manufacturer, model, url, image).statusCode(200);
        productID = response.log().all().extract().path("id");

        HashMap<String, Object> productMap = steps.getProductInfoByProductName(productID);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("Delete the product and verify if the product has been deleted")
    @Test
    public void test004() {
        steps.deleteProduct(productID).statusCode(200);
        steps.getProductById(productID).statusCode(404);
    }

}


