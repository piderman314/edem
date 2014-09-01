package edem.engine.graphics.obj.data;

public class Vertex extends FloatVector {
    
    public static final Vertex EMPTY_VERTEX = new Vertex();
    public static final String PREFIX = "v";
    
    public Vertex() {
        super(4);
    }
    
    @Override
    protected String getPrefix() {
        return PREFIX;
    }

}
