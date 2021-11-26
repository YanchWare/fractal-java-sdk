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


## Documentation template

Intro + Drawing
Arguments (required & optional - specify defaults) - java code example with comments to the fields + detailed
Examples (maybe with tabs for each provider)
