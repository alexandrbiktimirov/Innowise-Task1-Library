import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"aop", "config", "controller", "i18n", "repository", "service", "command", "mapper"})
public class AppConfig {

}
