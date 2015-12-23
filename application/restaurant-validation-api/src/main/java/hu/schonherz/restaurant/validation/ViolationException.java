package hu.schonherz.restaurant.validation;

import java.util.List;

import org.apache.commons.lang3.Validate;

public class ViolationException extends Exception {

	private static final long serialVersionUID = 1L;

	private List<Violation> violations;

	public ViolationException(List<Violation> violations) {
		Validate.notNull(violations, "Violations must be specified when using ViolationException");
		Validate.noNullElements(violations);

		this.violations = violations;
	}

	public List<Violation> getViolations() {
		return violations;
	}
}
