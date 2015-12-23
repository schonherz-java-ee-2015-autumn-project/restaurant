package hu.schonherz.restaurant.web.validation.rules;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.validation.Violation;
import hu.schonherz.restaurant.validation.rule.ValidationRule;

public class DeliveryDateTenMinutesLaterRule implements ValidationRule<DeliveryVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Violation> validate(DeliveryVo object) {
		List<Violation> res = new ArrayList<>();

		Date tenMinutesLater = getTenMinutesLater(new Date());
		if (object.getDeliveryDate() != null && object.getDeliveryDate().before(tenMinutesLater)) {
			Violation violation = new Violation("date_ten_minutes", "");
			res.add(violation);
		}

		return res;
	}

	private Date getTenMinutesLater(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 10);
		return cal.getTime();
	}

}
