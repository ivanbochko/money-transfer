package com.ivanbochko.moneytransfer.integration;

import com.ivanbochko.moneytransfer.resources.account.BankAccountView;
import io.restassured.RestAssured;
import io.restassured.mapper.TypeRef;
import io.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ListAllAccountsTest {
    @Rule
    public AppRule appRule = new AppRule();

    @Before
    public void setUp() {
        RestAssured.baseURI = appRule.getLocalAddress();
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void shouldListAllAccountsIncludingSystemOne() {
        List<BankAccountView> accounts = RestAssured.given()
                .contentType("application/json")
                .when()
                .get("/accounts")
                .as(new TypeRef<>() {
                });

        assertThat(accounts).hasSize(1);




    }
}
