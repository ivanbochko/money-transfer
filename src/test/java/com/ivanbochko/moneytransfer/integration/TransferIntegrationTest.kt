package com.ivanbochko.moneytransfer.integration

import com.ivanbochko.moneytransfer.common.model.Currency
import com.ivanbochko.moneytransfer.integration.AccountFunctions.createAccount
import com.ivanbochko.moneytransfer.integration.AccountFunctions.getAllAccounts
import com.ivanbochko.moneytransfer.integration.TransferFunctions.getAllTransfers
import com.ivanbochko.moneytransfer.integration.TransferFunctions.makeTransfer
import com.ivanbochko.moneytransfer.resources.transfer.Recipient
import com.ivanbochko.moneytransfer.resources.transfer.Sender
import io.restassured.RestAssured
import io.restassured.parsing.Parser
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransferIntegrationTest {
    @get:Rule
    var appRule = AppRule()

    @Before
    fun setUp() {
        RestAssured.baseURI = appRule.localAddress
        RestAssured.defaultParser = Parser.JSON
    }

    @Test
    fun shouldMakeIntraTransferFromSystemToCustomer() {
        createAccount("John", "Savings", Currency.GBP) {
            statusCode(200)
        }

        makeTransfer(1000.1,
                Sender("Barklays System", "Barklays Operator Account", Currency.GBP),
                Recipient("Barklays", "John", "Savings", Currency.GBP)) {
            statusCode(200)
        }

        getAllAccounts().toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": -1000.1
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "GBP",
                "balance": 1000.1
            }
        ]
        """
    }

    @Test
    fun shouldMakeIntraTransferFromCustomerToCustomer() {
        createAccount("John", "Savings", Currency.GBP) {
            statusCode(200)
        }
        createAccount("Alice", "Savings", Currency.GBP) {
            statusCode(200)
        }

        makeTransfer(1000.1,
                Sender("Barklays System", "Barklays Operator Account", Currency.GBP),
                Recipient("Barklays", "John", "Savings", Currency.GBP)) {
            statusCode(200)
        }
        makeTransfer(300.0,
                Sender("John", "Savings", Currency.GBP),
                Recipient("Barklays", "Alice", "Savings", Currency.GBP)) {
            statusCode(200)
        }

        getAllAccounts().toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": -1000.1
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "GBP",
                "balance": 700.1
            },
            {
                "bank": "Barklays",
                "customer": "Alice",
                "account": "Savings",
                "currency": "GBP",
                "balance": 300.0
            }
        ]
        """
    }

    @Test
    fun shouldMakeIntraTransferFromCustomerToCustomerWithCurrencyConversion() {
        createAccount("John", "Savings", Currency.GBP) {
            statusCode(200)
        }
        createAccount("Alice", "Savings", Currency.EUR) {
            statusCode(200)
        }

        makeTransfer(1000.1,
                Sender("Barklays System", "Barklays Operator Account", Currency.GBP),
                Recipient("Barklays", "John", "Savings", Currency.GBP)) {
            statusCode(200)
        }
        makeTransfer(1000.0,
                Sender("John", "Savings", Currency.GBP),
                Recipient("Barklays", "Alice", "Savings", Currency.EUR)) {
            statusCode(200)
        }

        getAllAccounts().toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": -1000.1
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "GBP",
                "balance": 0.1
            },
            {
                "bank": "Barklays",
                "customer": "Alice",
                "account": "Savings",
                "currency": "EUR",
                "balance": 1131.15074
            }
        ]
        """

        getAllTransfers().toJson() shouldBe """
            [
                {
                    "senderCustomer": "Barklays System",
                    "senderAccount": "Barklays Operator Account",
                    "senderCurrency": "GBP",
                    "recipientBank": "Barklays",
                    "recipientCustomer": "John",
                    "recipientAccount": "Savings",
                    "recipientCurrency": "GBP",
                    "amountInSenderCurrency": 1000.1,
                    "amountInRecipientCurrency": 1000.1,
                    "issuedUtc": "2019-06-04T10:15:30.000"
                },
                {
                    "senderCustomer": "John",
                    "senderAccount": "Savings",
                    "senderCurrency": "GBP",
                    "recipientBank": "Barklays",
                    "recipientCustomer": "Alice",
                    "recipientAccount": "Savings",
                    "recipientCurrency": "EUR",
                    "amountInSenderCurrency": 1000.0,
                    "amountInRecipientCurrency": 1131.15074,
                    "issuedUtc": "2019-06-04T10:15:30.000"
                }
            ]
        """
    }

    @Test
    fun shouldMakeInterbankTransferFromCustomerToCustomer() {
        createAccount("John", "Savings", Currency.GBP) {
            statusCode(200)
        }

        makeTransfer(1000.1,
                Sender("Barklays System", "Barklays Operator Account", Currency.GBP),
                Recipient("Barklays", "John", "Savings", Currency.GBP)) {
            statusCode(200)
        }
        makeTransfer(800.0,
                Sender("John", "Savings", Currency.GBP),
                Recipient("Lloyds", "Alice", "Lloyds Savings", Currency.GBP)) {
            statusCode(200)
        }

        getAllAccounts().toJson() shouldBe """
        [
            {
                "bank": "Barklays",
                "customer": "Barklays System",
                "account": "Barklays Operator Account",
                "currency": "GBP",
                "balance": -1000.1
            },
            {
                "bank": "Barklays",
                "customer": "John",
                "account": "Savings",
                "currency": "GBP",
                "balance": 200.1
            }
        ]
        """
    }
}
