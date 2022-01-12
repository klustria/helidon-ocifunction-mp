
package io.helidon.examples.quickstart.mp;

import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.oracle.bmc.functions.FunctionsManagementClient;
import com.oracle.bmc.functions.requests.GetFunctionRequest;
import com.oracle.bmc.functions.responses.GetFunctionResponse;
import com.oracle.bmc.util.StreamUtils;
import org.apache.commons.io.IOUtils;

import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;


/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message:
 * curl -X GET http://localhost:8080/greet
 *
 * Get greeting message for Joe:
 * curl -X GET http://localhost:8080/greet/Joe
 *
 * Change greeting
 * curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Howdy"}' http://localhost:8080/greet/greeting
 *
 * The message is returned as a JSON object.
 */
@Path("/greet")
@RequestScoped
public class GreetResource {

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    /**
     * The greeting message provider.
     */
    private String functionId;
    private FunctionsInvokeClient invokeClient;
    private FunctionsManagementClient managementClient;

    /**
     * Using constructor injection to get a configuration property.
     * By default this gets the value from META-INF/microprofile-config
     *
     */
    @Inject
    public GreetResource(
            OCIFunctionProvider ociFunctionProvider,
            @ConfigProperty(name = "oci.function.id") String functionID
    ) {
        this.invokeClient = ociFunctionProvider.getInvokeClient();
        this.managementClient = ociFunctionProvider.getManagementClient();
        this.functionId = functionID;
    }

    /**
     * Return a worldly greeting message.
     *
     * @return {@link JsonObject}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getDefaultMessage() {
        return createOCIFunctionResponse("");
    }

    /**
     * Return a greeting message using the name that was provided.
     *
     * @param name the name to greet
     * @return {@link JsonObject}
     */
    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getMessage(@PathParam("name") String name) {
        return createOCIFunctionResponse(name);
    }

    private JsonObject createOCIFunctionResponse(String payload) {
        try {
            // Set function endpoint based on provided functionId
            GetFunctionRequest functionRequest = GetFunctionRequest.builder()
                    .functionId(functionId)
                    .build();
            GetFunctionResponse getFunctionResponse = this.managementClient.getFunction(functionRequest);
            invokeClient.setEndpoint(getFunctionResponse.getFunction().getInvokeEndpoint());

            InvokeFunctionRequest request = InvokeFunctionRequest.builder()
                    .functionId(functionId)
                    .invokeFunctionBody(
                            StreamUtils.createByteArrayInputStream(payload.getBytes())
                    )
                    .build();
            InvokeFunctionResponse invokeFunctionResponse = this.invokeClient.invokeFunction(request);
            String msg = IOUtils.toString(invokeFunctionResponse.getInputStream());

            return JSON.createObjectBuilder()
                    .add("message", msg)
                    .build();
        } catch(Exception e) {
            return JSON.createObjectBuilder()
                    .add("message", e.getMessage())
                    .build();
        }
    }
}
