import aop.Cached;
import aop.CachingAspect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

public class CachingAspectTests {
    public static class Book {
        private final int id;
        public Book(int id) {
            this.id = id;
        }
    }

    public interface Service {
        Book getBook(int id);
    }

    public static class ServiceImpl implements Service {
        int calls = 0;

        @Override
        @Cached
        public Book getBook(int id) {
            calls++;
            return new Book(id);
        }
    }

    @Test
    public void sameArguments_returnSameCache() {
        var target = new ServiceImpl();

        var proxyFactory = new AspectJProxyFactory(target);
        proxyFactory.addAspect(new CachingAspect());

        var proxy = (Service) proxyFactory.getProxy();

        proxy.getBook(1);
        proxy.getBook(1);

        Assertions.assertEquals(1, target.calls);
    }

    @Test
    public void differentArguments_returnDifferentCache() {
        var target = new ServiceImpl();

        var proxyFactory = new AspectJProxyFactory(target);
        proxyFactory.addAspect(new CachingAspect());

        var proxy = (Service) proxyFactory.getProxy();

        proxy.getBook(1);
        proxy.getBook(2);

        Assertions.assertEquals(2, target.calls);
    }
}
