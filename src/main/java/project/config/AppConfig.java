package project.config;


import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    /*
@Use if we have a LocalDate convert. But not LocalDateTime or LocalTime.
@Format (dd/MM/yyyy) needs to be set up according to input format.
 */
    modelMapper.addConverter(new Converter<String, LocalDate>() {
        @Override
        public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
        return LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    });


        modelMapper.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
                return LocalDateTime.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            }
        });
        modelMapper.addConverter(new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> context) {
                final DateTimeFormatter CUSTOM_FORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime source = context.getSource();
                String formated = source.format(CUSTOM_FORMATER);
                return formated;
            }
        });
    return modelMapper;
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
    }
}
