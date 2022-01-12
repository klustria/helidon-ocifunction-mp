
package io.helidon.examples.quickstart.mp;

import com.oracle.bmc.functions.requests.GetFunctionRequest;
import com.oracle.bmc.functions.responses.GetFunctionResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvokeClient;
import com.oracle.bmc.functions.FunctionsManagementClient;


/**
 * Provider for greeting message.
 */
@ApplicationScoped
public class OCIFunctionProvider {
    private FunctionsInvokeClient invokeClient;
    private FunctionsManagementClient managementClient;

    /**
     * Create a new greeting provider, reading the message from configuration.
     *
     * @param message greeting to use
     */
    @Inject
    public OCIFunctionProvider(@ConfigProperty(name = "oci.auth.profile") String ociAuthProfile) {
        try {
            ConfigFileAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(ociAuthProfile);
            invokeClient = FunctionsInvokeClient.builder()
                    // .endpoint(invokeEndpoint)
                    .build(provider);
            managementClient = new FunctionsManagementClient(provider);
        } catch (Exception e) {
            System.out.println("Unable to initialize OCI Function due to: " + e.getMessage());
        }
    }

    FunctionsInvokeClient getInvokeClient() {
        return invokeClient;
    }

    FunctionsManagementClient getManagementClient() {
        return managementClient;
    }
}
