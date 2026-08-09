package di.beans.ambiguityxml;

/**
 * Plain POJO -- deliberately NOT annotated with @Component. Both instances of
 * this class are registered purely via XML <bean> definitions (see
 * ambiguity-qualifier-beans.xml), each carrying a nested <qualifier> tag. As
 * learned in the XML-config phase of this study: the <qualifier> tag by itself
 * does nothing without @Qualifier on the Java-side injection point to actually
 * consume it -- see FraudCheckServiceUser below.
 */
public class FraudCheckServiceImpl implements FraudCheckService {

	private final String label;

	public FraudCheckServiceImpl(String label) {
		this.label = label;
	}

	@Override
	public String describe() {
		return "FraudCheckServiceImpl[" + label + "]";
	}

}
