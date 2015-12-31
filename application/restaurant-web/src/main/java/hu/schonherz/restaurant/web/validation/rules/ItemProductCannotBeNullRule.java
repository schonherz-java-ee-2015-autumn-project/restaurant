package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.List;

import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class ItemProductCannotBeNullRule implements ValidationRule<ItemVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(ItemVo object) {
		List<Violation> res = new ArrayList<Violation>();

		if (object.getProduct() == null) {
			Violation violation = new Violation("item_product_cannot_be_null", "");
			res.add(violation);
		}

		return res;
	}

}
