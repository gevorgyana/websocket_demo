package websocket.demo;

import javax.websocket.server.*;
import javax.websocket.*;
import java.io.*;

@ServerEndpoint(value="/entry")
public class Server {

  @OnOpen
  public void onOpen() throws IOException {
    System.out.println("The client has connected!");
  }
}
