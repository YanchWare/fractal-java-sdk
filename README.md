# fractal-java-sdk
Java SDK to create, update and invoke fractals.

## The two IaC layers

The SDK is designed in order to offer several layers of IaC.

### Live System First

The basic and simplest is the approach that allows you to define quickly a live system and deploy it.
In this scenario you can use directly Live Components and build  your Live System composing them.
Livesystem created in this way can be updated but operators will need full access to them.

### Fractal First

The next level is the Fractal level, a reusable multicloud definition for your infrastructure.
You can extract a fractal definition from an existing livesystem, or you can instead adopt the fractal-first approach, where you define your blueprint, your interface and then you utilise them to instantiate and expand your live systems.
This approach gives you the ability to define operations that can be delegated to team members with more restricted access to the infrastructure.
This will secure that your governance is applied at any time without having to review every single change, removing frictions and bottlenecks.

## Language Requirement

The SDK must be easy to use, elegant and should allow the users to define complex architectures in extremely compact amount of code.

## Validation

Validation is a crucial feature of the SDK.

It should help our users have a fantastic developer experience. The SDK should guide the user to easily define Live Systems and Fractals through language constructs already known by the user, as for instance Inheritance, Types, etc.
However, this is not enough sometimes. Validation errors should be accumulated and shown to the users all at once in order to avoid to have to build and run the code multiple times.
This in the future could be delivered as an extension to several IDEs.

Moreover, warnings will be also possible in the future and we should prepare for it now in order to avoid major code restructuring.
An example on how to use warnings is by for instance alerting the user that two services have access to the same database, or that there is more than one entry point to the architecture, or no security components, etc.

## Run time

The code created by the users referring to this SDK needs to run in a CI/CD environment.

This means that tests and design of the SDK needs to be done accordingly (nice output of warnings and errors. Error exit on error in order to block pipeline, secrets through env vars and/or cli inputs, etc).

## Local Development

Publish snapshot versions to local maven repository through gradle, executing the following command:

`./gradlew -Pversion=LOCAL-SNAPSHOT clean publishToMavenLocal`

### SSL Certificate Management Script for Local Development

This script (local-dev/importLocalSslCertToJavaKeystore.sh) is designed to manage SSL certificates for local development environments, specifically addressing the PKIX path building failed error often encountered in Java applications. It automates the process of exporting an SSL certificate from a specified domain and port and importing it into the Java keystore.

### Script Overview  
The script performs the following tasks:

1. Exports the SSL Certificate: Connects to the specified domain and port to retrieve the SSL certificate.
2. Verifies Certificate Export: Ensures that the certificate has been successfully exported.
3. Imports the Certificate into Java Keystore: Adds the exported certificate to the Java keystore, replacing any existing certificate with the same alias.
4. Cleans Up: Deletes the exported certificate file after the import process is complete.

Note  
This script uses the default Java keystore located at `$JAVA_HOME/lib/security/cacerts`. 
If your keystore is located elsewhere, update the pathToJavaKeystore variable accordingly.
Ensure you have the necessary permissions to modify the Java keystore, as this operation typically requires sudo access.