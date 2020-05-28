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

Regarding the JMonkey backend:
- it uses single thread to operate (check the log outputs of current
thread id's from the Client.java and Controller.java). So it makes things
easier, the internal callback will now ask for information from the
server.