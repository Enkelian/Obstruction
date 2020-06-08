package jv.visualization;

import javax.swing.*;
import jv.communication.Parser;
import jv.communication.Sender;
import java.awt.*;

public class GameFrame extends JFrame {

    private SwingBoard swingBoard;
    private JPanel contents;

    public GameFrame(Parser parser, Sender sender){
        this.swingBoard = new SwingBoard(parser, sender);

        this.setTitle("Obstruction");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(new JPanel());
        this.contents = new JPanel(new BorderLayout());
        this.contents.add(this.swingBoard, BorderLayout.LINE_END);
        this.setContentPane(this.contents);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


}
