package br.com.erudio.config;

import br.com.erudio.service.CourseService;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ToolConfig {

    @Bean
    public List<ToolCallback> erudioTools(CourseService service) {
        return List.of(ToolCallbacks.from(service));
    }
}
