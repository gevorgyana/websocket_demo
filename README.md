TODO: client must be in a different project, it is very stupid
that the war will package the client into the archieve.

For reference, see

[this](https://www.developer.com/java/ent/developing-websocket-clientserver-endpoints.html), guide uses the javax.websocket pure
implementation and uses ServiceLoader to load the implementaiton
of the specification at runtime, and [this](https://www.openshift.com/blog/how-to-build-java-websocket-applications-using-the-jsr-356-api)
guide uses the implementation of the Tyrus project, bypassing the
interface of javax and therefore making the code platform-dependant.

Client
- Run ./gradle build and you get the compiled classes in the build/.
- Use them as a regular java application. For example, run the Client
with ```java Client```.
- Server must be launched before the client for Client to work.

Server
- Run ./gradlew war to prepare the packaged for Tomcat.
- Then grab the WAR from build/libs and move it to
```$CATALINA_HOME/webapps/```
- Launch the Tomcat by running ./startup.sh in the directory where it
is installed
- To test if the deployed WAR works, go to localhost:8080/websocketDemo
- If Tomcat is not installed, download it from the official website
and unpack somewhere.

To troubleshoot the server, see the logs
in the $CATALINA_HOME/logs. Use grep to
search.