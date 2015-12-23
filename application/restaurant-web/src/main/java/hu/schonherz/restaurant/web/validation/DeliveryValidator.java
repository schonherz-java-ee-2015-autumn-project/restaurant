package hu.schonherz.restaurant.web.validation;

import java.util.Arrays;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.rule.RuleValidator;
import hu.schonherz.restaurant.web.validation.rules.DeliveryDateBeforeOrderDeadlineRule;
import hu.schonherz.restaurant.web.validation.rules.DeliveryDateNotBeforeNowRule;
import hu.schonherz.restaurant.web.validation.rules.DeliveryDateRequiredRule;
import hu.schonherz.restaurant.web.validation.rules.DeliveryDateTenMinutesLaterRule;
import hu.schonherz.restaurant.web.validation.rules.DeliveryOrdersNotEmptyRule;

@ViewScoped
@ManagedBean(name = "deliveryValidator")
public class DeliveryValidator extends RuleValidator<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	public DeliveryValidator() {
		super(Arrays.asList(new DeliveryDateRequiredRule(), new DeliveryDateNotBeforeNowRule(),
				new DeliveryDateTenMinutesLaterRule(), new DeliveryDateBeforeOrderDeadlineRule(),
				new DeliveryOrdersNotEmptyRule()));
	}

}
