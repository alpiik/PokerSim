package reflection.tester;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestRunner {

    private final List<String> results = new ArrayList<>();

    public void runTests(List<String> testClassNames, String... tagsToRun) {
        Set<String> specifiedTags = tagsToRun.length > 0
                ? Arrays.stream(tagsToRun).collect(Collectors.toSet())
                : null;

        for (String className : testClassNames) {
            runTestClass(className, specifiedTags);
        }

        Collections.sort(results);
    }

    private void runTestClass(String className, Set<String> specifiedTags) {
        try {
            Class<?> testClass = Class.forName(className);
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            List<Method> methods = Arrays.asList(testClass.getDeclaredMethods());
            methods.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));

            for (Method method : methods) {
                if (method.isAnnotationPresent(MyTest.class)) {
                    runTestMethod(testInstance, method, specifiedTags);
                }
            }

        } catch (ClassNotFoundException e) {
            results.add("Class " + className + " - FAILED (Not Found)");
        } catch (ReflectiveOperationException e) {
            results.add("Class " + className + " - FAILED (Cannot create instance or constructor issue): " + e.getMessage());
        }
    }

    private void runTestMethod(Object testInstance, Method method, Set<String> specifiedTags) {
        MyTest myTestAnnotation = method.getAnnotation(MyTest.class);
        String methodName = method.getName() + "()";

        boolean shouldRun = specifiedTags == null ||
                Arrays.stream(myTestAnnotation.tags()).anyMatch(specifiedTags::contains);

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

    public String getResult() {
        return String.join("\n", results);
    }

    public static void assertThrows(Runnable code, Class<? extends Exception> expected) {
        try {
            code.run();
            throw new AssertionError("Expected " + expected.getSimpleName() + " to be thrown, but nothing was thrown.");
        } catch (Exception actualException) {
            if (!expected.isInstance(actualException)) {
                throw new AssertionError("Expected " + expected.getSimpleName() + " but caught " + actualException.getClass().getSimpleName(), actualException);
            }
        }
    }
}
