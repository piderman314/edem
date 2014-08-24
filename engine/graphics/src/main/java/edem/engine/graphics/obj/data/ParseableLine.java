package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public interface ParseableLine {

    String getPrefix();
    void parseLine(String line) throws ObjException;
    
}
