package di.beans.valuespel;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:app.properties")
@ComponentScan(basePackages = "di.beans.valuespel")
public class ValueSpelConfig {

}
