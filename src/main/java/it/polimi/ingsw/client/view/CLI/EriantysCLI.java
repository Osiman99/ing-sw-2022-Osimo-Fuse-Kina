package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.*;

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
            onDemandServerInfo();
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
        ArrayList<String> plankBoard = new ArrayList<>();
        ArrayList<String> studentsEntranceBoard = new ArrayList<>(9);

        ArrayList<String> diningRoomBoard[] = new ArrayList[5];

        int i, j, k;
        for(i=0; i<5; i++) {
            diningRoomBoard[i]= new ArrayList<String>();
        }

        for(i=0; i<game.getNumPlayers(); i++){

            //dalla lista degli studenti di ogni giocatore aggiungo un ● dello stesso colore in studentsEntranceBoard

            for(j=0; j<game.getPlayers().get(i).getPlank().getEntrance().getStudents().size(); j++)
                studentsEntranceBoard.add(convertANSI(game.getPlayers().get(i).getPlank().getEntrance().getStudents().get(j).getColor()));

            //e proseguo fino alla fine della lista (9 posizioni) settando un blanckspace per le celle vuote
            for(; j <9; j++)
                studentsEntranceBoard.add(" ");

            for(j=0; j<game.getPlayers().get(i).getPlank().getDiningRoom().length; j++) {
                for(k=0; k< game.getPlayers().get(i).getPlank().getDiningRoom()[j].getStudents().size(); k++)
                    diningRoomBoard[j].add(" ●");
                for(;k<10;k++)
                    diningRoomBoard[j].add("  ");
            }

            plankBoard.add("→ " +ANSIColor.PINK+ game.getPlayers().get(i).getNickname()+ANSIColor.RESET+"'s plank");
            plankBoard.add("╔══════════╦═══════════════════════╦═════════╦═════════════╗");
            plankBoard.add("║ "+ANSIColor.RED_BACKGROUND+ANSIColor.CYAN_BOLD+"Entrance"+ANSIColor.RESET+" ║      "+ANSIColor.RED_BACKGROUND+ANSIColor.CYAN_BOLD+"Dining Room"+ANSIColor.RESET+"      ║"+ANSIColor.RED_BACKGROUND+ANSIColor.CYAN_BOLD+"Professor"+ANSIColor.RESET+"║ "+ANSIColor.RED_BACKGROUND+ANSIColor.CYAN_BOLD+"Tower Space"+ANSIColor.RESET+" ║");
            plankBoard.add("╠══════════╬═══════════════════════╬═════════╬═════════════╣");
            plankBoard.add("║       "+studentsEntranceBoard.get(0)+"  ║ "+ANSIColor.GREEN+diningRoomBoard[0].get(0)+diningRoomBoard[0].get(1)+diningRoomBoard[0].get(2)+diningRoomBoard[0].get(3)+diningRoomBoard[0].get(4)+diningRoomBoard[0].get(5)+diningRoomBoard[0].get(6)+diningRoomBoard[0].get(7)+diningRoomBoard[0].get(8)+diningRoomBoard[0].get(9)+ANSIColor.RESET+"  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(1)+"    "+studentsEntranceBoard.get(2)+"  ║ "+ANSIColor.RED+diningRoomBoard[1].get(0)+diningRoomBoard[1].get(1)+diningRoomBoard[1].get(2)+diningRoomBoard[1].get(3)+diningRoomBoard[1].get(4)+diningRoomBoard[1].get(5)+diningRoomBoard[1].get(6)+diningRoomBoard[1].get(7)+diningRoomBoard[1].get(8)+diningRoomBoard[1].get(9)+ANSIColor.RESET+"  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(3)+"    "+studentsEntranceBoard.get(4)+"  ║ "+ANSIColor.YELLOW+diningRoomBoard[2].get(0)+diningRoomBoard[2].get(1)+diningRoomBoard[2].get(2)+diningRoomBoard[2].get(3)+diningRoomBoard[2].get(4)+diningRoomBoard[2].get(5)+diningRoomBoard[2].get(6)+diningRoomBoard[2].get(7)+diningRoomBoard[2].get(8)+diningRoomBoard[2].get(9)+ANSIColor.RESET+"  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(5)+"    "+studentsEntranceBoard.get(6)+"  ║ "+ANSIColor.PINK+diningRoomBoard[3].get(0)+diningRoomBoard[3].get(1)+diningRoomBoard[3].get(2)+diningRoomBoard[3].get(3)+diningRoomBoard[3].get(4)+diningRoomBoard[3].get(5)+diningRoomBoard[3].get(6)+diningRoomBoard[3].get(7)+diningRoomBoard[3].get(8)+diningRoomBoard[3].get(9)+ANSIColor.RESET+"  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(7)+"    "+studentsEntranceBoard.get(8)+"  ║ "+ANSIColor.BLUE+diningRoomBoard[4].get(0)+diningRoomBoard[4].get(1)+diningRoomBoard[4].get(2)+diningRoomBoard[4].get(3)+diningRoomBoard[4].get(4)+diningRoomBoard[4].get(5)+diningRoomBoard[4].get(6)+diningRoomBoard[4].get(7)+diningRoomBoard[4].get(8)+diningRoomBoard[4].get(9)+ANSIColor.RESET+"  ║    ▲    ║             ║");
            plankBoard.add("╚══════════╩═══════════════════════╩═════════╩═════════════╝");

            for (String p : plankBoard)
                System.out.println(p);
            plankBoard.clear();
            studentsEntranceBoard.clear();
        }

        /*
            ╔ ═ ═ ═ ╦ ═ ═ ═ ╗       →
            ║   	║       ║       ● studente
            ╠ ═ ═ ═	╬ ═ ═ ═	╣       ▲ professore
            ║       ║       ║       •  ☻ ☼
            ╚ ═ ═ ═ ╩ ═ ═ ═ ╝       ■ torre
                            → nickname's plank
            "               ╔══════════╦═══════════════════════╦═════════╦═════════════╗");
            plankBoard.add("║ Entrance ║      Dining Room      ║Professor║ Tower Space ║");
            plankBoard.add("╠══════════╬═══════════════════════╬═════════╬═════════════╣");
            plankBoard.add("║       ●  ║  ● ● ● ● ● ● ● ● ● ●  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  ●    ●  ║  ● ● ● ● ● ● ● ● ● ●  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  ●    ●  ║  ● ● ● ● ● ● ● ● ● ●  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  ●    ●  ║  ● ● ● ● ● ● ● ● ● ●  ║    ▲    ║   ■     ■   ║");
            plankBoard.add("║  ●    ●  ║  ● ● ● ● ● ● ● ● ● ●  ║    ▲    ║             ║");
            plankBoard.add("╚══════════╩═══════════════════════╩═════════╩═════════════╝");
        */
    }

    private String convertANSI(StudentColor color) {

        switch (color) {
            case YELLOW: return ANSIColor.YELLOW_BOLD_BRIGHT + "●" +ANSIColor.RESET;

            case RED: return ANSIColor.RED + "●" +ANSIColor.RESET;

            case PINK: return ANSIColor.PINK + "●" +ANSIColor.RESET;

            case BLUE: return ANSIColor.BLUE + "●" +ANSIColor.RESET;

            case GREEN: return ANSIColor.GREEN + "●" +ANSIColor.RESET;

            default: return null;
        }

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



    public void onDemandServerInfo() throws ExecutionException {

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
    public void onDemandNickname() {
        out.print("Enter nickname: ");
        String nickname = nextLine();
        notifyObserver(obs -> obs.onUpdateNickname(nickname));

    }

    public void onDemandPlayersNumber() {
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
    public void onDemandAssistantCard(List<AssistantCard> deck) {
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
            onDemandNickname();
        } else if (nicknameAccepted) {
            out.println("Max players reached. Connection refused.");
            out.println("EXIT.");

            System.exit(1);
        }/* else {
            showErrorAndExit("Could not contact server.");
        }*/
    }

    public void onDemandMoveStudent(){

        String colorIn = nextLine();

        switch (colorIn){
            case "green":
                notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.GREEN));
                break;
            case "red":
                notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.RED));
                break;
            case "yellow":
                notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.YELLOW));
                break;
            case "pink":
                notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.PINK));
                break;
            case "blue":
                notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.BLUE));
                break;
        }
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
        if(genericMessage.equals("Choose your student to move.")){
            onDemandMoveStudent();
        }
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