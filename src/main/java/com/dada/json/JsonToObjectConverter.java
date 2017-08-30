package com.dada.json;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonToObjectConverter {	

	private static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}
	
	public static <T> T jsonToObject(String outPut, Class<T> destinationClass) throws JsonParseException, JsonMappingException, IOException  {
		T t = mapper.readValue(outPut, destinationClass);
		return t;
	}
	public static <T> List<T> jsonToList(String outPut, Class<T> destinationClass) throws JsonParseException, JsonMappingException, IOException {
		List<T> list = null;
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, destinationClass);
		list = mapper.readValue(outPut, type);
		return list;
	}
	public static <T> Set<T> jsonToSet(String outPut, Class<T> destinationClass) throws JsonParseException, JsonMappingException, IOException {
		Set<T> set = null;
		JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, destinationClass);
		set = mapper.readValue(outPut, type);
		return set;
	}
	public static <S, T> S jsonToParametricObject(String outPut, Class<S> destinationClass, Class<T> parameterType) throws JsonParseException, JsonMappingException, IOException {
		JavaType parametricType= mapper.getTypeFactory().constructParametricType(destinationClass, parameterType);
		return mapper.readValue(outPut, parametricType);
	}
	public static <S, T> S jsonToParametricObject(File file, Class<S> destinationClass, Class<T> parameterType) throws JsonParseException, JsonMappingException, IOException {
		JavaType parametricType= mapper.getTypeFactory().constructParametricType(destinationClass, parameterType);
		return mapper.readValue(file, parametricType);
	}
	public static <S, C, T> S jsonToParametricObjectCollection(String outPut, Class<S> destinationClass, Class<C> collectionClass, Class<T> parameterType) throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		CollectionType subJT = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) collectionClass, parameterType);
        JavaType parametricType= mapper.getTypeFactory().constructParametricType(destinationClass, subJT);
		return mapper.readValue(outPut, parametricType);
	}
	public static <S, C, T> S jsonToParametricObjectCollection(File file, Class<S> destinationClass, Class<C> collectionClass, Class<T> parameterType) throws JsonParseException, JsonMappingException, IOException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		CollectionType subJT = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) collectionClass, parameterType);
        JavaType parametricType= mapper.getTypeFactory().constructParametricType(destinationClass, subJT);
		return mapper.readValue(file, parametricType);
	}
	
	public static void writeToFile(File file, Object object) throws JsonGenerationException, JsonMappingException, IOException {
        mapper.writeValue(file, object);
    }
	
	public static <T> T readFromFile(File file, Class<T> destinationClass) throws JsonGenerationException, JsonMappingException, IOException {
        return mapper.readValue(file, destinationClass);
    }
    
    public static <K,V> Map<K,V> jsonToMap(String outPut, Class<K> keyClass, Class<V> valueClass) throws JsonParseException, JsonMappingException, IOException {
        JavaType type = mapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
        return mapper.readValue(outPut, type);
    }
    public static <K,V> Map<K,V> jsonToMap(File jsonFile, Class<K> keyClass, Class<V> valueClass) throws JsonParseException, JsonMappingException, IOException {
        JavaType type = mapper.getTypeFactory().constructMapType(ConcurrentHashMap.class, keyClass, valueClass);
        return mapper.readValue(jsonFile, type);
    }
    
    public static <K,T> Map<K,List<T>> jsonToMapWithListValue(File jsonFile, Class<K> keyClass, Class<T> parameterType) throws JsonParseException, JsonMappingException, IOException {
		CollectionType subJT = mapper.getTypeFactory().constructCollectionType(List.class, parameterType);
    	JavaType keyType= mapper.getTypeFactory().constructType(keyClass);
        JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, keyType, subJT);
        return mapper.readValue(jsonFile, type);
    }
}