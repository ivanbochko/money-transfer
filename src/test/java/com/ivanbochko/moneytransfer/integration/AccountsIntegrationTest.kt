package com.ivanbochko.moneytransfer.integration

import com.ivanbochko.moneytransfer.common.model.Currency
import com.ivanbochko.moneytransfer.integration.AccountFunctions.getAllAccounts
import io.restassured.RestAssured
import io.restassured.parsing.Parser
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccountsIntegrationTest {
    @get:Rule
    var appRule = AppRule()

    @Before
    fun setUp() {
        RestAssured.baseURI = appRule.localAddress
        RestAssured.defaultParser = Parser.JSON
    }

    @Test
    fun shouldInitiallyListAllAccountsIncludingSystemOne() {
        val accounts = getAllAccounts()

        assertThat(accounts).hasSize(1)

        accounts.toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": 0.0
            }
        ]
        """
    }

    @Test
    fun shouldCreateAndListAllCreatedAccounts() {
        AccountFunctions.createAccount("John", "Savings", Currency.USD) {
            statusCode(200)
        }
        val accounts = AccountFunctions.getAllAccounts()

        assertThat(accounts).hasSize(2)

        accounts.toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": 0.0
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "USD",
                "balance": 0.0
            }
        ]
        """
    }

    @Test
    fun shouldFailCreatingInvalidAccount() {
        AccountFunctions.createAccount("Alice", "", Currency.USD) {
            statusCode(422)
            extract().body().asString() shouldBe """
                {
                "errors": [
                    "account may not be empty"
                ]
            }
            """
        }

        val accounts = AccountFunctions.getAllAccounts()
        assertThat(accounts).hasSize(1)
    }

    @Test
    fun shouldNotCreateDuplicateAccounts() {
        AccountFunctions.createAccount("John", "Savings", Currency.USD) {
            statusCode(200)
        }
        AccountFunctions.createAccount("John", "Savings", Currency.USD) {
            statusCode(200)
        }
        val accounts = AccountFunctions.getAllAccounts()

        assertThat(accounts).hasSize(2)

        accounts.toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": 0.0
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "USD",
                "balance": 0.0
            }
        ]
        """
    }
}
