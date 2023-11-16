package com.tec02.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class Util {

	@SuppressWarnings("rawtypes")
	public static boolean isNullorEmpty(List list) {
		return list == null || list.isEmpty();
	}

	public static Predicate[] createPredicate(Object filter, CriteriaBuilder cb, Root<?> root) {
		List<Predicate> predicates = createPredicateList(filter, cb, root);
		return predicates.toArray(new Predicate[0]);
	}

	public static List<Predicate> createPredicateList(Object filter, CriteriaBuilder cb, Root<?> root) {
		List<Predicate> predicates = new ArrayList<>();
		JSONObject jsonfilter = ModelMapperUtil.convertValue(filter, JSONObject.class);
		for (String key : jsonfilter.keySet()) {
			if(key.equalsIgnoreCase("finish_time") || key.equalsIgnoreCase("start_time")) {
				continue;
			}
			Object value = jsonfilter.get(key);
			if (value != null) {
				predicates.add(cb.equal(root.get(key), jsonfilter.get(key)));
			}
		}
		return predicates;
	}
	
	public static Set<String> getFields(Class<?> clazz){
		Set<String> rs = new HashSet<>();
		while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                rs.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }
		return rs;
	}
	
	public static List<String> getRetainField(Class<?> clazz, Class<?> root){
		Set<String> fields = getFields(clazz);
		Set<String> rootFields = getFields(root);
		List<String> common = rootFields.stream().filter(fields :: contains).collect(Collectors.toList());
		if(common == null || common.isEmpty()) {
			throw new RuntimeException("root class not contian fields of DTO");
		}
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        List<String> constParam = new ArrayList<>();
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            constParam.clear();
            for (Parameter parameter : parameters) {
            	constParam.add(parameter.getName());
            }
            if(common.containsAll(constParam)) {
            	return constParam;
            }
        }
        throw new RuntimeException("no matching constructor found and one or more arguments did not define alias for bean-injection");
	}
	

	public static boolean isNullorEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public static void checkFilePath(String fileName, String filePath) {
		checkFileName(fileName);
		checkDir(filePath);
	}

	public static void checkFileName(String fileName) {
		if (fileName == null || fileName.isBlank()
				|| !fileName.matches("^(?!\\.|\\s)(?!.*[\\\\/:*?\"<>|]).*(?<!\\.|\\s)$")) {
			throw new RuntimeException("Invalid file name");
		}
	}

	public static String objectToString(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	public static String objectToHearderElements(Object object) {
		try {
			@SuppressWarnings("rawtypes")
			Map map = convertTo(object, Map.class);
			StringBuilder builder = new StringBuilder();
			for (Object key : map.keySet()) {
				Object value = map.get(key);
				if (value == null) {
					continue;
				}
				builder.append(key).append("=").append(value).append(";");
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	public static <T> T convertTo(Object object, Class<T> clazz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.convertValue(object, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
	}

	public static boolean isNewVersionGreaterThanOldVersion(String version, String oldVersion) {
		if (version.equals(oldVersion) || isInvalidVersion(version) || isInvalidVersion(oldVersion)) {
			return false;
		}
		String[] vOldElems = oldVersion.split("\\.");
		String[] vNewElems = version.split("\\.");
		for (int i = 0; i < 3; i++) {
			int nV = Integer.valueOf(vNewElems[i]);
			int oV = Integer.valueOf(vOldElems[i]);
			if (nV > oV) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInvalidVersion(String version) {
		return version == null || !version.matches("^[0-9]+\\.[0-9]+\\.[0-9]+$");
	}

	public static void checkDir(String filePath) {
		if (filePath != null && !filePath.isBlank() && !filePath.matches("^(?!.*[<>:\"|?*]).+$")) {
			throw new RuntimeException("Invalid directory");
		}
	}

	public static String subInLength(String str, int length) {
		if (str == null) {
			return null;
		}
		if (str.length() > length) {
			return str.substring(0, length);
		}
		return str;
	}

	public static String md5File(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		return DigestUtils.md5DigestAsHex(bytes);
	}

	public static String md5File(InputStream inputStream) {
		try {
			return DigestUtils.md5DigestAsHex(inputStream);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String md5File(String filePath) {
		try (FileInputStream input = new FileInputStream(filePath)) {
			return md5File(input);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String instantCvtString(Instant instant, String format) {
		if (instant == null || format == null) {
			return null;
		}
		return new SimpleDateFormat(format).format(Date.from(instant));
	}

	public static Instant stringCvtInstant(String sTime, String... formats) {
		if (sTime == null) {
			return null;
		}
		if (formats == null) {
			return convetToInstant(sTime, "yyyy-MM-dd HH:mm:ss");
		}
		Instant instant;
		for (String format : formats) {
			if ((instant = convetToInstant(sTime, format)) != null) {
				return instant;
			}
		}
		return null;
	}

	private static Instant convetToInstant(String sTime, String format) {
		try {
			return new SimpleDateFormat(format).parse(sTime).toInstant();
		} catch (ParseException e) {
			return null;
		}
	}

	public static Instant longCvtInstant(Long time) {
		try {
			return new Date(time).toInstant();
		} catch (Exception e) {
			return null;
		}
	}
}
