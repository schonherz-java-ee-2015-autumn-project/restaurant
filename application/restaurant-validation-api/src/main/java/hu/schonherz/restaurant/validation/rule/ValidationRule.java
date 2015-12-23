package hu.schonherz.restaurant.validation.rule;

import java.io.Serializable;
import java.util.List;

import hu.schonherz.restaurant.validation.Violation;

public interface ValidationRule<T> extends Serializable {

	List<Violation> validate(T object);

}
