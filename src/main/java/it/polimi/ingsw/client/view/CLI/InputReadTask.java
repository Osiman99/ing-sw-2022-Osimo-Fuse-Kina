package it.polimi.ingsw.client.view.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * This class is used to read the input stream and making the input kind of interruptible.
 */
public class InputReadTask implements Callable<String> {

    private final BufferedReader bufferedReader;

    public InputReadTask(){
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String call() throws IOException, InterruptedException {
        String input;
        //wait until there is data to complete a readLine()
        while (!bufferedReader.ready()){
            Thread.sleep(200);
        }
        input = bufferedReader.readLine();
        return null;
    }
}

