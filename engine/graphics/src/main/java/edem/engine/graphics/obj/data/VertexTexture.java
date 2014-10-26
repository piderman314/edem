package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public class VertexTexture extends FloatVector {
    
    public static final VertexTexture EMPTY_VERTEX_TEXTURE = new VertexTexture();
    public static final String PREFIX = "vt";
    
    public VertexTexture() {
        super(3);
    }
    
    public VertexTexture(String line) throws ObjException {
        this();
        parseLine(line);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }
    
}
