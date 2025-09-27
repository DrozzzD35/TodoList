package TaskServer;

public enum Identity {
    IDENTITY;
    private int id = 1;

    public int createId() {
        return id++;
    }

}
