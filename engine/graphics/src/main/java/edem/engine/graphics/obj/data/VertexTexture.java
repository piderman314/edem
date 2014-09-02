package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public class VertexTexture extends FloatVector {
    
    public static final VertexTexture EMPTY_VERTEX_TEXTURE = new VertexTexture();
    public static final String PREFIX = "vt";
    
    public VertexTexture() {
        super(3);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }
    
    public static VertexTexture of(String line) throws ObjException {
        VertexTexture vertexTexture = new VertexTexture();
        vertexTexture.parseLine(line);
        return vertexTexture;
    }
    
}
