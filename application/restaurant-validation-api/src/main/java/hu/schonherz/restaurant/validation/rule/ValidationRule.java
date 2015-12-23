package hu.schonherz.restaurant.validation.rule;

import java.util.List;

import hu.schonherz.restaurant.validation.Violation;

public interface ValidationRule<T> {

	List<Violation> validate(T object);

}
