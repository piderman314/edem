package edem.engine.graphics.obj.data;

import edem.engine.graphics.obj.ObjException;

public interface ParseableLine {

    void parseLine(String line) throws ObjException;
    
}
