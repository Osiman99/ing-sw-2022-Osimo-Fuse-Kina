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
        clearConsole();
        drawIslands(game);
        drawClouds(game);
        drawPlanks(game);
    }

    public void drawPlanks(Game game) {
        ArrayList<String> plankBoard = new ArrayList<>();
        ArrayList<String> studentsEntranceBoard = new ArrayList<>(9);
        ArrayList<String>[] diningRoomBoard = new ArrayList[5];
        ArrayList<String> towerBoard = new ArrayList<>();
        String[] professorsBoard = new String[5];

        int i, j, k;
        for(i=0; i<5; i++) {
            diningRoomBoard[i]= new ArrayList<String>();
        }

        for(i=0; i<game.getNumPlayers(); i++){
            Player playerBoard = game.getPlayers().get(i);
            //dalla lista degli studenti di ogni giocatore aggiungo un ● dello stesso colore in studentsEntranceBoard

            for(j=0; j<playerBoard.getPlank().getEntrance().getStudents().size(); j++)
                studentsEntranceBoard.add(convertANSI(playerBoard.getPlank().getEntrance().getStudents().get(j).getColor()));

            //e proseguo fino alla fine della lista (9 posizioni) settando un blanckspace per le celle vuote
            for(; j <9; j++)
                studentsEntranceBoard.add(" ");

            //salvo gli studenti della dining room con un ● colorato e poi riempio con blanckspace
            for(j=0; j<playerBoard.getPlank().getDiningRoom().length; j++) {
                for(k=0; k< playerBoard.getPlank().getDiningRoom()[j].getStudents().size(); k++)
                    diningRoomBoard[j].add(" ●");
                for(;k<10;k++)
                    diningRoomBoard[j].add("  ");
            }

            //dal towerSpace metto le torri colorate dentro a towerBoard con un ■
            for(j=0; j<playerBoard.getPlank().getTowerSpace().getTowersList().size(); j++)
                towerBoard.add(convertANSI(playerBoard.getPlayerColor()));
            for(;j<8;j++)
                towerBoard.add(" ");

            //per ogni player ho una lista contenente i professori ▲ che controlla
            for(j=0; j<5; j++) {
                if(game.getBoard().getProfessorsControlledBy()[j].equals(playerBoard.getNickname()))
                    professorsBoard[j]="▲";
                else
                    professorsBoard[j]=" ";
            }



            plankBoard.add("→ " +ANSIColor.PINK+ playerBoard.getNickname()+ANSIColor.RESET+"'s plank");
            plankBoard.add("╔══════════╦═══════════════════════╦═════════╦═════════════╗");
            plankBoard.add("║"+ANSIColor.RED_BACKGROUND+ANSIColor.BLACK+" Entrance "+ANSIColor.RESET+"║"+ANSIColor.RED_BACKGROUND+ANSIColor.BLACK+"      Dining Room      "+ANSIColor.RESET+"║"+ANSIColor.RED_BACKGROUND+ANSIColor.BLACK+"Professor"+ANSIColor.RESET+"║"+ANSIColor.RED_BACKGROUND+ANSIColor.BLACK+" Tower Space "+ANSIColor.RESET+"║");
            plankBoard.add("╠══════════╬═══════════════════════╬═════════╬═════════════╣");
            plankBoard.add("║       "+studentsEntranceBoard.get(0)+"  ║ "+ANSIColor.GREEN+diningRoomBoard[0].get(0)+diningRoomBoard[0].get(1)+diningRoomBoard[0].get(2)+diningRoomBoard[0].get(3)+diningRoomBoard[0].get(4)+diningRoomBoard[0].get(5)+diningRoomBoard[0].get(6)+diningRoomBoard[0].get(7)+diningRoomBoard[0].get(8)+diningRoomBoard[0].get(9)+ANSIColor.RESET+"  ║    "+ANSIColor.GREEN+professorsBoard[0]+ANSIColor.RESET+"    ║   "+towerBoard.get(0)+"     "+towerBoard.get(1)+"   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(1)+"    "+studentsEntranceBoard.get(2)+"  ║ "+ANSIColor.RED+diningRoomBoard[1].get(0)+diningRoomBoard[1].get(1)+diningRoomBoard[1].get(2)+diningRoomBoard[1].get(3)+diningRoomBoard[1].get(4)+diningRoomBoard[1].get(5)+diningRoomBoard[1].get(6)+diningRoomBoard[1].get(7)+diningRoomBoard[1].get(8)+diningRoomBoard[1].get(9)+ANSIColor.RESET+"  ║    "+ANSIColor.RED+professorsBoard[1]+ANSIColor.RESET+"    ║   "+towerBoard.get(2)+"     "+towerBoard.get(3)+"   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(3)+"    "+studentsEntranceBoard.get(4)+"  ║ "+ANSIColor.YELLOW_BOLD_BRIGHT+diningRoomBoard[2].get(0)+diningRoomBoard[2].get(1)+diningRoomBoard[2].get(2)+diningRoomBoard[2].get(3)+diningRoomBoard[2].get(4)+diningRoomBoard[2].get(5)+diningRoomBoard[2].get(6)+diningRoomBoard[2].get(7)+diningRoomBoard[2].get(8)+diningRoomBoard[2].get(9)+ANSIColor.RESET+"  ║    "+ANSIColor.YELLOW_BOLD_BRIGHT+professorsBoard[2]+ANSIColor.RESET+"    ║   "+towerBoard.get(4)+"     "+towerBoard.get(5)+"   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(5)+"    "+studentsEntranceBoard.get(6)+"  ║ "+ANSIColor.PINK+diningRoomBoard[3].get(0)+diningRoomBoard[3].get(1)+diningRoomBoard[3].get(2)+diningRoomBoard[3].get(3)+diningRoomBoard[3].get(4)+diningRoomBoard[3].get(5)+diningRoomBoard[3].get(6)+diningRoomBoard[3].get(7)+diningRoomBoard[3].get(8)+diningRoomBoard[3].get(9)+ANSIColor.RESET+"  ║    "+ANSIColor.PINK+professorsBoard[3]+ANSIColor.RESET+"    ║   "+towerBoard.get(6)+"     "+towerBoard.get(7)+"   ║");
            plankBoard.add("║  "+studentsEntranceBoard.get(7)+"    "+studentsEntranceBoard.get(8)+"  ║ "+ANSIColor.BLUE+diningRoomBoard[4].get(0)+diningRoomBoard[4].get(1)+diningRoomBoard[4].get(2)+diningRoomBoard[4].get(3)+diningRoomBoard[4].get(4)+diningRoomBoard[4].get(5)+diningRoomBoard[4].get(6)+diningRoomBoard[4].get(7)+diningRoomBoard[4].get(8)+diningRoomBoard[4].get(9)+ANSIColor.RESET+"  ║    "+ANSIColor.BLUE+professorsBoard[4]+ANSIColor.RESET+"    ║             ║");
            plankBoard.add("╚══════════╩═══════════════════════╩═════════╩═════════════╝");

            System.out.println("\n");
            for (String b : plankBoard)
                System.out.println(b);

            plankBoard.clear();
            studentsEntranceBoard.clear();
            towerBoard.clear();
            for(j=0; j<5; j++)
                diningRoomBoard[j].clear();
        }

        /*
            ╔ ═ ═ ═ ╦ ═ ═ ═ ╗       →
            ║   	║       ║       ● studente
            ╠ ═ ═ ═	╬ ═ ═ ═	╣       ▲ professore
            ║       ║       ║       •  ☻ ☼
            ╚ ═ ═ ═ ╩ ═ ═ ═ ╝       ■ torre
                                    ☬  ℳ madrenatura
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

    public void gotoXY(int x, int y) {
        System.out.print(String.format("\u001B[%d;%dH", y, x));
        // CSI n ; m H
    }

    //convert studentColor into ANSIColor
    private String convertANSI(StudentColor color) {

        switch (color) {
            case YELLOW:
                return ANSIColor.YELLOW_BOLD_BRIGHT + "●" + ANSIColor.RESET;

            case RED:
                return ANSIColor.RED + "●" + ANSIColor.RESET;

            case PINK:
                return ANSIColor.PINK + "●" + ANSIColor.RESET;

            case BLUE:
                return ANSIColor.BLUE + "●" + ANSIColor.RESET;

            case GREEN:
                return ANSIColor.GREEN + "●" + ANSIColor.RESET;

            default:
                return null;
        }
    }

    //convert towerColor into ANSIColor
    private String convertANSI(TowerColor color) {

            switch (color) {
                case BLACK: return ANSIColor.BLACK + "■" +ANSIColor.RESET;

                case WHITE: return ANSIColor.WHITE + "■" +ANSIColor.RESET;

                case GREY: return ANSIColor.BLACK_BOLD_BRIGHT + "■" +ANSIColor.RESET;

                default: return null;
            }
    }

    public void drawIslands(Game game) {
        ArrayList<String> islandBoard = new ArrayList<>();
        String numIsolaBoard;
        String mnBoard= "  ";
        ArrayList<String> towerBoard = new ArrayList<>();
        int[] numStudent= {0, 0, 0, 0, 0};
        int i,j;


        for(i=0; i< game.getBoard().getIslands().size(); i++) {
            Island currentIsland = game.getBoard().getIslands().get(i);

            //sets the island number
            if( i+1 < 10 )
                numIsolaBoard= " " + String.valueOf(i+1);
            else
                numIsolaBoard=String.valueOf(i+1);

            //sets the mother nature token
            if(currentIsland.isMotherNature())
                mnBoard= ANSIColor.ORANGE_BACKGROUND + ANSIColor.BLACK + "  MN  " + ANSIColor.RESET;
            else
                mnBoard= "      ";

            //add ■ for each tower onto the island
            for(j=0; j< currentIsland.getTowers().size(); j++)
                towerBoard.add(" "+convertANSI(currentIsland.getTowers().get(j).getColor()));
            for(; j<8; j++)
                towerBoard.add("  ");

            //counts the students for each color
            for(j=0; j<currentIsland.getStudents().size(); j++) {
                switch(currentIsland.getStudents().get(j).getColor()) {
                    case GREEN: numStudent[0]++;
                    break;
                    case RED: numStudent[1]++;
                    break;
                    case YELLOW: numStudent[2]++;
                    break;
                    case PINK: numStudent[3]++;
                    break;
                    case BLUE: numStudent[4]++;
                    break;
                }
            }

            islandBoard.add("╔═══════════════════════╦══════╗");
            islandBoard.add("║"+ANSIColor.WHITE_BACKGROUND+ANSIColor.BLACK+"I S L A N D  n° "+ numIsolaBoard +"    "+ ANSIColor.RESET+"║"+mnBoard+"║");
            islandBoard.add("╠═══════════════════════╬══════╣");
            islandBoard.add("║TOWERS"+towerBoard.get(0)+towerBoard.get(1)+towerBoard.get(2)+towerBoard.get(3)+towerBoard.get(4)+towerBoard.get(5)+towerBoard.get(6)+towerBoard.get(7) +" ║  xx  ║");
            islandBoard.add("╠═════╦═════╦═════╦═════╬═════╦╝");
            islandBoard.add("║ "+ANSIColor.GREEN+"●"+ANSIColor.RESET+" "+numStudent[0]+" ║ "+ANSIColor.RED+"●"+ANSIColor.RESET+" "+numStudent[1]+" ║ "+ANSIColor.YELLOW_BOLD_BRIGHT+"●"+ANSIColor.RESET+" "+numStudent[2]+" ║ "+ANSIColor.PINK+"●"+ANSIColor.RESET+" "+numStudent[3]+" ║ "+ANSIColor.BLUE+"●"+ANSIColor.RESET+" "+numStudent[4]+" ║");
            islandBoard.add("╚═════╩═════╩═════╩═════╩═════╝");

            System.out.println("\n");

            for(String b : islandBoard)
                System.out.println(b);

            islandBoard.clear();
            towerBoard.clear();
            for(j=0; j<5; j++)
                numStudent[j]=0;

        }


                // ☬  ℳ madrenatura
        /*
                            ╔═════════════════════════════════╦══════╗");
            plankBoard.add("║       I S L A N D  n°  8        ║  MN  ║");
            plankBoard.add("╠═════════════════════════════════╬══════╣");
            plankBoard.add("║     TOWERS: ■ ■ ■ ■ ■ ■ ■ ■     ║  xx  ║");
            plankBoard.add("╠═════════════════════════════════╩══════╣");
            plankBoard.add("║    ● x2   ● x0   ● x0   ● x1   ● x0    ║");
            plankBoard.add("╚════════════════════════════════════════╝");
         */
    }


    public void drawClouds(Game game){

        ArrayList<String> cloudBoard = new ArrayList<>();
        ArrayList<String> cloudStudents = new ArrayList<>();
        int i;

        for(i=0; i< game.getBoard().getClouds().size(); i++) {
            Cloud currentCloud= game.getBoard().getClouds().get(i);

            if(currentCloud.getStudentsSize() == 0){
                for(int k = 0; k < 4; k++){
                    cloudStudents.add(" ");
                }
            }
            for(int j=0; j< currentCloud.getStudentsSize(); j++)
                cloudStudents.add(convertANSI(currentCloud.getStudents().get(j).getColor()));

            if(cloudStudents.size()==3)
                cloudStudents.add(" ");

            cloudBoard.add("  ︵︶︵ Cloud "+ (i+1) +" ︵︶︵  ");
            cloudBoard.add("(          "+cloudStudents.get(3)+"          )");
            cloudBoard.add("  )   "+cloudStudents.get(0)+"         "+cloudStudents.get(1)+"   (  ");
            cloudBoard.add("(          "+cloudStudents.get(2)+"          )");
            cloudBoard.add("  ︶︵︶︵︶︵︶︵︶︵︶   ");

            System.out.println("\n");
            for(String b : cloudBoard)
                System.out.println(b);

            cloudBoard.clear();
            cloudStudents.clear();
        }


        /*
          ︵︶︵ Cloud 1 ︵︶︵
        (          ●          )
          )   ●         ●   (
        (          ●          )
          ︶︵︶︵︶︵︶︵︶︵︶
         */

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
                clearConsole();
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
                    out.println("Invalid number! Please try again.");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input! Please try again.");
            }
        } while (playersNumber < 2 || playersNumber > 3);
        notifyObserver(obs -> obs.onUpdatePlayersNumber(playersNumber));
    }


    @Override
    public void onDemandAssistantCard(List<AssistantCard> deck) {
        List<String> cardValueList= new ArrayList<String>();
        for (int i = 0; i < deck.size(); i++)
            cardValueList.add(Integer.toString(deck.get(i).getValue()));

        int assistantCardValue; //== value

        out.print("Enter one of the available Assistant Card Value : " + cardValueList);

        assistantCardValue= Integer.parseInt(nextLine());
        notifyObserver(obs -> obs.onUpdateAssistantCard(assistantCardValue));
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connection, String nickname) {
        clearConsole();

        if (nicknameAccepted && connection) {
            out.println("Hi, " + nickname + "! You connected to the server.");
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

        /*StringBuilder builderNumIsland = new StringBuilder();
        for(int i = 2; i < colorIn.length(); i++){
            builderNumIsland.append(String.valueOf(colorIn.charAt(i)));
        }
        String sNumIsland = builderNumIsland.toString();
        int numIsland = Integer.parseInt(sNumIsland);*/

        String plankOrIsland = nextLine();

        if(plankOrIsland.equals("p")) {
            showGenericMessage("Choose your student to move. [g/r/y/p/b]");
            String colorIn;
            colorIn = nextLine();
            switch (colorIn) {
                case "g":
                    notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.GREEN));
                    break;
                case "r":
                    notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.RED));
                    break;
                case "y":
                    notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.YELLOW));
                    break;
                case "p":
                    notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.PINK));
                    break;
                case "b":
                    notifyObserver(obs -> obs.onUpdateMoveStudentToDiningRoom(StudentColor.BLUE));
                    break;
                default:
                    showGenericMessage("Invalid input! Please try again.");
                    showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
            }

        }else if(plankOrIsland.equals("i")){
            try {                                                                                                                                                               //do while?
                showGenericMessage("Choose your student to move. Write the first letter of the color followed by a space and then the island number. (e.g.: r 3)");
                String colorIn = nextLine();
                char color = colorIn.charAt(0);
                String sColor = String.valueOf(color);
                StringBuilder builderNumIsland = new StringBuilder();
                for (int i = 2; i < colorIn.length(); i++) {
                    builderNumIsland.append(String.valueOf(colorIn.charAt(i)));
                }
                String sNumIsland = builderNumIsland.toString();
                int numIsland = Integer.parseInt(sNumIsland);
                switch (sColor) {
                    case "g":
                        notifyObserver(obs -> obs.onUpdateMoveStudentToIsland(StudentColor.GREEN, numIsland));
                        break;
                    case "r":
                        notifyObserver(obs -> obs.onUpdateMoveStudentToIsland(StudentColor.RED, numIsland));
                        break;
                    case "y":
                        notifyObserver(obs -> obs.onUpdateMoveStudentToIsland(StudentColor.YELLOW, numIsland));
                        break;
                    case "p":
                        notifyObserver(obs -> obs.onUpdateMoveStudentToIsland(StudentColor.PINK, numIsland));
                        break;
                    case "b":
                        notifyObserver(obs -> obs.onUpdateMoveStudentToIsland(StudentColor.BLUE, numIsland));
                        break;
                    default:
                        showGenericMessage("Invalid input! Please try again.");
                        showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
                }
            } catch (NumberFormatException e) {
                showGenericMessage("Invalid input! Please try again.");
                showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
            }
        }else{
            showGenericMessage("Invalid input! Please try again.");
            showGenericMessage("Do you want to move a student to your plank or island? [p/i]");
        }
    }

    public void onDemandCloud(){
        int numCloud = Integer.parseInt(nextLine());
        notifyObserver(obs -> obs.onUpdateCloud(numCloud));
    }

    public void onDemandMotherNatureMoves(int maxMoves){
        showGenericMessage("How many steps do you want Mother Nature does? Insert a number between 1 and " + maxMoves + ".");
        int numMoves = Integer.parseInt(nextLine());
        notifyObserver(obs -> obs.onUpdateMotherNatureMoves(numMoves));
    }

    public void onDemandQuit(){
        String quit = nextLine();
        if (quit.equals("quit")) {
            notifyObserver(obs -> obs.onUpdateQuit(quit));
        }else{
            showGenericMessage("Invalid input! Please try again.");
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
        if(genericMessage.equals("Do you want to move a student to your plank or island? [p/i]")){
            onDemandMoveStudent();
        }else if (genericMessage.equals("Which cloud do you choose? Insert the cloud number.")){
            onDemandCloud();
        }else if (genericMessage.equals("Type \"quit\" to leave the game.")){
            onDemandQuit();
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

    public static void clearConsole(){
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String text) {
        out.println("\n" + nicknameDisconnected + text);

        System.exit(1);
    }

}