package it.polimi.ingsw.client.view.CLI;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.server.model.AssistantCard;

import java.io.Console;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class EriantysCLI extends ViewObservable implements View {

    private final PrintStream out;
    private static final String CANCEL_INPUT = "User input canceled.";


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
        ArrayList<String> result = new ArrayList<>();
        result.add(ANSIColor.PURPLE_BOLD_BRIGHT);
        result.add("      (_)(_)(_)(_)(_)     (_)(_)(_)(_) _      (_)(_)(_)          _(_)_          (_) _       (_)     (_)(_)(_)(_)(_)     (_)_       _(_)      _(_)(_)(_)(_)_");
        result.add("     (_)                 (_)         (_)        (_)           _(_) (_)_        (_)(_)_     (_)           (_)             (_)_   _(_)       (_)          (_)");
        result.add("    (_) _  _            (_) _  _  _ (_)        (_)         _(_)     (_)_      (_)  (_)_   (_)           (_)               (_)_(_)         (_)_  _  _  _");
        result.add("   (_)(_)(_)           (_)(_)(_)(_)           (_)        (_) _  _  _ (_)     (_)    (_)_ (_)           (_)                 (_)             (_)(_)(_)(_)_");
        result.add("  (_)                 (_)   (_) _            (_)        (_)(_)(_)(_)(_)     (_)      (_)(_)           (_)                 (_)            _           (_)");
        result.add(" (_) _  _  _  _      (_)      (_) _       _ (_) _      (_)         (_)     (_)         (_)           (_)                 (_)           (_)_  _  _  _(_)");
        result.add("(_)(_)(_)(_)(_)     (_)         (_)     (_)(_)(_)     (_)         (_)     (_)         (_)           (_)                 (_)             (_)(_)(_)(_)");
        result.add(ANSIColor.RESET);

        for(String s:result){
            System.out.println(s);
        }

        try {
            askServerInfo();
        } catch (ExecutionException e) {
            out.println(CANCEL_INPUT);
        }
    }


    public void askServerInfo() throws ExecutionException {
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "127.0.0.1";
        String defaultPort = "12500";
        boolean correctInput;
        Scanner scanner = new Scanner(System.in);
        Console cnsl = System.console();

        out.println("The default value of address and port is shown between brackets.");

        do{
            out.print("Enter the server address [" + defaultAddress +"]:");
            String address = scanner.nextLine();

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
            String port = scanner.nextLine();

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
        try {
            String nickname = readLine();
            notifyObserver(obs -> obs.onUpdateNickname(nickname));
        } catch (ExecutionException e) {
            out.println(CANCEL_INPUT);
        }
    }

    public void askPlayersNumber() {
        int playersNumber;
        String question = "How many players are going to play? (You can choose between 2 or 3 players): ";

        try {
            playersNumber = numberInput(2, 3, null, question);
            notifyObserver(obs -> obs.onUpdatePlayersNumber(playersNumber));
        } catch (ExecutionException e) {
            out.println((CANCEL_INPUT));
        }
    }


    @Override
    public void askAssistantCard(List<AssistantCard> deck) {
        //clearCli();

        int AssistantCardId; //== value

        if(deck.size() > 1){
            out.println("Enter one of the available AssistantCardIds : ");
        }else if (deck.size() == 1){
            out.println("You're the last player, your only AssistantCardId is: ");

        }
    }

    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
        clearCli();

        if (nicknameAccepted && connectionSuccessful) {
            out.println("Hi, " + nickname + "! You connected to the server.");
        } else if (connectionSuccessful) {
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
     * Asks the user for a input number. The number must be between minValue and maxValue.
     * A wrong number (outside the range) or a non-number will result in a new request of the input.
     * A forbidden list of numbers inside the range can be set through jumpList parameter.
     * An output question can be set via question parameter.
     *
     * @param minValue the minimum value which can be inserted (included).
     * @param maxValue the maximum value which can be inserted (included).
     * @param jumpList a list of forbidden values inside the range [minValue, maxValue]
     * @param question a question which will be shown to the user.
     * @return the number inserted by the user.
     * @throws ExecutionException if the input stream thread is interrupted.
     */
    private int numberInput(int minValue, int maxValue, List<Integer> jumpList, String question) throws ExecutionException {
        int number = minValue--;

        if (jumpList == null)
            jumpList = List.of();

        do {
            try {
                out.print(question);
                number = Integer.parseInt(readLine());

                if (number < minValue || number > maxValue) {
                    out.println("Invalid number! Please try again.\n");
                } else if (jumpList.contains(number)) {
                    out.println("This number cannot be selected! Please try again.\n");
                }
            } catch (NumberFormatException e) {
                out.println("Invalid input! Please try again.\n");
            }
        } while (number < minValue || number > maxValue || jumpList.contains(number));

        return number;
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

    @Override
    public void showDisconnectionMessage(String nicknameDisconnected, String value) {

    }

}