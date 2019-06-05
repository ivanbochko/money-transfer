package com.ivanbochko.moneytransfer.integration

import com.fasterxml.jackson.databind.JsonNode
import io.dropwizard.jackson.Jackson
import org.assertj.core.api.ObjectAssert


private val JSON_MAPPER = Jackson.newObjectMapper()

fun String.toJsonTree(): JsonNode {
    return JSON_MAPPER.readTree(this)
}

fun Any.toJson(): String {
    return JSON_MAPPER.writeValueAsString(this)
}

fun <T> String.fromJson(classOfT: Class<T>): T {
    return JSON_MAPPER.readValue(this, classOfT)
}

infix fun String.shouldBe(json: String) {
    ObjectAssert(this.toJsonTree()).isEqualTo(json.toJsonTree())
}
