package websocket.demo;

import websocket.demo.physics.Controller;

import jakarta.websocket.*;

import org.apache.tomcat.websocket.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.lang.InterruptedException;
import java.lang.*;
import java.io.*;

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
 * --------------------------------------------------
 * The main method instantiates the connection between one client and
 * one server. It does not matter what the actual instances of the
 * client and server are. The framework is designed to handle multiple
 * sessions by means of the container instantiating a separate class
 * per connection.
 *
 * Part of the code has to be static in order to share
 * between multiple endpoints, but there are
 * more appropriate workarounds such as retrieving information
 * from the Sessions that come into the endpoint
 */

@ClientEndpoint

public class Client {

  // a handle used to control the game engine; makes sense to
  // have one controller per session, as a single endpoint class
  // is created for single connection, this is what we want
  public Controller controller;

  public void setController(Controller controller) {
    this.controller = controller;
  }

  /*
  @OnMessage
  public void onMessage() {

  }
  */

  @OnOpen
  public void onOpen(Session session) {

    // initialize the drawing engine
    controller = new Controller(this);
    setController(controller);

    // ask for data

    // and we are live!
    controller.start();
  }

  // Launches the Client entry and then everything
  // happens inside the annotated methods, this is simiply to
  // boot the Client
  public static void main(String[] args) {
    WebSocketContainer container = WsContainerProvider
        .getWebSocketContainer();
    assert(container != null);
    try {
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
