package com.ivanbochko.moneytransfer.integration

import com.ivanbochko.moneytransfer.common.model.Currency
import com.ivanbochko.moneytransfer.resources.account.BankAccountView
import com.ivanbochko.moneytransfer.resources.account.CreateBankAccountRequest
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse

object AccountFunctions {

    fun getAllAccounts(): Array<BankAccountView> {
        return RestAssured.given()
                .contentType("application/json")
                .`when`()
                .get("/accounts")
                .`as`(Array<BankAccountView>::class.java)
    }

    fun createAccount(customer: String, account: String, currency: Currency,
                      assertBlock: ValidatableResponse.() -> Unit = {}) {
        val then = RestAssured.given()
                .contentType("application/json")
                .`when`()
                .body(CreateBankAccountRequest(customer, account, currency))
                .post("/accounts")
                .then()
        then.apply(assertBlock)
    }
}