package it.polimi.ingsw.client.view.CLI;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class EriantysCLI {

    private final PrintStream out;
    private Thread inputThread;


    /**
     * Default constructor.
     */
    public EriantysCLI() {
        out = System.out;
    }

    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        inputThread = new Thread(futureTask);
        inputThread.start();

        String input = null;
        try {
            input = futureTask.get();
        } catch (InterruptedException e) {
            futureTask.cancel(true);
            Thread.currentThread().interrupt();
        }
        return input;
    }


    public ArrayList<String> welcome(String idIsola){
        ArrayList<String> res= new ArrayList<>();
        res.add(ANSIColor.WHITE+"ISOLA"+idIsola);
        res.add("+-----------------+");
        res.add("|                 |"/*+ANSIColor.RESET*/);
        res.add("|                 |");
        res.add("|                 |");
        res.add("+-----------------+");
        return res;
    }



    public static void main(String[] args) {
        /*if(args.length < 2) {
            System.out.println("Please provide server address and port");
            return;
        }

        serverHostName = args[0];
        serverPortNumber = Integer.parseInt(args[1]);
        */

        EriantysCLI cli = new EriantysCLI();
        ArrayList<String> esempio = new ArrayList<>();
/*
        esempio.add("WELCOME");
        esempio.add("TO");
        esempio.add("ERIANTYS");
        for(String s:esempio)
            System.out.println(s);
*/
/*
        System.out.println("WELCOME" +
                "\n" +
                "TO" +
                "\n" +
                "ERIANTYS");*/

        esempio = cli.welcome("5");
        for(String s:esempio)
            System.out.println(s);

    }
}