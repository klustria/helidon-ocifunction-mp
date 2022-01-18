# Helidon MP example that invokes Oracle Function
Sample Helidon MP project that includes Oracle Function integration using OCI java sdk.

## Configure environment

Create/deploy a sample hello world Oracle Function by following steps provided in the [Functions QuickStart Guides](https://docs.oracle.com/en-us/iaas/Content/Functions/Tasks/functionsquickstartguidestop.htm#functionsquickstartguidestop).

## Configure microprofile config

Configure oci function related paramters in src/main/resources/META-INF/microprofile-config.properties:
* **oci.auth.profile** - Profile name of the OCI authentication parameters in ~/.oci/config that will be used to access the Oracle Function resource
* **oci.function.id** - OCID of the Oracle function deployed from the previous step. This information can be retrieved from OCI console by navigating to the deployed function.

## Build and run

With JDK11+
```bash
mvn package
java -jar target/helidon-ocifunction-mp.jar
```

## Exercise the application
* Use default name (World)
   ```
   curl -X GET http://localhost:8080/greet
   {"message":"Hello World!"}
   ```
* Provide custom name
   ```
   curl -X GET http://localhost:8080/greet/Joe
   {"message":"Hello Joe!"}
   ```