package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteItemQuantityDTO;
import hu.schonherz.restaurant.service.vo.ItemVo;

public class ItemConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemoteItemQuantityDTO toRemote(ItemVo vo) {
		if (vo == null) {
			return null;
		}

		RemoteItemQuantityDTO res = new RemoteItemQuantityDTO();
		res.setId(vo.getGlobalId());
		res.setItemDTO(ProductConverter.toRemote(vo.getProduct()));
		res.setQuantity(vo.getQuantity());
		return res;
	}

	public static List<RemoteItemQuantityDTO> toRemote(List<ItemVo> vos) {
		List<RemoteItemQuantityDTO> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (ItemVo item : vos) {
				res.add(toRemote(item));
			}
		}

		return res;
	}

	public static ItemVo toLocal(RemoteItemQuantityDTO rdto) {
		if (rdto == null) {
			return null;
		}

		ItemVo res = new ItemVo();
		res.setGlobalId(rdto.getId());
		res.setProduct(ProductConverter.toLocal(rdto.getItemDTO()));
		res.setQuantity(rdto.getQuantity());
		return res;
	}

	public static List<ItemVo> toLocal(List<RemoteItemQuantityDTO> rdtos) {
		List<ItemVo> res = new ArrayList<>();

		if (rdtos != null) {
			for (RemoteItemQuantityDTO dto : rdtos) {
				res.add(toLocal(dto));
			}
		}

		return res;
	}

}
