package view;

import javax.swing.*;

public class MinesweeperForm {
    private JPanel mainPanel;
    private JPanel fieldPanel;
    private JLabel bombsLbl;
    private JLabel timeLbl;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getFieldPanel() { return fieldPanel; }

    public JLabel getBombsLbl() { return bombsLbl; }

    public JLabel getTimeLbl() { return timeLbl; }
}
