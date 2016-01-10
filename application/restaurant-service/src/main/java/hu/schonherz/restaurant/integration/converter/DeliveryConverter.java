package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteCargoDTO;
import hu.schonherz.restaurant.service.vo.DeliveryVo;

public class DeliveryConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemoteCargoDTO toRemote(DeliveryVo vo) {
		if (vo == null) {
			return null;
		}
		RemoteCargoDTO res = new RemoteCargoDTO();

		res.setId(vo.getGlobalId());
		res.setRestaurantId(vo.getRestaurant().getGlobalId());
		res.getOrders().addAll(OrderConverter.toRemote(vo.getOrders()));
		res.setState(DeliveryStateConverter.toRemote(vo.getDeliveryState()));
		res.setCourierId(null);
		res.setIsDeleted(vo.getIsDeleted());

		return res;
	}

	public static List<RemoteCargoDTO> toRemote(List<DeliveryVo> vos) {
		List<RemoteCargoDTO> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (DeliveryVo delivery : vos) {
				res.add(toRemote(delivery));
			}
		}

		return res;
	}

	public static DeliveryVo toLocal(RemoteCargoDTO rdto) {
		if (rdto == null) {
			return null;
		}

		DeliveryVo res = new DeliveryVo();
		res.setIsDeleted(rdto.isIsDeleted());
		res.setCourier(rdto.getCourierName());
		res.setDeliveryState(DeliveryStateConverter.toLocal(rdto.getState()));
		res.setGlobalId(rdto.getId());
		res.setOrders(OrderConverter.toLocal(rdto.getOrders()));
		return res;
	}

	public static List<DeliveryVo> toLocal(List<RemoteCargoDTO> vos) {
		List<DeliveryVo> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (RemoteCargoDTO cargo : vos) {
				res.add(toLocal(cargo));
			}
		}

		return res;
	}

}
