package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public class Vertex extends FloatVector {
    
    public static final Vertex EMPTY_VERTEX = new Vertex();
    public static final String PREFIX = "v";
    
    public Vertex() {
        super(4);
    }
    
    public Vertex(String line) throws ObjException {
        this();
        parseLine(line);
    }
    
    @Override
    protected String getPrefix() {
        return PREFIX;
    }
    
}
