package websocket.demo.physics;

/**
 * Originally written by
 * @author Alexandra
 */

import websocket.demo.Client;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;
import com.jme3.math.Vector3f;

import java.util.Arrays;
import java.util.ArrayList;

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
  }

  // This is asking the client endpoint to do something
  // The client is then responsible to notify us when he finishes
  private void forwardComputation(Camera cam,
                                  RigidBodyControl ball_phy) {

    Vector3f v = cam.getDirection();
    Vector3f p = cam.getLocation();
    float ball_phy_x = ball_phy.getPhysicsLocation().x;
    float ball_phy_z = ball_phy.getPhysicsLocation().z;

    ArrayList<String> data = new ArrayList<> (
        Arrays.asList (
            String.valueOf(v.x), String.valueOf(v.y),
            String.valueOf(v.z), String.valueOf(p.x),
            String.valueOf(p.y), String.valueOf(p.z),
            String.valueOf(ball_phy_x), String.valueOf(ball_phy_z)
        )
    );

    System.out.println("Forwarding (1/2) from thread: "+
                       Thread.currentThread().getId());

    client.requestComputation(data);
  }

  private final ActionListener actionListener = new ActionListener() {

      // this is the main entry point for controlling the logic of
      // the application; it currently is called whenever a user
      // presses rigth mouse button

      @Override
      public void onAction(String name, boolean keyPressed,
                           float tpf) {

        if (!name.equals("move") || keyPressed) {
          return;
        }

        for (RigidBodyControl ball_phy : balls.ball_phys) {

          // getComputationresult is called from 1 to 3 times
          // depending on the status of the communication
          forwardComputation(cam, ball_phy);

          while (client.getComputationResult() == null) {
            System.out.println("There is response yet");
          }

          ball_phy.setLinearVelocity(client.getComputationResult());
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
