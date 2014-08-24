package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class FloatVector implements ParseableLine {

    private static final float DEFAULT_W_VALUE = 1.0f;
    
    private float x;
    private float y;
    private float z;
    private float w;
    
    private final String prefix;
    
    @Override
    public void parseLine(String line) throws ObjException {
        if (StringUtils.isBlank(line)) {
            throw new ObjException("Cannot parse empty line");
        }
        
        String[] values = line.trim().split("\\s");
        
        if (values.length < 4 || values.length > 5) {
            throw new ObjException("Expected 3 or 4 vertices/weights");
        }
        
        if (!StringUtils.equals(values[0], prefix)) {
            throw new ObjException("Got a " + values[0] + " line, but expected a " + getPrefix() + " line");
        }
        
        x = Float.parseFloat(values[1]);
        y = Float.parseFloat(values[2]);
        z = Float.parseFloat(values[3]);
        
        if (values.length == 5) {
            w = Float.parseFloat(values[4]);
        } else {
            w = DEFAULT_W_VALUE;
        }
    }
    
}
