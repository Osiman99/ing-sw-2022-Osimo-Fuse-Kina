import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{

    Socket socket;
    Server server;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    boolean shouldRun = true;

    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
    }

    public void sendStringToClient(String text){
        try {
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStringToAllClients(String text){
        for (int i = 0; i < server.connections.size(); i++){
            ServerConnection serverConnection = server.connections.get(i);
            serverConnection.sendStringToClient(text);
        }
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while(shouldRun){
                while(dataInputStream.available() == 0){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String textIn = dataInputStream.readUTF();
                sendStringToAllClients(textIn);

            }
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}