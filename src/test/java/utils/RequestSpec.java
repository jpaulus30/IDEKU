package utils;

import config.BaseConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import config.BaseConfig;

public class RequestSpec {

    public static RequestSpecification spec() {
        return new RequestSpecBuilder()
                .setBaseUri(BaseConfig.baseURL())
                .addHeader("Authorization", "Bearer " + TokenManager.getToken())
                .setContentType(ContentType.JSON)
                .build();
    }
}