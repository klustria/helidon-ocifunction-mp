
package io.helidon.examples.ocifunction.mp;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.BasicAuthenticationDetailsProvider;
import com.oracle.bmc.functions.FunctionsManagement;
import com.oracle.bmc.functions.FunctionsManagementPaginators;
import com.oracle.bmc.functions.FunctionsManagementWaiters;
import com.oracle.bmc.functions.model.Function;
import com.oracle.bmc.functions.requests.*;
import com.oracle.bmc.functions.responses.*;


public class MockedFunctionsManagementClient implements FunctionsManagement {
    public MockedFunctionsManagementClient(BasicAuthenticationDetailsProvider authenticationDetailsProvider) {}

    @Override
    public void setEndpoint(String s) {}

    @Override
    public void setRegion(Region region) {}

    @Override
    public void setRegion(String s) {}

    @Override
    public ChangeApplicationCompartmentResponse changeApplicationCompartment(ChangeApplicationCompartmentRequest changeApplicationCompartmentRequest) { return null; }

    @Override
    public CreateApplicationResponse createApplication(CreateApplicationRequest createApplicationRequest) { return null; }

    @Override
    public CreateFunctionResponse createFunction(CreateFunctionRequest createFunctionRequest) {
        return null;
    }

    @Override
    public DeleteApplicationResponse deleteApplication(DeleteApplicationRequest deleteApplicationRequest) { return null; }

    @Override
    public DeleteFunctionResponse deleteFunction(DeleteFunctionRequest deleteFunctionRequest) {
        return null;
    }

    @Override
    public GetApplicationResponse getApplication(GetApplicationRequest getApplicationRequest) {
        return null;
    }

    @Override
    public GetFunctionResponse getFunction(GetFunctionRequest getFunctionRequest) {
        return GetFunctionResponse.builder()
                .etag("DummyEtag")
                .opcRequestId("DummyRequestId")
                .function(
                        Function.builder()
                                .invokeEndpoint("http://dummy.function.endpoint")
                                .build()
                )
                .build();
    }

    @Override
    public ListApplicationsResponse listApplications(ListApplicationsRequest listApplicationsRequest) {
        return null;
    }

    @Override
    public ListFunctionsResponse listFunctions(ListFunctionsRequest listFunctionsRequest) {
        return null;
    }

    @Override
    public UpdateApplicationResponse updateApplication(UpdateApplicationRequest updateApplicationRequest) { return null; }

    @Override
    public UpdateFunctionResponse updateFunction(UpdateFunctionRequest updateFunctionRequest) {
        return null;
    }

    @Override
    public FunctionsManagementWaiters getWaiters() {
        return null;
    }

    @Override
    public FunctionsManagementPaginators getPaginators() {
        return null;
    }

    @Override
    public void close() throws Exception {}
}
