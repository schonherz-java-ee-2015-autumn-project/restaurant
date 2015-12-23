package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class DeliveryDateNotBeforeNowRule implements ValidationRule<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(DeliveryVo object) {
		List<Violation> res = new ArrayList<>();

		if (object.getDeliveryDate() != null && object.getDeliveryDate().before(new Date())) {
			Violation violation = new Violation("wrong_date_before", "");
			res.add(violation);
		}

		return res;
	}

}
