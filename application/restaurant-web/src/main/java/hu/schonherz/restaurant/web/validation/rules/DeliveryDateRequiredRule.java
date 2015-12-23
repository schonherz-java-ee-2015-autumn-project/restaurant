package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class DeliveryDateRequiredRule implements ValidationRule<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(DeliveryVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getDeliveryDate() == null) {
			Violation v = new Violation("delivery_date_required", "");
			res.add(v);
		}

		return res;
	}

}
