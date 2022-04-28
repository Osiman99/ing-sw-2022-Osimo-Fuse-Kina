import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public static void main(String[] args) {
        new Client();
    }

    public Client(){
        try {
            Socket socket = new Socket("localhost", 5000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            listenForInput();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void listenForInput(){
        Scanner console = new Scanner(System.in);

        while (true){
            while(!console.hasNextLine()) {
                try {
                    Thread.sleep(1);
                  // System.out.println("waiting user input");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String input = console.nextLine();

            if (input.toLowerCase().equals("quit")){
                break;
            }

            try {
                dataOutputStream.writeUTF(input);
                dataOutputStream.flush();

                while (dataInputStream.available() == 0){
                    try {
                        Thread.sleep(1);
                       // System.out.println("waiting...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String reply = dataInputStream.readUTF();
                System.out.println(reply);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
