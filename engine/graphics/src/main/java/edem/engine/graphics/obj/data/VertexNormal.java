package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public class VertexNormal extends FloatVector {
    
    public static final VertexNormal EMPTY_VERTEX_NORMAL = new VertexNormal();
    public static final String PREFIX = "vn";
    
    public VertexNormal() {
        super(3);
    }
        
    public VertexNormal(String line) throws ObjException {
        this();
        parseLine(line);
    }
    
    @Override
    protected String getPrefix() {
        return PREFIX;
    }
    
}
