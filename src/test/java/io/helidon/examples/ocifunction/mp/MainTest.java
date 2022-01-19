
package io.helidon.examples.ocifunction.mp;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.spi.CDI;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import io.helidon.microprofile.server.Server;

import org.junit.jupiter.api.*;

class MainTest {

    private static Server server;
    private static String serverUrl;

    @BeforeAll
    public static void startTheServer() throws Exception {
        server = Server.create().start();
        serverUrl = "http://localhost:" + server.port();
    }

    @Test
    void testHelloWorld() {
        Client client = ClientBuilder.newClient();

        JsonObject jsonObject = client
                .target(serverUrl)
                .path("greet")
                .request()
                .get(JsonObject.class);
        Assertions.assertEquals("Hello, world!", jsonObject.getString("oracle-function-message"),
                "default message");

        jsonObject = client
                .target(serverUrl)
                .path("greet/Joe")
                .request()
                .get(JsonObject.class);
        Assertions.assertEquals("Hello, Joe!", jsonObject.getString("oracle-function-message"),
                "hello Joe message");
    }

    @AfterAll
    static void destroyClass() {
        CDI<Object> current = CDI.current();
        ((SeContainer) current).close();
    }
}
