package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.restaurant.service.vo.ProductVo;

public class ProductConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemoteItemDTO toRemote(ProductVo vo) {
		if (vo == null) {
			return null;
		}

		RemoteItemDTO res = new RemoteItemDTO();

		res.setId(vo.getGlobalId());
		res.setName(vo.getName());
		res.setPrice(vo.getPrice());

		return res;
	}

	public static List<RemoteItemDTO> toRemote(List<ProductVo> vos) {
		List<RemoteItemDTO> res = new ArrayList<>();

		if (vos != null) {
			for (ProductVo prod : vos) {
				res.add(toRemote(prod));
			}
		}

		return res;
	}

	public static ProductVo toLocal(RemoteItemDTO rdto) {
		if (rdto == null) {
			return null;
		}

		ProductVo res = new ProductVo();

		res.setGlobalId(rdto.getId());
		res.setName(rdto.getName());
		res.setPrice(rdto.getPrice());

		return res;
	}

	public static List<ProductVo> toLocal(List<RemoteItemDTO> rdtos) {
		List<ProductVo> res = new ArrayList<>();

		if (rdtos != null) {
			for (RemoteItemDTO dto : rdtos) {
				res.add(toLocal(dto));
			}
		}

		return res;
	}
}
