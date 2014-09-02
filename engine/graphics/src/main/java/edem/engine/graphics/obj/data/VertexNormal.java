package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public class VertexNormal extends FloatVector {
    
    public static final VertexNormal EMPTY_VERTEX_NORMAL = new VertexNormal();
    public static final String PREFIX = "vn";
    
    public VertexNormal() {
        super(3);
    }
    
    @Override
    protected String getPrefix() {
        return PREFIX;
    }
    
    public static VertexNormal of(String line) throws ObjException {
        VertexNormal vertexNormal = new VertexNormal();
        vertexNormal.parseLine(line);
        return vertexNormal;
    }
    
}
