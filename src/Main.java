import controller.ConsolePlayer;
import view.GuiView;

public class Main {
    public static final boolean consoleMode = false;

    public static void main(String[] args) {
        if (consoleMode) {
            ConsolePlayer.play();
        } else {
            GuiView view = new GuiView();
            view.setVisible(true);
        }
    }
}
