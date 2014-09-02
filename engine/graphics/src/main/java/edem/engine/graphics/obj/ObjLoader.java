package edem.engine.graphics.obj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import edem.engine.graphics.model.Model;
import edem.engine.graphics.obj.data.Face;
import edem.engine.graphics.obj.data.Vertex;
import edem.engine.graphics.obj.data.VertexNormal;
import edem.engine.graphics.obj.data.VertexTexture;
import edem.util.function.FunctionUtil;
import edem.util.function.ResultOrException;

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
                        Collection<ResultOrException<Vertex, ObjException>> vertices = lineGroup.getValue().stream()
                                .map(FunctionUtil.wrapException(Vertex::of))
                                .collect(Collectors.toList());
                        vertexList = new ArrayList<>(FunctionUtil.<Vertex, ObjException>unwrap(vertices));
                    break;
                    case VertexTexture.PREFIX:
                        Collection<ResultOrException<VertexTexture, ObjException>> vertexTextures = lineGroup.getValue().stream()
                                .map(FunctionUtil.wrapException(VertexTexture::of))
                                .collect(Collectors.toList());
                        vertexTextureList = new ArrayList<>(FunctionUtil.<VertexTexture, ObjException>unwrap(vertexTextures));
                    break;
                    case VertexNormal.PREFIX:
                        Collection<ResultOrException<VertexNormal, ObjException>> vertexNormals = lineGroup.getValue().stream()
                                .map(FunctionUtil.wrapException(VertexNormal::of))
                                .collect(Collectors.toList());
                        vertexNormalList = new ArrayList<>(FunctionUtil.<VertexNormal, ObjException>unwrap(vertexNormals));
                    break;
                    case Face.PREFIX:
                        Collection<ResultOrException<Face, ObjException>> faces = lineGroup.getValue().stream()
                                .map(FunctionUtil.wrapException(Face::of))
                                .collect(Collectors.toList());
                        faceList = new ArrayList<>(FunctionUtil.<Face, ObjException>unwrap(faces));
                    break;
                    default:
                        break;
                }
            }
            
            return new Model(vertexList, vertexTextureList, vertexNormalList, faceList);
        }
    }
    
}
