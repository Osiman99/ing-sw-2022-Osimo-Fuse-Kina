import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    /**
     * The socket port where the server listens to client connections.
     */
    public static int SOCKET_PORT = 7831;

    /**
     * The designated amount of time from when the socket connects until the connection breaks.
     */
    public static final int SOCKET_TIMEOUT = 2000;


    /**
     * Main Server loop
     * @param args are the command line or manually inserted at run time arguments
     */
    public static void main(String[] args) {

        if (args.length==2){
            SOCKET_PORT=Integer.parseInt(args[1]);
        }
        ServerSocket socket;
        System.out.println("My port is : "+ SOCKET_PORT);
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }
    }

}
