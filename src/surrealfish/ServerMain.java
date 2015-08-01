package surrealfish;

import arkhados.net.DefaultReceiver;
import arkhados.net.OneTrueMessage;
import arkhados.net.Receiver;
import arkhados.net.ServerSender;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import java.io.IOException;
import surrealfish.net.DataRegistration;
import surrealfish.net.ServerNetListener;

public class ServerMain extends SimpleApplication {

    public static void main(String[] args) {
        ServerMain app = new ServerMain();
        app.start();
    }
    private Server server;
    private Receiver receiver;
    private ServerSender sender;

    @Override
    public void simpleInitApp() {
        BulletAppState physics = new BulletAppState();
        physics.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(physics);
        physics.getPhysicsSpace().setAccuracy(1 / 60f);


        try {
            server = Network.createServer(12345, 12345);
            server.start();
        } catch (IOException e) {
            System.exit(1);
        }

        DataRegistration.register();

        receiver = new DefaultReceiver();
        stateManager.attach(receiver);        
        server.addMessageListener(receiver, OneTrueMessage.class);        

        sender = new ServerSender(server);
        stateManager.attach(sender);
        receiver.registerCommandHandler(sender);
        
        server.addConnectionListener(new ServerNetListener(this, server));
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
