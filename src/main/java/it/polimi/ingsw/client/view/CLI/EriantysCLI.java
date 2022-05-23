package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.StudentColor;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class EriantysCLI extends ViewObservable implements View {

    private final PrintStream out;
    private static final String CANCEL_INPUT = "User input canceled.";
    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));


    /**
     * Default constructor.
     */
    public EriantysCLI() {
        out = System.out;
    }


    /**
     * Reads a line from the standard input.
     *
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


    public void welcome() {
        ArrayList<String> welcome = new ArrayList<>();
        welcome.add(ANSIColor.PURPLE_BOLD_BRIGHT);
        welcome.add("      (_)(_)(_)(_)(_)     (_)(_)(_)(_) _      (_)(_)(_)          _(_)_          (_) _       (_)     (_)(_)(_)(_)(_)     (_)_       _(_)      _(_)(_)(_)(_)_");
        welcome.add("     (_)                 (_)         (_)        (_)           _(_) (_)_        (_)(_)_     (_)           (_)             (_)_   _(_)       (_)          (_)");
        welcome.add("    (_) _  _            (_) _  _  _ (_)        (_)         _(_)     (_)_      (_)  (_)_   (_)           (_)               (_)_(_)         (_)_  _  _  _");
        welcome.add("   (_)(_)(_)           (_)(_)(_)(_)           (_)        (_) _  _  _ (_)     (_)    (_)_ (_)           (_)                 (_)             (_)(_)(_)(_)_");
        welcome.add("  (_)                 (_)   (_) _            (_)        (_)(_)(_)(_)(_)     (_)      (_)(_)           (_)                 (_)            _           (_)");
        welcome.add(" (_) _  _  _  _      (_)      (_) _       _ (_) _      (_)         (_)     (_)         (_)           (_)                 (_)           (_)_  _  _  _(_)");
        welcome.add("(_)(_)(_)(_)(_)     (_)         (_)     (_)(_)(_)     (_)         (_)     (_)         (_)           (_)                 (_)             (_)(_)(_)(_)");
        welcome.add(ANSIColor.RESET);

        for(String w:welcome){
            System.out.println(w);
        }

      /* ArrayList<StudentColor> printCloud = new ArrayList<>();
        printCloud.add(StudentColor.YELLOW);
        printCloud.add(StudentColor.RED);
        printCloud.add(StudentColor.BLUE);
        printCloud.add(StudentColor.PINK);
        cloud(4, printCloud);*/


        try {
            askServerInfo();
        } catch (ExecutionException e) {
            out.println(CANCEL_INPUT);
        }
    }

    public void drawBoard(Game game) {
        drawPlanks(game);
        drawIslands(game);
        ArrayList<StudentColor> list = new ArrayList<>(List.of(StudentColor.BLUE, StudentColor.GREEN, StudentColor.RED, StudentColor.YELLOW));
        drawClouds(3, list);

    }

    public void drawPlanks(Game game) {
        ArrayList<String> plank = new ArrayList<>();
        plank.add("╔════════════════════╦═══════════════════════════════════════╦═════════════════════╗");
        plank.add("║      Entrance      ║              Dining Room              ║      Tower Space    ║");
        plank.add("║");




        /*  ╔ ═ ═ ═ ╦ ═ ═ ═ ╗
            ║   	║       ║       ☻ studente
            ╠ ═ ═ ═	╬ ═ ═ ═	╣       ▲ torre
            ║       ║       ║       •
            ╚ ═ ═ ═ ╩ ═ ═ ═ ╝

        */
    }

    public void drawIslands(Game game) {

    }


    public void drawClouds(int cloudSize, ArrayList<StudentColor> students){

        ArrayList<String> cloud = new ArrayList<>();
        ArrayList<String> coloredStudents = new ArrayList<>();

        for(int i = 0 ; i < cloudSize; i++){
            if(students.size() >= 1){
                switch (students.get(i)){
                    case YELLOW: coloredStudents.add(ANSIColor.YELLOW_BOLD_BRIGHT+ "O" +ANSIColor.RESET);
                     break;
                    case RED: coloredStudents.add(ANSIColor.RED+ "O" +ANSIColor.RESET);
                     break;
                    case PINK: coloredStudents.add(ANSIColor.PURPLE_BOLD_BRIGHT+ "O" +ANSIColor.RESET);
                     break;
                    case BLUE: coloredStudents.add(ANSIColor.BLUE+ "O" +ANSIColor.RESET);
                     break;
                    case GREEN: coloredStudents.add(ANSIColor.GREEN+ "O" +ANSIColor.RESET);
                     break;
                }
            }else {
                for (int j=0; j <= 3; j++){
                    coloredStudents.add(" ");
                }
            }
        }


        if(cloudSize==3) {
            cloud.add(" +---------------+");
            cloud.add(" +       " + coloredStudents.get(2) + "       +");
            cloud.add(" +     "+coloredStudents.get(1)+"  "+coloredStudents.get(0)+"      +");
            cloud.add(" +---------------+");
        }else if(cloudSize==4){
            cloud.add(" +---------------+");
            cloud.add(" +      "+coloredStudents.get(2)+" "+coloredStudents.get(3)+"      +");
            cloud.add(" +      "+coloredStudents.get(1)+" "+coloredStudents.get(0)+"      +");
            cloud.add(" +---------------+");
        }
        for(String c:cloud){
            System.out.println(c);
        }

    }



    public void askServerInfo() throws ExecutionException {

        System.out.println(ANSIColor.GREEN_BOLD_BRIGHT);
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "127.0.0.1";
        String defaultPort = "12500";
        boolean correctInput;
        //Scanner scanner = new Scanner(System.in);

        out.println("The default value of address and port is shown between brackets.");

        do{
            out.print("Enter the server address [" + defaultAddress +"]:");
            String address = nextLine();

            if (address.equals("") || address.equals("127.0.0.1")){
                serverInfo.put("address", defaultAddress);
                correctInput = true;
            }else{
                out.println("Invalid address");
                clearCli();
                correctInput = false;
            }
        } while(!correctInput);

        do{
            out.print("Enter the server port [" + defaultPort +"]:");
            String port = nextLine();

            if (port.equals("") || port.equals("12500")){
                serverInfo.put("port", defaultPort);
                correctInput = true;
            }else {
                out.println("Invalid port");
                correctInput = false;
            }
        }while (!correctInput);

        System.out.println(serverInfo);
        notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));

    }


    @Override
    public void askNickname() {
        out.print("Enter nickname: ");
        String nickname = nextLine();
        notifyObserver(obs -> obs.onUpdateNickname(nickname));

    }

    public void askPlayersNumber() {
        int playersNumber;
        out.print("How many players are going to play? (You can choose between 2 or 3 players): ");
        playersNumber = Integer.parseInt(nextLine());
        do {
            try {
                if (playersNumber < 2 || playersNumber > 3) {
                    out.println("Invalid number! Please try again.\n");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input! Please try again.\n");
            }
        } while (playersNumber < 2 || playersNumber > 3);
        notifyObserver(obs -> obs.onUpdatePlayersNumber(playersNumber));
    }


    @Override
    public void askAssistantCard(List<AssistantCard> deck) {
        //clearCli();
        List<String> cardValueList= new ArrayList<String>();
        for (int i = 0; i < deck.size(); i++)
            cardValueList.add(Integer.toString(deck.get(i).getValue()));

        int assistantCardValue; //== value

        out.print("Enter one of the available AssistantCard Value : " + cardValueList);

        assistantCardValue= Integer.parseInt(nextLine());
        notifyObserver(obs -> obs.onUpdateAssistantCard(assistantCardValue));
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connection, String nickname) {
        clearCli();

        if (nicknameAccepted && connection) {
            out.println("\n\n\n\n\n\n\n\n\n\nHi, " + nickname + "! You connected to the server.");
        } else if (connection) {
            askNickname();
        } else if (nicknameAccepted) {
            out.println("Max players reached. Connection refused.");
            out.println("EXIT.");

            System.exit(1);
        }/* else {
            showErrorAndExit("Could not contact server.");
        }*/
    }



    /**
     * Clears the EriantysCLI terminal.
     */
    public void clearCli() {
        out.print(ANSIColor.CLEAR);
        out.flush();
    }

    public void showGenericMessage(String genericMessage) {
        out.println(genericMessage);
    }

    public String nextLine(){
        String s;
        System.out.print("\n< ");
        try {
            s = console.readLine();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return ("DIGIT ERROR");
        }
    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String value) {

    }

}