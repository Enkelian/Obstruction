package jv.visualization;

import com.ericsson.otp.erlang.OtpErlangObject;
import jv.communication.Parser;
import jv.util.Type;
import javax.swing.*;
import jv.communication.Sender;
import jv.util.Vector2d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwingBoard extends JPanel implements MouseListener {

    private Parser parser;

    private Sender sender;

    public SwingBoard(Parser parser, Sender sender){
        this.parser = parser;
        this.sender = sender;
        this.setPreferredSize(new Dimension(600, 600));
        this.setFocusable(true);
        this.addMouseListener(this);

        try {
            this.parser.parseReply(this.sender.getGame());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    private Vector2d getCellCoords(Vector2d v){
        return new Vector2d(v.x / 100, v.y / 100);
    }

    @Override
    protected void paintComponent(Graphics g){
        for(int x = 0; x < 600; x += 100){
            for(int y = 0; y < 600; y += 100){

                Type cellType = this.parser.getType(getCellCoords(new Vector2d(x, y)));

                switch (cellType){
                    case X:
                        g.setColor(new Color(148, 176, 218));
                        break;
                    case O:
                        g.setColor(new Color(218, 65, 103));
                        break;
                    case G:
                        g.setColor(new Color(143, 145, 162));
                        break;
                    default:
                        g.setColor(new Color(241, 247, 237));
                }
                int cellSize = 100;
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(new Color(191, 210, 191));
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Vector2d move = getCellCoords(new Vector2d(mouseEvent.getX(), mouseEvent.getY()));
        try {

            OtpErlangObject reply = this.sender.sendMove(move);

            this.parser.parseReply(reply);
            this.repaint();

            if(this.parser.didGameEnd()){
                this.sender.startNewGame();
                this.parser.clearAll();
                this.repaint();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }


}
