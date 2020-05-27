package websocket.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.websocket.*;

public class MessageEncoder implements Encoder.Text<Message> {

  private static Gson gson = new Gson();

  @Override
  public String encode(Message message) throws EncodeException {
    return gson.toJson(message);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    // Custom initialization logic
  }

  @Override
  public void destroy() {
    // Close resources
  }
}
