package hu.schonherz.restaurant.validation.rule;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import hu.schonherz.restaurant.validation.Validator;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.ViolationException;

public class RuleValidator<T> implements Validator<T> {

	private static final long serialVersionUID = 1L;

	private Collection<ValidationRule<T>> rules;

	public RuleValidator(Collection<ValidationRule<T>> rules) {
		Validate.notNull(rules);
		Validate.noNullElements(rules);
		this.rules = rules;
	}

	@Override
	public void validate(T object) throws ViolationException {
		Validate.notNull(object);

		List<Violation> violations = new LinkedList<>();
		for (ValidationRule<T> rule : this.rules) {
			violations.addAll(rule.validate(object));
		}

		if (!violations.isEmpty()) {
			throw new ViolationException(violations);
		}
	}

	public Collection<ValidationRule<T>> getRules() {
		return rules;
	}

	public void setRules(Collection<ValidationRule<T>> rules) {
		this.rules = rules;
	}

}
