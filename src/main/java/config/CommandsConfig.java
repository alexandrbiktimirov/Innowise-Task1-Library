package config;

import command.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Configuration
@ComponentScan(basePackages = "command")
public class CommandsConfig {
    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

    @Bean
    public Map<Integer, Command> commands(List<Command> commandBeans) {
        return commandBeans.stream().collect(Collectors.toMap(Command::id, c -> c));
    }
}