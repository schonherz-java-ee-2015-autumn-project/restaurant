package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.schonherz.administrator.RemoteItemDTO;
import hu.schonherz.restaurant.service.vo.ProductVo;

public class ProductConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Mapper mapper = new DozerBeanMapper();

	public static RemoteItemDTO toRemote(ProductVo vo) {
		if (vo == null) {
			return null;
		}
		return mapper.map(vo, RemoteItemDTO.class);
	}

	public static List<RemoteItemDTO> toRemote(List<ProductVo> vos) {
		List<RemoteItemDTO> res = new ArrayList<>(vos.size());

		if (vos != null) {
			for (ProductVo prod : vos) {
				res.add(toRemote(prod));
			}
		}

		return res;
	}

}
