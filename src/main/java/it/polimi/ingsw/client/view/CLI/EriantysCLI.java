package it.polimi.ingsw.client.view.CLI;

public class EriantysCLI {

    public static String serverHostName;
    public static int serverPortNumber;


    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Please provide server address and port");
            return;
        }

        serverHostName = args[0];
        serverPortNumber = Integer.parseInt(args[1]);

    }
}
