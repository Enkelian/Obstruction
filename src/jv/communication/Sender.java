package jv.communication;

import com.ericsson.otp.erlang.*;
import jv.util.Vector2d;

import java.io.IOException;

public class Sender {

    private OtpSelf self;
    private OtpPeer server;
    private OtpConnection connection;

    public Sender() throws Exception {
        this.self = new OtpSelf("client", "erljava" );
        this.server  = new OtpPeer("eNode");
        this.connection = this.self.connect(server);
    }

    public OtpErlangObject getGame() throws IOException, OtpErlangExit, OtpAuthException {
        connection.sendRPC("board_gen_server","getGame", new OtpErlangList());
        return connection.receiveRPC();
    }

    public OtpErlangObject sendMove(Vector2d move) throws OtpErlangExit, IOException, OtpAuthException {

        OtpErlangObject[] tupleObj = {new OtpErlangInt(move.x), new OtpErlangInt(move.y)};
        OtpErlangTuple tuple = new OtpErlangTuple(tupleObj);
        OtpErlangList moveList = new OtpErlangList(tuple);
        connection.sendRPC("board_gen_server","takeTurn", moveList);
        return connection.receiveRPC();

    }

    public OtpErlangObject startNewGame() throws OtpErlangExit, IOException, OtpAuthException {
        connection.sendRPC("board_gen_server","newGame", new OtpErlangList());
        return connection.receiveRPC();
    }
}
