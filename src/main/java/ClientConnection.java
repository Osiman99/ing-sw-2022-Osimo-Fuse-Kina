import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection extends Thread{

    Client c;
    Socket s;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    boolean shouldRun = true;

    public ClientConnection(Socket socket, Client client){
        c = client;
        s = socket;
    }

    public void sendStringToServer(String text){
        try {
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(s.getInputStream());
            dataOutputStream = new DataOutputStream(s.getOutputStream());

            while (shouldRun) {
                try {
                    while (dataInputStream.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    String reply = dataInputStream.readUTF();
                    System.out.println(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void close(){
        try {
            dataInputStream.close();
            dataOutputStream.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
