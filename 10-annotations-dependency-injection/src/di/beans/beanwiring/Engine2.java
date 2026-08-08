package di.beans.beanwiring;

/**
 * Deliberately a plain class, not @Component -- it is only ever created via
 * @Bean factory methods in the configs below, so we can observe identity
 * behavior (same instance vs. a freshly constructed one).
 */
public class Engine2 {
    public Engine2() {
        System.out.println("        (a new Engine2 object was just constructed)");
    }
}
