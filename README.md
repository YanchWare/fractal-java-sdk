# fractal-java-sdk
Java SDK to create, update and invoke fractals

The SDK is designed in order to offer several layers of IaC.

The basic and simplest is the approach that allows you to define quickly a live system and deploy it.
In this scenario you can use directly Live Components and build  your Live System composing them.
Livesystem created in this way can be updated but operators will need full access to them.

The next level is the Fractal level, a reusable multicloud definition for your infrastructure.
You can extract a fractal definition from an existing livesystem, or you can instead adopt the fractal-first approach, where you define your blueprint, your interface and then you utilise them to instantiate and expand live systems.
This approach gives you the ability to define operations that can be delegated to team members with more restricted access to the infrastructure.
This will secure that your governance is applied at any time without having to review every single change, removing frictions and bottlenecks. 