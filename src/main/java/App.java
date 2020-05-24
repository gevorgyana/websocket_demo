package websocket.demo;

import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/socket")
public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
