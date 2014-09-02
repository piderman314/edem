package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

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
    
    public static Vertex of(String line) throws ObjException {
        Vertex vertex = new Vertex();
        vertex.parseLine(line);
        return vertex;
    }

}
