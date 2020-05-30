package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;
import java.io.*;
import java.lang.*;

@ServerEndpoint(value="/entry")
public class Server {

  @OnMessage
  public void onMessage(String data, Session session) {
    try {
      session.getBasicRemote().sendText("1|2|3");
      System.out.println("Sent the message");
    } catch (IOException e) {
      System.out.println("Unable to send message...");
    }
  }

  @OnError
  public void onError(Throwable err) {
    err.printStackTrace();
  }

  @OnOpen
  public void onOpen() {
    try {
      System.out.println("The client has connected!");
    } catch (Exception e) {
      System.out.println("Unable to open...");
    }
  }
}
