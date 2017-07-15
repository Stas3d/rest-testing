package com.epam.company.stepdef;

import com.epam.company.ConfigurationConstants;
import com.epam.company.utils.ConfigurationService;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.commons.lang3.StringUtils;

import org.apache.commons.configuration.Configuration;
import org.json.JSONObject;

import java.util.Map;

import static com.jayway.restassured.RestAssured.basic;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public class CardApiStepDefinitions {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    private static final String hostUrl;
    private static final String testUser;
    private static final String testPassword;

    private static final String _CARDS = "/cards";
    private static Configuration configuration = ConfigurationService.loadConfiguration();

    static {
        hostUrl = configuration.getString(ConfigurationConstants.DEFAULT_HOST);
        testUser = configuration.getString(ConfigurationConstants.DEFAULT_USER);
        testPassword = configuration.getString(ConfigurationConstants.DEFAULT_PASSWORD);
    }

    @Given("User send request to get card with name (.*)")
    public void send_get_request_to_get_card(final String arg1) {
        final String requestUrl = hostUrl + _CARDS + "/" + arg1;
        response = given()
                .get(requestUrl);
    }

    @Given("I verify the parameter from the response:$")
    public void verify_card_parameter(final Map map) {
        json = response
                .then()
                .body("", equalTo(""));
    }

    @Given("User send request to receive all cards in system")
    public void send_get_request() {
        final String url = hostUrl + _CARDS;
        response = given()
                .get(url);
    }

    @Then("User verify that response has (.*) cards items inside")
    public void response_has_items(final int size) {
        json = response
                .then()
                .body("card.size()", equalTo(size));
    }

    @Given("User send request to create new card with following data:$")
    public void send_create_card_post_request(final Map map) {
        JSONObject json = new JSONObject(map);
        final String requestUrl = hostUrl + _CARDS;
        response = given()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(json)
                .post(requestUrl)
                .thenReturn();
    }

    @Given("I send post request for user with name (.*)")
    public void send_post_request(final String arg1) {
        final String requestUrl = hostUrl + arg1;
        response = given()
                .get(requestUrl);
    }

    @Given("I send the renaming request for user (.*) to provide him new name (.*)")
    public void send_rename_request(final String arg1, final String arg2) {
    }

    @Then("The status code is (\\d+)")
    public void verify_status_code(int statusCode) {
        json = response
                .then()
                .statusCode(statusCode);
    }

    @Given("User provides authentication")
    public void verify_status_code() {
        RestAssured.authentication =
                basic(testUser, testPassword);
    }

    @And("response includes the following$")
    public void response_equals(Map<String, String> responseFields) {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
            } else {
                json.body(field.getKey(), equalTo(field.getValue()));
            }
        }
    }

    @And("response includes the following in any order")
    public void response_contains_in_any_order(Map<String, String> responseFields) {
        for (Map.Entry<String, String> field : responseFields.entrySet()) {
            if (StringUtils.isNumeric(field.getValue())) {
                json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
            } else {
                json.body(field.getKey(), containsInAnyOrder(field.getValue()));
            }
        }
    }

    @And("response includes following error reason '(.*)'")
    public void response_contains_following(final String value) {
        json = response
                .then()
                .body("error", equalTo(value));
    }
}
