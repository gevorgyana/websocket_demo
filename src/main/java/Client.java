package websocket.demo;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import jakarta.websocket.*;
import org.apache.tomcat.websocket.*;

/**
 * This example uses the Java Websocket API described here, see
 * client deployment (section 6.1). However, the API only describes the
 * interface of the specification, it does not provide full
 * implementation. I chose Tomcat's websocket implementation for
 * this application, however there was also an option of using
 * Tyrus project. As far as I understand from the source code,
 * the Tomcat's version of websockets uses jakarta.websocket.* API,
 * which is the same as the javax.websocket.*, but has a different
 * name. If we try to use the javax-version with Tomcat's
 * impelementation, the code will not compile, therefore jakarta is
 * needed.
 *
 * https://www.oracle.com/technical-resources/articles/java/jsr356.html
 *
 * For this to work, the server-side *.war must be
 * placed under $CATALINA_HOME/webapps/websocket. This demo
 * uses Tomcat.
 */

@ClientEndpoint

public class Client {
  public static void main(String[] args) {
    WebSocketContainer container = WsContainerProvider.getWebSocketContainer();
    assert(container != null);

    try {
      // we could also try to get the session object here
      container.connectToServer(
          Client.class,
          new URI("ws://localhost:8080/websocketDemo/entry")
      );
      System.out.println("Connected!");
    } catch(URISyntaxException | DeploymentException | IOException e) {
      e.printStackTrace();
      System.out.println("Failed!");
    }
  }
}
