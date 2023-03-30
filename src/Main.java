import model.ConsolePlayer;

public class Main {
    public static final boolean consoleMode = true;

    public static void main(String[] args) {
        if (consoleMode) {
            ConsolePlayer.play();
        }
    }
}
