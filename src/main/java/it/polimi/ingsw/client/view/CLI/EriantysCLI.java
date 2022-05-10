package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.ViewObservable;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class EriantysCLI extends ViewObservable implements View {


    /**
     * Default constructor.
     */
    public EriantysCLI() {
        PrintStream out = System.out;
    }


    /**
     * Reads a line from the standard input.
     * @return the string read from the input.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    public String readLine() throws ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new InputReadTask());
        Thread inputThread = new Thread(futureTask);
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


    public ArrayList<String> welcome(String serverIp){
        ArrayList<String> result= new ArrayList<>();
        result.add(ANSIColor.GREEN_BOLD_BRIGHT+"Server IP : "+serverIp);
        result.add(ANSIColor.PURPLE_BOLD_BRIGHT);
        //result.add(" _  _  _  _  _        _  _  _  _          _  _  _               _              _           _       _  _  _  _  _       _           _         _  _  _  _");
        result.add("      (_)(_)(_)(_)(_)     (_)(_)(_)(_) _      (_)(_)(_)          _(_)_          (_) _       (_)     (_)(_)(_)(_)(_)     (_)_       _(_)      _(_)(_)(_)(_)_");
        result.add("     (_)                 (_)         (_)        (_)           _(_) (_)_        (_)(_)_     (_)           (_)             (_)_   _(_)       (_)          (_)");
        result.add("    (_) _  _            (_) _  _  _ (_)        (_)         _(_)     (_)_      (_)  (_)_   (_)           (_)               (_)_(_)         (_)_  _  _  _");
        result.add("   (_)(_)(_)           (_)(_)(_)(_)           (_)        (_) _  _  _ (_)     (_)    (_)_ (_)           (_)                 (_)             (_)(_)(_)(_)_");
        result.add("  (_)                 (_)   (_) _            (_)        (_)(_)(_)(_)(_)     (_)      (_)(_)           (_)                 (_)            _           (_)");
        result.add(" (_) _  _  _  _      (_)      (_) _       _ (_) _      (_)         (_)     (_)         (_)           (_)                 (_)           (_)_  _  _  _(_)");
        result.add("(_)(_)(_)(_)(_)     (_)         (_)     (_)(_)(_)     (_)         (_)     (_)         (_)           (_)                 (_)             (_)(_)(_)(_)");
        return result;
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
        ArrayList<String> entryScene;

        entryScene = cli.welcome("127.0.0.1");
        for(String s:entryScene)
            System.out.println(s);
    }
}