package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;
import java.io.*;
import java.lang.*;
import java.util.logging.*;
import java.util.Random;

@ServerEndpoint(value="/entry")
public class Server {

  private String nextMessage;

  // prepare nextMessage
  private void processMessage(String data) {
    String[] decoded = data.split("\\|"); // this takes a regex, need to
                                          // escape the pipe symbol (bar)
    Random rand = new Random();

    /*
            return new Vector3f((v.x * (0.4f - p.y) / v.y) + p.x - ball_phy.getPhysicsLocation().x, 0.4f,
                (v.z * (0.4f - p.y) / v.y) + p.z - ball_phy.getPhysicsLocation().z + rand.nextFloat() % 3f);
    */

    float first = (Float.parseFloat(decoded[0]) *
                   (0.4f - Float.parseFloat(decoded[4])) /
                   (Float.parseFloat(decoded[1]) + 0.01f) + // avoid div by 0
                   Float.parseFloat(decoded[3]) -
                   Float.parseFloat(decoded[6]));

    System.out.println(first);

    float third = (Float.parseFloat(decoded[2]) *
                   (0.4f - Float.parseFloat(decoded[4])) /
                   (Float.parseFloat(decoded[1]) + 0.01f)) + // avoid div by 0
        Float.parseFloat(decoded[5]) -
        Float.parseFloat(decoded[7]) +
        rand.nextFloat() % 3f;

    System.out.println(third);

    nextMessage = new String(
        String.valueOf((first))  + "|" +
        String.valueOf(0.4f) + "|" +
        String.valueOf(third)
    );
  }

  @OnMessage
  public void onMessage(String data, Session session) {
    try {
      Logger.getAnonymousLogger().info("Received " + data);
      processMessage(data);
      System.out.println("Done processing message");
      session.getBasicRemote().sendText(nextMessage);
      System.out.println("Sent the message");
    } catch (Exception e) {
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
