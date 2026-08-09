package di.beans.ambiguityxml;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Bridges XML-declared beans (fastFraudCheck / thoroughFraudCheck, from
 * ambiguity-qualifier-beans.xml) into an otherwise annotation-driven
 * context, alongside the @Component-scanned FraudCheckServiceUser.
 */
@Configuration
@ComponentScan(basePackages = "di.beans.ambiguityxml")
@ImportResource("classpath:ambiguity-qualifier-beans.xml")
public class XmlQualifierConfig {
}
