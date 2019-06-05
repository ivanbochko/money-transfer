package com.ivanbochko.moneytransfer.integration

import com.ivanbochko.moneytransfer.resources.transfer.MakeTransferRequest
import com.ivanbochko.moneytransfer.resources.transfer.Recipient
import com.ivanbochko.moneytransfer.resources.transfer.Sender
import com.ivanbochko.moneytransfer.resources.transfer.TransferView
import io.restassured.RestAssured
import io.restassured.response.ValidatableResponse

object TransferFunctions {

    fun getAllTransfers(): Array<TransferView> {
        return RestAssured.given()
                .contentType("application/json")
                .`when`()
                .get("/transfers")
                .`as`(Array<TransferView>::class.java)
    }

    fun makeTransfer(amount: Double, sender: Sender, recipient: Recipient,
                     assertBlock: ValidatableResponse.() -> Unit = {}) {
        val then = RestAssured.given()
                .contentType("application/json")
                .`when`()
                .body(MakeTransferRequest(sender, recipient, amount))
                .post("/transfers")
                .then()
        then.apply(assertBlock)
    }
}