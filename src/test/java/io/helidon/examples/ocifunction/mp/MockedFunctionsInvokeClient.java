
package io.helidon.examples.ocifunction.mp;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsInvoke;
import com.oracle.bmc.functions.requests.InvokeFunctionRequest;
import com.oracle.bmc.functions.responses.InvokeFunctionResponse;
import org.apache.commons.io.IOUtils;


public class MockedFunctionsInvokeClient implements FunctionsInvoke {
    public MockedFunctionsInvokeClient(BasicAuthenticationDetailsProvider authenticationDetailsProvider) {}

    @Override
    public void setEndpoint(String endpoint) {}

    @Override
    public void setRegion(Region region) {}

    @Override
    public void setRegion(String s) {}

    @Override
    public InvokeFunctionResponse invokeFunction(InvokeFunctionRequest invokeFunctionRequest) {
        try {
            String payload = IOUtils.toString(invokeFunctionRequest.getBody$());
            return InvokeFunctionResponse.builder()
                    .inputStream(
                            IOUtils.toInputStream(String.format("Hello, %s!", payload.isEmpty() ? "world" : payload)))
                    .build();
        } catch (java.io.IOException ioException) {
            System.out.println("Exception is " + ioException.getMessage());
        }
        return null;
    }

    @Override
    public void close() throws Exception {}
}
