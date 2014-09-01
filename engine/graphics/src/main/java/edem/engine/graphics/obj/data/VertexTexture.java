package edem.engine.graphics.obj.data;

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
    
}
