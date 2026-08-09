package di.beans.optional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Empirically confirms WHERE java.util.Optional<T> is valid as a non-required
 * expression: the Spring reference docs only show it on a setter parameter, so
 * this class deliberately tries it on a FIELD, a SETTER, and a CONSTRUCTOR
 * PARAMETER, to verify all three actually work rather than assuming from the
 * docs' single example.
 */
@Component
public class OptionalDemoBean {

	@Autowired
	private Optional<PremiumSupport> premiumSupport; // Optional on a FIELD

	private Optional<LoyaltyProgram> loyaltyProgram; // Optional on a SETTER parameter

	private final Optional<ConciergeService> conciergeService; // Optional on a CONSTRUCTOR parameter

	@Autowired
	public OptionalDemoBean(Optional<ConciergeService> conciergeService) {
		this.conciergeService = conciergeService;
	}

	@Autowired
	public void setLoyaltyProgram(Optional<LoyaltyProgram> loyaltyProgram) {
		this.loyaltyProgram = loyaltyProgram;
	}

	public void printStatus() {
		System.out.println("    [OptionalDemoBean] premiumSupport   (FIELD)      isPresent="
				+ premiumSupport.isPresent() + "  (expected: false, no bean registered)");
		System.out.println("    [OptionalDemoBean] loyaltyProgram   (SETTER)     isPresent="
				+ loyaltyProgram.isPresent() + "   (expected: true -> "
				+ (loyaltyProgram.isPresent() ? loyaltyProgram.get().describe() : "n/a") + ")");
		System.out.println("    [OptionalDemoBean] conciergeService (CTOR arg)   isPresent="
				+ conciergeService.isPresent() + "  (expected: false, no bean registered)");
	}

}
