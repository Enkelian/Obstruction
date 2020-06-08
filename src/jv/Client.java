package jv;


import jv.communication.Parser;
import jv.visualization.GameFrame;
import jv.communication.Sender;

public class Client {

    public static void main(String[] args) throws Exception {

        Sender sender = new Sender();
        Parser parser = new Parser();
        new GameFrame(parser, sender);

    }


}


