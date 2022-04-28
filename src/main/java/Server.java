import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket serverSocket;
    ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    boolean shouldRun = true;

    public static void main(String[] args) {
        new Server();
    }

    public Server(){
        try {
            serverSocket = new ServerSocket(5000);
            while (shouldRun){
                Socket socket = serverSocket.accept();
                ServerConnection sc = new ServerConnection(socket,this);
                sc.start();
                connections.add(sc);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}