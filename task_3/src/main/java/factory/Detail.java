package factory;

public abstract class Detail {
    private String ID;
    public Detail(String ID) {
        this.ID = ID;
    }
    public String getID() {
        return ID;
    }
}
