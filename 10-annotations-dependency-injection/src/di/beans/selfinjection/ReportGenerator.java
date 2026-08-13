package di.beans.selfinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Demonstrates Spring's documented SELF-INJECTION fallback: "@Autowired also
 * considers self references for injection (that is, references back to the bean
 * that is currently injected)." Per the docs, this is meant as a last resort --
 * typically to call another method on the same instance THROUGH the bean's AOP
 * proxy (for example, a @Transactional-advised method), so that the proxy's
 * advice actually runs, which a plain `this.method()` call would bypass.
 *
 * This class does NOT set up any real AOP/@Transactional proxying -- that is
 * intentionally out of scope for this project (see README). Because there is no
 * proxy in play here, `self` below will simply be the SAME plain instance as
 * `this` -- Main07 prints identity comparisons to prove that directly.
 */
@Component
public class ReportGenerator {

	@Autowired
	private ReportGenerator self;

	public void generateReport() {
		System.out.println("    [ReportGenerator] generateReport() executing");
	}

	public void generateReportViaSelf() {
		System.out.println("    [ReportGenerator] generateReportViaSelf() -- " + "about to call self.generateReport()");
		self.generateReport();
	}

	/**
	 * Exposed purely so Main07 can compare identity against the context-managed
	 * bean.
	 */
	public ReportGenerator getSelfReference() {
		return self;
	}

}
