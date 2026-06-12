package com.example.steps;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductSteps {

    private ProductRepository productRepository;
    private ProductService productService;

    private List<Product> results;

    @Before
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        results = null;
    }

    @Given("the catalogue returns the product {string} for the keyword {string}")
    public void catalogueReturnsForKeyword(String name, String keyword) {
        when(productRepository.searchByKeyword(keyword))
                .thenReturn(List.of(new Product(name, "any", 0.0)));
    }

    @Given("the catalogue returns the product {string} for a maximum price of {double}")
    public void catalogueReturnsForMaxPrice(String name, double maxPrice) {
        when(productRepository.findByMaxPrice(maxPrice))
                .thenReturn(List.of(new Product(name, "any", maxPrice)));
    }

    @Given("the category {string} contains the product {string}")
    public void categoryContains(String category, String name) {
        when(productRepository.findByCategory(category))
                .thenReturn(List.of(new Product(name, category, 0.0)));
    }

    @When("the user searches for the keyword {string}")
    public void searchesForKeyword(String keyword) {
        results = productService.searchByKeyword(keyword);
    }

    @When("the user searches with a maximum price of {double}")
    public void searchesByMaxPrice(double maxPrice) {
        results = productService.searchByMaxPrice(maxPrice);
    }

    @When("the user selects the category {string}")
    public void selectsCategory(String category) {
        results = productService.browseCategory(category);
    }

    @Then("the results contain the product {string}")
    public void resultsContainTheProduct(String name) {
        assertNotNull(results, "Results should not be null");
        assertNotNull(findByName(name), "Results should contain " + name);
    }

    @Then("the matching product {string} costs at most {double}")
    public void theMatchingProductCostsAtMost(String name, double maxPrice) {
        Product product = findByName(name);
        assertNotNull(product, "Results should contain " + name);
        assertTrue(product.getPrice() <= maxPrice, name + " should cost at most " + maxPrice);
    }

    @Then("the matching product {string} belongs to the category {string}")
    public void theMatchingProductBelongsToCategory(String name, String category) {
        Product product = findByName(name);
        assertNotNull(product, "Results should contain " + name);
        assertEquals(category, product.getCategory());
    }

    private Product findByName(String name) {
        return results.stream()
                .filter(product -> product.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
