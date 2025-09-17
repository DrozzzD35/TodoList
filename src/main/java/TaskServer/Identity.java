package TaskServer;

public enum Identity {
    IDENTITY;
    private int id;

    public int createId() {
        return id++;
    }

}
