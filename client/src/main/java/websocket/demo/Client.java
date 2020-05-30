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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.*;

import com.jme3.math.Vector3f;

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
 * from the Sessions that come into the endpoint. If there is not
 * need to share data/state between client endpoints, there is no
 * need to allow static members.
 */

@ClientEndpoint
public class Client {

  // we remember the session as we will need to send messages to
  // the server through it, we only store the single session per
  // each instance of Client. As there is only going to be one
  // client, there is no need to have static references to the
  // endpoints (we do not communicate between clients, it is a
  // server - client 1:1 relatinoship)
  private Session session;

  // a handle used to control the game engine; makes sense to
  // have one controller per session, as a single endpoint class
  // is created for single connection, this is what we want
  private Controller controller;

  // to handle remote execution results
  private boolean computationIsReady;
  private Vector3f computationResult;

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void requestComputation(ArrayList<String> data) {
    computationIsReady = false;

    System.out.println("Forwarding (2/2) from thread: " + Thread.currentThread().getId() +
                       "; Data: " + data);

    try {
      session.getBasicRemote()
          .sendText (
              data.get(0) + "|" + data.get(1) + "|" +
              data.get(2) + "|" + data.get(3) + "|" +
              data.get(4) + "|" + data.get(5) + "|" +
              data.get(6) + "|" + data.get(7)
                     );
    } catch (Exception e) {
      e.printStackTrace();
    }

    Logger logger = Logger.getAnonymousLogger();
    logger.info("Forwarding completed");
  }

  public Vector3f getComputationResult() {

    System.out.println("Thread: " + Thread.currentThread().getId() +
                       " asked for computationResult");

    if (computationIsReady) {
      System.out.println("And it is ready...");
      return computationResult;
    }
    System.out.println("But it is not ready...");
    return null;
  }

  @OnMessage
  public void messageHandler(String data)
  {
    System.out.println("Thread: " + Thread.currentThread().getId() +
                       "Accepted : " + data);
    computationIsReady = true;
    computationResult = new Vector3f(1, 2, 3);
  }

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    controller = new Controller(this);
    setController(controller);
    System.out.println("Connected!");

    // and we are live!
    // controller.start();

    Runnable gameLoop = () -> {
      controller.start();
    };

    Thread gameThread = new Thread(gameLoop);
    gameThread.start();

    System.out.println("Out of @OnOpen!");
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
    } catch(URISyntaxException | DeploymentException | IOException e) {
      e.printStackTrace();
      System.out.println("Failed!");
    }
  }
}
