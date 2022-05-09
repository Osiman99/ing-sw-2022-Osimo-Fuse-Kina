package it.polimi.ingsw.client.view.CLI;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
        name = "WELCOME",
        description = "it prints WELCOME"
)

public class Welcome implements Runnable{

    public static void main(String[] args) {
        new CommandLine(new Welcome()).execute(args);
    }

    @Override
    public void run() {
        System.out.println("WELCOME");
    }
}
