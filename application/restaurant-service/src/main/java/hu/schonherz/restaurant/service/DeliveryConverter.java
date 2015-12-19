package hu.schonherz.restaurant.service;

import hu.schonherz.restaurant.entities.Delivery;
import hu.schonherz.restaurant.service.vo.DeliveryVo;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 17..
 */
public class DeliveryConverter {

    static Mapper mapper = new DozerBeanMapper();

    public static DeliveryVo toVo(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        return mapper.map(delivery, DeliveryVo.class);
    }

    public static Delivery toEntity(DeliveryVo deliveryVo) {
        if (deliveryVo == null) {
            return null;
        }
        return mapper.map(deliveryVo, Delivery.class);
    }

    public static List<DeliveryVo> toVo(List<Delivery> deliveries) {
        List<DeliveryVo> deliveryVos = new ArrayList<>();
        for (Delivery delivery : deliveries) {
            deliveryVos.add(toVo(delivery));
        }
        return deliveryVos;
    }

    public static List<Delivery> toEntity(List<DeliveryVo> deliveryVos) {
        List<Delivery> deliveries = new ArrayList<>();
        for (DeliveryVo deliveryVo : deliveryVos) {
            deliveries.add(toEntity(deliveryVo));
        }
        return deliveries;
    }
}
