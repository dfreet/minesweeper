package view;

import javax.swing.*;

public class GuiView extends JFrame {
    private final MinesweeperForm form;
    private final JMenuItem newGameItm;
    private final JRadioButton beginnerBtn;
    private final JRadioButton intermediateBtn;
    private final JRadioButton expertBtn;

    public GuiView() {
       this.form = new MinesweeperForm();
       JPanel content = form.getMainPanel();
       this.setContentPane(content);
       this.pack();
       this.setTitle("Minesweeper");
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       JMenuBar menuBar = new JMenuBar();
       JMenu gameMnu = new JMenu("Game");
       ButtonGroup buttonGroup = new ButtonGroup();

       beginnerBtn = new JRadioButton("Beginner (9x9) 10", true);
       intermediateBtn = new JRadioButton("Intermediate (16x16) 40");
       expertBtn = new JRadioButton("Expert (16x30) 99");
       newGameItm = new JMenuItem("New Game");
       JMenuItem exitItm = new JMenuItem("Exit");
       exitItm.addActionListener(a -> System.exit(0));

       buttonGroup.add(beginnerBtn);
       buttonGroup.add(intermediateBtn);
       buttonGroup.add(expertBtn);

       gameMnu.add(beginnerBtn);
       gameMnu.add(intermediateBtn);
       gameMnu.add(expertBtn);
       gameMnu.add(newGameItm);
       gameMnu.add(exitItm);
       menuBar.add(gameMnu);
       this.setJMenuBar(menuBar);
    }

    public JMenuItem getNewGameItm() { return newGameItm; }

    public JRadioButton getBeginnerBtn() { return beginnerBtn; }

    public JRadioButton getIntermediateBtn() { return intermediateBtn; }

    public JRadioButton getExpertBtn() { return expertBtn; }

    public MinesweeperForm form() { return form; }
}
