package it.polimi.ingsw.client.view.CLI;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Command(name = "WELCOME")

public class Welcome implements Runnable{

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Welcome(){
        //reader =  new BufferedReader(new InputStreamReader(System.in));
    }


    public static void main(String[] args) {
        new CommandLine(new Welcome()).execute(args);
    }

    @Override
    public void run() {
        System.out.println("WELCOME");
        System.out.println("Insert nickname");
        do{
            try {
                String s = reader.readLine();
                //controller.send(s);
                if(s.equals("elis")){
                    System.out.println("nickname accepted");
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(true);

    }
}
