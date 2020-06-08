package jv.util;

public enum Type {
    X,
    O,
    G,
    N;

    @Override
    public String toString() {
        switch (this){
            case X:
                return "Blue";
            case O:
                return "Red";
            default:
                return "Undefined";
        }
    }
}
