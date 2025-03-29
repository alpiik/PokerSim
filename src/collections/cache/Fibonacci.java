package collections.cache;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private Map<Integer, Integer> cache = new HashMap<>();

    public Integer fib(Integer n) {
        if (n <= 1) {
            return n;
        }
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        int result = fib(n - 1) + fib(n - 2);
        cache.put(n, result);

        return result;
    }
}