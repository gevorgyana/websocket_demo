UPDATE: Use wildfly for containing the server! Tomcat has bugs!
For deploying with wildfly, run it in standalone mode and drop the war
to standalone/deployments.

If you want to try Tomcat, here is the instruction.
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