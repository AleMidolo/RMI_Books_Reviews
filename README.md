# Automatically Injecting Robustness Statements into Distributed
Applications

## Abstract
When developing a distributed application, several issues need to be handled and software components should include some mechanisms to make their execution resilient when network faults, delays, or tampering occur. 
E.g. synchronous calls represent a too tight connection between a client requesting a service and the service itself, whereby potential network delays or temporary server overloads would keep the client-side hanging, exposing it to a domino effect. 
The proposed approach assists developers in dealing with such issues by providing an automatic tool that enhances a distributed application using simple blocking calls and makes it robust in the face of adverse events.
The proposed devised solution consists in automatically identifying the parts of the application that connect to remote services using simple synchronous calls and substituting them with a generated customised snippet of code that handles potential network delays or faults. To accurately perform the proposed transformation, the devised tool finds application code statements that are data-dependent on the results of the original synchronous calls. 
Then, for the dependent statements, a solution including guarding code, proper synchronisation, and timeouts is injected. We experimented with the analysis and transformation of several applications and report a meaningful example, together with the analysis of the achieved results.
