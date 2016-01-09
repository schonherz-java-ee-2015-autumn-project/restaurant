package hu.schonherz.restaurant.integration.converter;

import java.io.Serializable;

import hu.schonherz.administrator.RemotePaymentMethod;
import hu.schonherz.restaurant.type.PayType;

public class PayTypeConverter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static RemotePaymentMethod toRemote(PayType payType) {
		switch (payType) {
		case CREDITCARD:
			return RemotePaymentMethod.CREDIT_CARD;
		case MONEY:
			return RemotePaymentMethod.CASH;
		case NICECARD:
			return RemotePaymentMethod.SZEP_CARD;
		case VOUCHER:
			return RemotePaymentMethod.VOUCHER;
		}
		return null;
	}

	public static PayType toLocal(RemotePaymentMethod pm) {
		switch (pm) {
		case CASH:
			return PayType.MONEY;
		case CREDIT_CARD:
			return PayType.CREDITCARD;
		case SZEP_CARD:
			return PayType.NICECARD;
		case VOUCHER:
			return PayType.VOUCHER;
		}
		return null;
	}

}
