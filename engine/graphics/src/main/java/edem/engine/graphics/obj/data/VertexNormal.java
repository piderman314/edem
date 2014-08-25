package edem.engine.graphics.obj.data;

public class VertexNormal extends FloatVector {
    
    public static final VertexNormal EMPTY_VERTEX_NORMAL = new VertexNormal();
    
    public VertexNormal() {
        super("vn", 3);
    }

}
