package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;
import java.io.*;

@ServerEndpoint(value="/entry")
public class Server {

  @OnMessage
  public void onMessage(String data, Session session) {
    try {
      session.getBasicRemote().sendText("1|2|3");
    } catch (IOException e) {
      System.out.println("Unable to send message...");
    }
  }

  @OnOpen
  public void onOpen() throws IOException {
    System.out.println("The client has connected!");
  }
}
