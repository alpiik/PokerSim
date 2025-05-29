package reflection.tester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections; // Import Collections for sorting
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestRunner {

    private final List<String> results = new ArrayList<>();

    public void runTests(List<String> testClassNames, String... tagsToRun) {
        Set<String> specifiedTags = tagsToRun.length > 0 ?
                Arrays.stream(tagsToRun).collect(Collectors.toSet()) : null;

        for (String className : testClassNames) {
            try {
                Class<?> testClass = Class.forName(className);
                Object testInstance = testClass.getDeclaredConstructor().newInstance();

                List<Method> methods = Arrays.asList(testClass.getDeclaredMethods());
                methods.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));

                for (Method method : methods) {
                    if (method.isAnnotationPresent(MyTest.class)) {
                        MyTest myTestAnnotation = method.getAnnotation(MyTest.class);
                        String methodName = method.getName() + "()";
                        boolean shouldRun = true;

                        if (specifiedTags != null) {
                            Set<String> methodTags = Arrays.stream(myTestAnnotation.tags()).collect(Collectors.toSet());
                            shouldRun = methodTags.stream().anyMatch(specifiedTags::contains);
                        }

                        if (shouldRun) {
                            try {
                                method.invoke(testInstance);
                                results.add(methodName + " - OK");
                            } catch (InvocationTargetException e) {

                                results.add(methodName + " - FAILED");
                            } catch (IllegalAccessException e) {

                                results.add(methodName + " - ERROR (Access Issue)");
                            }
                        } else {
                            results.add(methodName + " - SKIPPED");
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                results.add("Class " + className + " - FAILED (Not Found)");
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                results.add("Class " + className + " - FAILED (Cannot create instance or constructor issue): " + e.getMessage());
            }
        }
        Collections.sort(results);
    }

    public String getResult() {
        return String.join("\n", results);
    }

    public static void assertThrows(Runnable code, Class<? extends Exception> expected) {
        try {
            code.run();
            throw new AssertionError("Expected " + expected.getSimpleName() + " to be thrown, but nothing was thrown.");
        } catch (Throwable actualException) {
            if (!expected.isInstance(actualException)) {
                throw new AssertionError("Expected " + expected.getSimpleName() + " but caught " + actualException.getClass().getSimpleName(), actualException);
            }
        }
    }
}