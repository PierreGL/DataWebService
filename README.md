# DataWebService
This project is a data web service type key/value. 
It allow to deploy the service on a server and store and manipulate datas remotely by standard way.

# Structure of project
The structure of project is defined by maven. It is a modular project composed by a parent project (DataWebService) and several modules. 
Note that the sub-modules can easily to be reused for other projects.
The project is coded in java, it needs a JVM 8 and servlet container JEE7 (like tomcat, jetty,...).
It include lib Jersey (JAX-RS implementation), lib JUnit to perform test campaigns, customized binary tree.

# Build the project
To build the project :
1/ install maven 3 on your engine.
2/ Go to root of parent project (DataWebService/) in the same directory of pom.xml.
3/ Enter in a shell "mvn install"
=> The project is built (including run unit test). The artefact to run the project is "web-service.war". 
It is located in DataWebService/web-services/

# Run the project
Tu run the project deploy the artefact on servlet container. Start the server.
Tu use the web service, use web service client type REST with url like http://<host>:<port>/web-service/dataservice/<resource>

# Features to finish
-Configuration and security policy

-Back up planning

-...
