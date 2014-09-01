package edem.engine.graphics.obj;

import java.io.IOException;

import edem.engine.graphics.model.Model;

import org.testng.annotations.Test;

@Test
public class ObjLoaderTest {

    public void testLoadCube() throws IOException, ObjException {
        // when
        Model model = ObjLoader.parseToModel("/edem/engine/graphics/obj/cube.obj");
    }
    
}
