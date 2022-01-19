
package io.helidon.examples.ocifunction.mp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvoke;
import com.oracle.bmc.functions.FunctionsManagement;
import org.eclipse.microprofile.config.inject.ConfigProperty;


/**
 * Mocked Test Provider for Oracle function greeting message.
 */
@ApplicationScoped
public class OCIFunctionProvider {
    private FunctionsInvoke invokeClient;
    private FunctionsManagement managementClient;

    /**
     * Instantiates a new Oracle Function provider, that creates management and invoke clients using ~/.oci
     * configuration properties via the set oci.auth.profile configuration property or use "DEFAULT" if not provided.
     */
    @Inject
    public OCIFunctionProvider(@ConfigProperty(name = "oci.auth.profile", defaultValue = "DEFAULT") String ociAuthProfile) {
        try {
            System.out.println("From test directory");
            ConfigFileAuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(ociAuthProfile);
            invokeClient = new MockedFunctionsInvokeClient(provider);
            managementClient = new MockedFunctionsManagementClient(provider);
        } catch (Exception e) {
            System.out.println("Unable to initialize OCI Function due to: " + e.getMessage());
        }
    }

    FunctionsInvoke getInvokeClient() {
        System.out.println("Test OCIFunctionProvider");
        return invokeClient;
    }

    FunctionsManagement getManagementClient() {
        return managementClient;
    }
}
