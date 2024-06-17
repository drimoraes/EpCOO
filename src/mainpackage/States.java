package mainpackage;

public enum States {
    INACTIVE(0),
    ACTIVE(1),
    EXPLODING(0);

    private final int value;

    States(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}