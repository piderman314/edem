package edem.engine.graphics.obj.data;

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
    
}
