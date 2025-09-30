package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class CachingAspect {
    private final Map<CacheKey, Object> cache = new ConcurrentHashMap<>();

    @Around("execution(* service.BookServiceImpl.getBookById(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        CacheKey cacheKey = new CacheKey(method, joinPoint.getArgs());

        Object alreadyExists = cache.get(cacheKey);
        if (alreadyExists != null) {
            return alreadyExists;
        }

        Object result = joinPoint.proceed();

        if (result != null) {
            cache.putIfAbsent(cacheKey, result);
        }

        return result;
    }

    private static final class CacheKey {
        private final Method method;
        private final Object[] args;
        private final int hash;

        public CacheKey(Method method, Object[] args) {
            this.method = method;
            this.args = Arrays.copyOf(args, args.length);
            this.hash = Objects.hash(method, Arrays.deepHashCode(args));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof CacheKey cacheKey)) {
                return false;
            }

            return method.equals(cacheKey.method) && Arrays.deepEquals(this.args, cacheKey.args);
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }
}
