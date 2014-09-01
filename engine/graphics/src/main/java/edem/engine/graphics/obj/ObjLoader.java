package edem.engine.graphics.obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edem.engine.graphics.model.Model;
import edem.engine.graphics.obj.data.Face;
import edem.engine.graphics.obj.data.Vertex;
import edem.engine.graphics.obj.data.VertexNormal;
import edem.engine.graphics.obj.data.VertexTexture;

import org.apache.commons.lang3.StringUtils;

public final class ObjLoader {
    
    private ObjLoader() {
        // NOOP
    }

    public static Model parseToModel(String resource) throws IOException, ObjException {
        try (InputStream resourceInputStream = ObjLoader.class.getResourceAsStream(resource)) {
            return parseToModel(resourceInputStream);
        }
    }
    
    public static Model parseToModel(InputStream inputStream) throws IOException, ObjException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            Map<String, List<String>> lines = 
            bufferedReader
                    .lines()
                    .filter(string -> StringUtils.isNotBlank(string))
                    .collect(Collectors.groupingBy(string -> string.trim().split("\\s+")[0]));
            
            List<Vertex> vertexList = null;
            List<VertexTexture> vertexTextureList = null;
            List<VertexNormal> vertexNormalList = null;
            List<Face> faceList = null;
            
            for (Entry<String, List<String>> lineGroup : lines.entrySet()) {
                switch (lineGroup.getKey()) {
                    case Vertex.PREFIX:
                        vertexList = new ArrayList<>();
                        for (String line : lineGroup.getValue()) {
                            Vertex vertex = new Vertex();
                            vertex.parseLine(line);
                            vertexList.add(vertex);
                        }
                    break;
                    case VertexTexture.PREFIX:
                        vertexTextureList = new ArrayList<>();
                        for (String line : lineGroup.getValue()) {
                            VertexTexture vertexTexture = new VertexTexture();
                            vertexTexture.parseLine(line);
                            vertexTextureList.add(vertexTexture);
                        }
                    break;
                    case VertexNormal.PREFIX:
                        vertexNormalList = new ArrayList<>();
                        for(String line : lineGroup.getValue()) {
                            VertexNormal vertexNormal = new VertexNormal();
                            vertexNormal.parseLine(line);
                            vertexNormalList.add(vertexNormal);
                        }
                    break;
                    case Face.PREFIX:
                        faceList = new ArrayList<>();
                        for (String line : lineGroup.getValue()) {
                            Face face = new Face();
                            face.parseLine(line);
                            faceList.add(face);
                        }
                    break;
                    default:
                        break;
                }
            }
            
            return new Model(vertexList, vertexTextureList, vertexNormalList, faceList);
        }
    }
    
}
