package it.polimi.ingsw.client.view.CLI;


/**
 * Collection of escape coded console text colors
 */
public class ANSIColor {

    public static final String RESET = "\u001B[0m";
    public static final String CLEAR = "\033[H\033[2J";


    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PINK = "\033[38;5;206m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String CYAN_BOLD = "\033[1;36m";

    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String YELLOW_BOLD_BRIGHT ="\033[1;93m";
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m";
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m";


}
