import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.InetAddress;

public class ClientHandler implements Runnable{

    private transient Socket client;
    private InetAddress clientAddress;
    private transient ObjectOutputStream output;
    private transient ObjectInputStream input;


    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("can't open connection to " + client.getInetAddress());
            return;
        }
    }


    /**
     * Initializes a new handler using a specific socket connected to
     * a client.
     * @param client The socket connection to the client.
     */
    ClientHandler(Socket client)
    {
        this.client = client;
        clientAddress = client.getInetAddress();
    }
}