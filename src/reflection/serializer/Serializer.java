package reflection.serializer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Serializer {

    private static final String SEPARATOR_FIELD = "|";
    private static final String SEPARATOR_KEY_VALUE = ":";

    private static final String ENCODED_PERCENT = "%25";
    private static final String ENCODED_COLON = "%3a";
    private static final String ENCODED_PIPE = "%7c";
    private static final String PERCENT = "%";
    private static final String COLON = ":";
    private static final String PIPE = "|";


    public String serialize(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();

        List<String> pairs = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Stored.class)) {
                field.setAccessible(true);
                try {
                    String fieldName = field.getAnnotation(Stored.class).value();
                    if (fieldName.isEmpty()) {
                        fieldName = field.getName();
                    }

                    Object value = field.get(instance);
                    String stringValue = (value == null) ? "null" : value.toString();

                    stringValue = stringValue.replace(PERCENT, ENCODED_PERCENT);
                    stringValue = stringValue.replace(COLON, ENCODED_COLON);
                    stringValue = stringValue.replace(PIPE, ENCODED_PIPE);

                    pairs.add(fieldName + SEPARATOR_KEY_VALUE + stringValue);
                } catch (IllegalAccessException e) {
                    System.err.println("Error accessing field " + field.getName() + ": " + e.getMessage());
                }
            }
        }
        return String.join(SEPARATOR_FIELD, pairs);
    }

    public <T> T deserialize(String inputString,
                             Class<T> clazz) {
        T instance = null;
        try {
            instance = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Error creating new instance of " + clazz.getName() + ". Ensure a no-argument constructor exists.", e);
        }

        Map<String, String> dataMap = new HashMap<>();
        String[] fieldPairs = inputString.split(Pattern.quote(SEPARATOR_FIELD));

        for (String pair : fieldPairs) {
            String[] parts = pair.split(SEPARATOR_KEY_VALUE, 2);
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];

                value = value.replace(ENCODED_PIPE, PIPE);
                value = value.replace(ENCODED_COLON, COLON);
                value = value.replace(ENCODED_PERCENT, PERCENT);

                dataMap.put(key, value);
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Stored.class)) {
                field.setAccessible(true);

                String fieldName = field.getAnnotation(Stored.class).value();
                if (fieldName.isEmpty()) {
                    fieldName = field.getName();
                }

                if (dataMap.containsKey(fieldName)) {
                    String stringValue = dataMap.get(fieldName);

                    try {

                        if (field.getType() == int.class || field.getType() == Integer.class) {
                            field.set(instance, Integer.parseInt(stringValue));
                        } else if (field.getType() == String.class) {
                            field.set(instance, stringValue);
                        }
                    } catch (IllegalAccessException e) {
                        System.err.println("Error setting field " + field.getName() + ": " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsing number for field " + field.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
        return instance;
    }
}
