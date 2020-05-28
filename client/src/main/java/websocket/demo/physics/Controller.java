package websocket.demo.physics;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;

/**
 * Originally written by
 * @author Alexandra
 */

import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector3f;

import websocket.demo.Client;

public class Controller extends SimpleApplication {

  private BulletAppState bulletAppState;
  private Ball balls;
  private final static String FONT = "Interface/Fonts/Default.fnt";
  private Client client;

  public Controller(Client client) {
    this.client = client;
  }

  @Override
  public void simpleInitApp() {
    bulletAppState = new BulletAppState();
    // bulletAppState.setDebugEnabled(true);
    stateManager.attach(bulletAppState);
    cam.setLocation(new Vector3f(0, 10f, 20f));
    cam.lookAt(new Vector3f(0, 0, -10), Vector3f.UNIT_Y);
    Floor floor = new Floor(assetManager, rootNode, bulletAppState);
    balls = new Ball(assetManager, rootNode, bulletAppState);
    inputManager.addMapping("move",
                            new MouseButtonTrigger(
                                MouseInput.BUTTON_RIGHT));
    inputManager.addListener(actionListener, "move");
    initCrossHairs();

    // initialize the connection
    // websocket http handshake happens here
  }

  private final ActionListener actionListener = new ActionListener() {

      // this is the main entry point for controlling the logic of
      // the application; it currently is called whenever a user
      // presses left or rigth mouse button

      @Override
      public void onAction(String name, boolean keyPressed, float tpf) {
        // talk to the server via the client singleton

        // wait for response
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          // we are the single thread, we cannot get interrupted
        }

        // ask the client, if he is ready; if not, then log that
        // connection is lost

        // if yes, take the data from the singleton and pass it
        // to the move method

        // todo later use move in a smart way, for now
        // simply call it.

        if (name.equals("move") && !keyPressed) {
          balls.move(cam);
        }
      }
    };

  private void initCrossHairs() {
    guiNode.detachAllChildren();
    guiFont = assetManager.loadFont(FONT);
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+");
    ch.setLocalTranslation(
        settings.getWidth() / 2 -
        guiFont.getCharSet().getRenderedSize() / 3 * 2,
        settings.getHeight() / 2 + ch.getLineHeight() / 2,
        0);
    guiNode.attachChild(ch);
  }
}
