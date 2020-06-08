package jv.communication;

import com.ericsson.otp.erlang.*;
import jv.util.Type;

import jv.util.Vector2d;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<Vector2d> xs;
    private List<Vector2d> os;
    private List<Vector2d> grays;
    private Type turn;
    private boolean gameEnded = false;

    public Parser(){
        this.xs = new ArrayList<>();
        this.os = new ArrayList<>();
        this.grays = new ArrayList<>();
    }

    public void clearAll(){
        this.xs.clear();;
        this.os.clear();
        this.grays.clear();
    }

    public Type getType(Vector2d position){
        if(this.grays.contains(position)) return Type.G;
        if(this.xs.contains(position)) return Type.X;
        if(this.os.contains(position)) return Type.O;
        return Type.N;
    }


    public void parseType(OtpErlangObject otpObject, Type type) throws OtpErlangRangeException {
        List<Vector2d> typeList = new ArrayList<>();

        if(otpObject.equals(new OtpErlangList()) || !(otpObject instanceof  OtpErlangList)) return;

            OtpErlangList list = (OtpErlangList) otpObject;

            for (OtpErlangObject e : list.elements()) {
                OtpErlangTuple tuple = (OtpErlangTuple) e;
                int x = ((OtpErlangLong) tuple.elementAt(0)).intValue();
                int y = ((OtpErlangLong) tuple.elementAt(1)).intValue();
                typeList.add(new Vector2d(x, y));
            }

            switch(type){
                case X:
                    this.xs = typeList;
                    break;
                case O:
                    this.os = typeList;
                    break;
                case G:
                    this.grays = typeList;
                    break;
            }

    }


    public void parseReply(OtpErlangObject reply){

//        System.out.println(reply);

        OtpErlangTuple replyTuple = (OtpErlangTuple) reply;


        for(Type type: Type.values()){
            try {
                parseType(replyTuple.elementAt(type.ordinal() + 1 ), type);
            } catch (OtpErlangRangeException e) {
                e.printStackTrace();
            }
        }

        OtpErlangString turn = (OtpErlangString) replyTuple.elementAt(4);

        switch (turn.stringValue()){
            case "X":
                this.turn = Type.X;
                break;
            case "O":
                this.turn = Type.O;
                break;
        }

        OtpErlangAtom ended = (OtpErlangAtom) replyTuple.elementAt(5);


        this.gameEnded = ended.booleanValue();

        if(this.gameEnded){
            System.out.println(this.turn.toString() + " lost!");
        }

    }

    public boolean didGameEnd(){
        return this.gameEnded;
    }





}
