package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.Set;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

@ServerEndpoint(value="/abc")
public class Server {
  @OnOpen
  public void onOpen() throws IOException {
    System.out.println("The test did pass!");
  }
}

/*
@ServerEndpoint(value="/chat/{username}",
                decoders = MessageDecoder.class,
                encoders = MessageEncoder.class)

public class Server {

  private static HashMap<String, String> users = new HashMap<>();
  private static Set<Server> chatEndpoints = new CopyOnWriteArraySet<>();

  private Session session;

  @OnOpen
  public void onOpen(Session session,
                     @PathParam("username") String username)
      throws IOException {

    this.session = session;
    chatEndpoints.add(this);
    users.put(session.getId(), username);

    Message message = new Message();
    message.setFrom(username);
    message.setContent("Connected!");
    try {
      broadcast(message);
    } catch (Exception e) {}
  }

  @OnMessage
  public void onMessage(Session session, Message message)
      throws IOException {

    message.setFrom(users.get(session.getId()));
    try {
      broadcast(message);
    } catch (Exception e) {}
  }

  @OnClose
  public void onClose(Session session) throws IOException {

    chatEndpoints.remove(this);
    Message message = new Message();
    message.setFrom(users.get(session.getId()));
    message.setContent("Disconnected!");
    try {
      broadcast(message);
    } catch (Exception e) {}
  }

  @OnError
  public void onError(Session session, Throwable throwable) {}

  private static void broadcast(Message message)
      throws IOException, EncodeException {

    chatEndpoints.forEach(endpoint -> {
        synchronized (endpoint) {
          try {
            endpoint.session.getBasicRemote().
                sendObject(message);
          } catch (IOException | EncodeException e) {
            e.printStackTrace();
          }
        }
      });
  }
}
*/
