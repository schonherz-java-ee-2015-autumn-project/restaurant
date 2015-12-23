package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class DeliveryDateBeforeOrderDeadlineRule implements ValidationRule<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(DeliveryVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getDeliveryDate() != null && object.getOrders() != null
				&& object.getOrders().stream().anyMatch(o -> o.getDeadline().before(object.getDeliveryDate()))) {
			Violation violation = new Violation("delivery_date_before_deadline", "");
			res.add(violation);
		}

		return res;
	}

}
