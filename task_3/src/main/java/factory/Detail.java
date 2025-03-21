package factory;

public abstract class Detail {
    private final String ID;
    Detail(String ID){
        this.ID = ID;
    }
    abstract public String getID();
}
