package hu.schonherz.restaurant.web.vo.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hu.schonherz.restaurant.service.converter.Converter;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import hu.schonherz.restaurant.web.vo.DeliveryListingVo;

@Service("deliveryListingConverter")
public class DeliveryListingConverter implements Converter<DeliveryVo, DeliveryListingVo> {

	private static final long serialVersionUID = 1L;

	@Override
	public DeliveryListingVo convert(DeliveryVo source) {
		if (source == null) {
			return null;
		}
		DeliveryListingVo res = new DeliveryListingVo(source.getGuid(), source.getStatus(), source.getCourierName(),
				source.getNumberOfAddresses(), source.getTotal());
		return res;
	}

	@Override
	public DeliveryVo reverse(DeliveryListingVo source) {
		if (source == null) {
			return null;
		}
		DeliveryVo res = new DeliveryVo(source.getGuid(), source.getStatus(), source.getCourierName(),
				source.getNumberOfAddresses(), source.getTotal());
		return res;
	}

	@Override
	public List<DeliveryListingVo> convert(List<DeliveryVo> sources) {
		List<DeliveryListingVo> res = new ArrayList<>();
		for (DeliveryVo deliveryVo : sources) {
			res.add(convert(deliveryVo));
		}
		return res;
	}

	@Override
	public List<DeliveryVo> reverse(List<DeliveryListingVo> sources) {
		List<DeliveryVo> res = new ArrayList<>();
		for (DeliveryListingVo deliveryListingVo : sources) {
			res.add(reverse(deliveryListingVo));
		}
		return res;
	}

}
