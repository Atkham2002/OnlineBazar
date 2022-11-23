package uz.nt.firstspring.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor interceptor(){
        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate request) {
                request.header("Authorization", "Bearer " + ScheduleJob.token);
                request.header("Content-Type", "application/json");
                request.header("Accept", "*");
            }
        };

        return interceptor;
//        return request -> {
//            request.header("Authorization", "Bearer " + ScheduleJob.token);
//            request.header("Content-Type", "application/json");
//            request.header("Accept", "*");
//        };
    }
}
