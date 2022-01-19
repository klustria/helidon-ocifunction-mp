
package io.helidon.examples.ocifunction.mp;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.client.WebTarget;

import io.helidon.microprofile.tests.junit5.HelidonTest;


import org.junit.jupiter.api.*;

@HelidonTest
class GreetTest {
    @Inject
    WebTarget webTarget;

    @Test
    void testDefaultGreeting() {
        validate(webTarget, "/greet", "Hello, world!");
    }

    @Test
    void testCustomGreeting() {
        validate(webTarget, "greet/Joe", "Hello, Joe!");
    }

    private void validate(WebTarget webTarget,
                          String path,
                          String expected) {
        JsonObject jsonObject = webTarget.path(path)
                .request()
                .get(JsonObject.class);
        String actual = jsonObject.getString("oracle-function-message");
        Assertions.assertEquals(expected, actual, "Message in JSON");
    }
}
