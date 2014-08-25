package edem.engine.graphics.obj.data;

public class Vertex extends FloatVector {
    
    public static final Vertex EMPTY_VERTEX = new Vertex();
    
    public Vertex() {
        super("v", 4);
    }

}
