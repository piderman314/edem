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
import edem.util.function.FunctionUtil.ThrowingFunction;
import edem.util.function.ResultOrException;

import org.apache.commons.lang3.StringUtils;

public final class ObjParser {
    
    private ObjParser() {
        // NOOP
    }

    public static Model parseToModel(String resource) throws  ObjException {
        try (InputStream resourceInputStream = ObjParser.class.getResourceAsStream(resource)) {
            return parseToModel(resourceInputStream);
        } catch (IOException ioe) {
            throw new ObjException("Error while parsing to model: ", ioe);
        }
    }
    
    public static Model parseToModel(InputStream inputStream) throws ObjException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
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
                        vertexList = parseLines(lineGroup.getValue(), Vertex::new);
                    break;
                    case VertexTexture.PREFIX:
                        vertexTextureList = parseLines(lineGroup.getValue(), VertexTexture::new);
                    break;
                    case VertexNormal.PREFIX:
                        vertexNormalList = parseLines(lineGroup.getValue(), VertexNormal::new);
                    break;
                    case Face.PREFIX:
                        faceList = parseLines(lineGroup.getValue(), Face::new);
                    break;
                    default:
                        break;
                }
            }
            
            return new Model(vertexList, vertexTextureList, vertexNormalList, faceList);
        } catch (IOException ioe) {
            throw new ObjException("Error while parsing to model: ", ioe);
        }
    }
    
    private static <RESULT> List<RESULT> parseLines(List<String> lines, ThrowingFunction<String, RESULT, ObjException> parse) throws ObjException {
        Collection<ResultOrException<RESULT, ObjException>> parsedLines = 
                lines
                .stream()
                .map(FunctionUtil.wrapException(parse))
                .collect(Collectors.toList());
        
        return new ArrayList<>(FunctionUtil.<RESULT, ObjException> unwrap(parsedLines));
    }
    
}
