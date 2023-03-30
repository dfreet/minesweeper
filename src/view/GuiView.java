package view;

import javax.swing.*;

public class GuiView extends JFrame {
    private MinesweeperForm form;

    public GuiView() {
       this.form = new MinesweeperForm();
       JPanel content = form.getMainPanel();
       this.setContentPane(content);
       this.pack();
       this.setTitle("Minesweeper");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
