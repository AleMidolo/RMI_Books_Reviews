# Automatically Injecting Robustness Statements into Distributed Applications
## Abstract
When developing a distributed application, several issues need to be handled and software components should include some mechanisms to make their execution resilient when network faults, delays, or tampering occur. 
E.g. synchronous calls represent a too tight connection between a client requesting a service and the service itself, whereby potential network delays or temporary server overloads would keep the client-side hanging, exposing it to a domino effect. 
The proposed approach assists developers in dealing with such issues by providing an automatic tool that enhances a distributed application using simple blocking calls and makes it robust in the face of adverse events.
The proposed devised solution consists in automatically identifying the parts of the application that connect to remote services using simple synchronous calls and substituting them with a generated customised snippet of code that handles potential network delays or faults. To accurately perform the proposed transformation, the devised tool finds application code statements that are data-dependent on the results of the original synchronous calls. 
Then, for the dependent statements, a solution including guarding code, proper synchronisation, and timeouts is injected. We experimented with the analysis and transformation of several applications and report a meaningful example, together with the analysis of the achieved results.

## Proposed Approach Overview
The following figure illustrates the three main phases of the proposed approach: (i) remote calls identification, which parses the Java code to locate all instances of remote calls (including e.g.\ RMIs); (ii) data dependency analysis and code transformation, which replaces remote calls with new fragments of code that enhance the application's functionalities; (iii) project document update, which involves verifying and updating the application dependencies.

![Overview of the three main phases of the proposed approach: in the first phase, the source code is parsed to locate remote calls. Next, the identified calls are analyzed to ensure data consistency, and the code is transformed to support parallel execution and fault tolerance. The final phase involves updating the application's dependencies.](https://github.com/user-attachments/assets/d06602c0-88e3-4686-be29-ebe5f5e345ea)

## Server - Client Architecture
Following figures show the UML class diagrams outlining the application's components.
The DataService remote interface defines five methods: getAuthors(), getMostReviewedBook(), getLeastReviewedBook(), getAverageReviewedBook(), and getAverageReviewedBook(). The Server class creates and configures an instance of Service, which implements the DataService interface and uses the methods of the ExtractDataset class to retrieve information from the books dataset.

![The UML class diagram of the server side architecture.](https://github.com/user-attachments/assets/7e5d2c76-48cb-4daf-bfd6-eeef8660ac47)

On the client side, the ServiceProxy class implements DataService and manages network communication, performing RMIs on the methods exposed by Service. The Client class creates a ServiceProxy object to connect to the service and receive the results of remote method executions.

![The UML class diagram of the client side architecture.](https://github.com/user-attachments/assets/46b3da95-a5b3-4663-9f67-2c90fc84ec8d)

## Citation
Marletta, D.; Midolo, A.; Tramontana, E. Automatically Injecting Robustness Statements into Distributed Applications. Future Internet 2024, 16, 416. https://doi.org/10.3390/fi16110416
```
@Article{fi16110416,
	AUTHOR = {Marletta, Daniele and Midolo, Alessandro and Tramontana, Emiliano},
	TITLE = {Automatically Injecting Robustness Statements into Distributed Applications},
	JOURNAL = {Future Internet},
	VOLUME = {16},
	YEAR = {2024},
	NUMBER = {11},
	ARTICLE-NUMBER = {416},
	URL = {https://www.mdpi.com/1999-5903/16/11/416},
	ISSN = {1999-5903},
	DOI = {10.3390/fi16110416}
}
```
