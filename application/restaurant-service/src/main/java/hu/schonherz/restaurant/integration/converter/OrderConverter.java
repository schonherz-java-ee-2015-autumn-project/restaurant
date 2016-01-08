package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import hu.schonherz.administrator.RemoteOrderDTO;
import hu.schonherz.restaurant.service.vo.OrderVo;

public class OrderConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemoteOrderDTO toRemote(OrderVo vo) {
		if (vo == null) {
			return null;
		}

		RemoteOrderDTO res = new RemoteOrderDTO();

		res.setAddressToDeliver(vo.getAddress());
		res.setDeliveryState(OrderStateConverter.toRemote(vo.getOrderState()));
		res.setFullCost(vo.getTotalPrice());
		res.setId(vo.getGlobalId());
		res.setPayment(PayTypeConverter.toRemote(vo.getPayType()));
		res.setDeadline(toGregorianXml(vo.getDeadline()));
		res.getItems().addAll(ItemConverter.toRemote(vo.getItems()));

		return res;
	}

	public static List<RemoteOrderDTO> toRemote(List<OrderVo> vos) {
		List<RemoteOrderDTO> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (OrderVo order : vos) {
				res.add(toRemote(order));
			}
		}

		return res;
	}

	public static OrderVo toLocal(RemoteOrderDTO rdto) {
		if (rdto == null) {
			return null;
		}

		OrderVo res = new OrderVo();
		res.setAddress(rdto.getAddressToDeliver());
		res.setDeadline(rdto.getDeadline().toGregorianCalendar().getTime());
		res.setGlobalId(rdto.getId());
		res.setItems(ItemConverter.toLocal(rdto.getItems()));
		res.setOrderState(OrderStateConverter.toLocal(rdto.getDeliveryState()));
		res.setPayType(PayTypeConverter.toLocal(rdto.getPayment()));
		res.setTotalPrice(rdto.getFullCost());

		return res;
	}

	public static List<OrderVo> toLocal(List<RemoteOrderDTO> orders) {
		List<OrderVo> res = new ArrayList<>();

		if (orders != null) {
			for (RemoteOrderDTO dto : orders) {
				res.add(toLocal(dto));
			}
		}

		return res;
	}

	private static XMLGregorianCalendar toGregorianXml(Date deadline) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(deadline);

		XMLGregorianCalendar res = null;

		try {
			res = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException("Conversion Exception", e);
		}

		return res;
	}

}
