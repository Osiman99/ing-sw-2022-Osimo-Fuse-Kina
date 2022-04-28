import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public static void main(String[] args) {

        new Server();
    }

    public Server(){
        try {
            serverSocket = new ServerSocket(5000);
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            listenForData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForData(){
        while(true) {
            try {
                while (dataInputStream.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                String dataIn = dataInputStream.readUTF();
                System.out.println(dataIn);
                dataOutputStream.writeUTF(dataIn);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
