package edem.engine.graphics.obj.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edem.engine.graphics.obj.ObjException;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;

@Getter
public class Face implements ParseableLine {

    private List<Indices> indicesList;
    
    @Override
    public String getPrefix() {
        return "f";
    }

    @Override
    public void parseLine(String line) throws ObjException {
        if (StringUtils.isBlank(line)) {
            throw new ObjException("Cannot parse empty line");
        }
        
        String[] values = line.trim().split("\\s");
        
        if (values.length < 4) {
            throw new ObjException("Expected at least 3 vertices");
        }
        
        if (!StringUtils.equals(values[0], getPrefix())) {
            throw new ObjException("Got a " + values[0] + " line, but expected a " + getPrefix() + " line");
        }
        
        List<String> indices = Arrays.asList(values).subList(1, values.length);
        
        indicesList = indices
                .stream()
                .map(StringUtils::trim)
                .map(string -> string.split("/"))
                .map(Face::parseIndices)
                .collect(Collectors.toList());
    }
    
    private static Indices parseIndices(String[] indices) {
        if (indices.length == 1) {
            return Indices.of(parseIndex(indices[0]), 0, 0);
        }
        
        if (indices.length == 2) {
            return Indices.of(parseIndex(indices[0]), parseIndex(indices[1]), 0);
        }
        
        return Indices.of(parseIndex(indices[0]), parseIndex(indices[1]), parseIndex(indices[2]));
    }
    
    private static int parseIndex(String index) {
        if (StringUtils.isBlank(index)) {
            return 0;
        }
        
        return Integer.parseInt(index);
    }

    @Data
    @RequiredArgsConstructor(staticName = "of")
    public static class Indices {
        
        private final int vertexIndex;
        private final int vertexTextureIndex;
        private final int vertexNormalIndex;
        
    }

}
