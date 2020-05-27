package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;
import java.io.*;

@ServerEndpoint(value="/endpoint")
public class Server {
  @OnOpen
  public void onOpen() throws IOException {
    System.out.println("The test did pass!");
  }
}
