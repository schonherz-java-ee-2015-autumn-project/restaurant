package hu.schonherz.restaurant.web.validation;

import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.schonherz.restaurant.service.vo.ItemVo;
import hu.schonherz.restaurant.validation.rule.RuleValidator;
import hu.schonherz.restaurant.web.validation.rules.ItemProductCannotBeNullRule;
import hu.schonherz.restaurant.web.validation.rules.ItemQuantityMustBePositiveRule;

@ViewScoped
@ManagedBean(name = "itemValidator")
public class ItemValidator extends RuleValidator<ItemVo> {

	private static final long serialVersionUID = 1L;

	public ItemValidator() {
		super(Arrays.asList(new ItemProductCannotBeNullRule(), new ItemQuantityMustBePositiveRule()));
	}

}
