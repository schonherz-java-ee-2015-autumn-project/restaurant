package hu.schonherz.restaurant.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.FinancialReport;
import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.dto.PayTypeReport;
import hu.schonherz.restaurant.entities.Order;

/**
 * Created by tothd on 2015. 12. 19..
 */

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

	Order findByGlobalId(Long globalId);

	Page<Order> findByAddressContaining(String filter, Pageable pageable);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Hour(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and date(o.deadline) = date(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Hour(o.deadline)")
	public List<OrderCountReport> dailyQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Day(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and week(o.deadline) = week(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Day(o.deadline)")
	public List<OrderCountReport> weeklyQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Day(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Day(o.deadline)")
	public List<OrderCountReport> monthlyQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Month(o.deadline), count(o))From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and YEAR(o.deadline)= YEAR(Current_Date) group by Month(o.deadline)")
	public List<OrderCountReport> annualQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Year(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id = ?1 and o.orderState = 'DELIVERED' group by YEAR(o.deadline)")
	public List<OrderCountReport> overallQuery(Long id);

	@Query("SELECT New hu.schonherz.restaurant.dto.AddressReport(o.address) FROM Delivery d join d.orders o join d.restaurant r where r.id = ?1 and o.orderState= 'DELIVERED'")
	public List<AddressReport> addressQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Hour(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and date(o.deadline) = date(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Hour(o.deadline)")
	public List<FinancialReport> dailyFinancialQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Day(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and week(o.deadline) = week(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Day(o.deadline)")
	public List<FinancialReport> weeklyFinancialQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Day(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Day(o.deadline)")
	public List<FinancialReport> monthlyFinancialQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Month(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and YEAR(o.deadline)= YEAR(Current_Date) group by Month(o.deadline)")
	public List<FinancialReport> annualFinancialQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Year(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' group by YEAR(o.deadline)")
	public List<FinancialReport> overallFinancialQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and date(o.deadline) = date(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by o.payType")

	public List<PayTypeReport> dailyPayTypeQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and week(o.deadline) = week(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by o.payType")
	public List<PayTypeReport> weeklyPayTypeQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by o.payType")
	public List<PayTypeReport> monthlyPayTypeQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' and YEAR(o.deadline)= YEAR(Current_Date) group by o.payType")
	public List<PayTypeReport> annualPayTypeQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?1 and o.orderState = 'DELIVERED' group by o.payType")
	public List<PayTypeReport> overallPayTypeQuery(Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Day(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Day(o.deadline) order by YEAR(o.deadline), MONTH(o.deadline), DAY(o.deadline)")
	public List<OrderCountReport> customDailyQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Month(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Month(o.deadline) order by YEAR(o.deadline), MONTH(o.deadline), DAY(o.deadline)")
	public List<OrderCountReport> customMonthQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(Year(o.deadline), count(o)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Year(o.deadline)")
	public List<OrderCountReport> customOverallQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Day(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Day(o.deadline)order by YEAR(o.deadline), MONTH(o.deadline), DAY(o.deadline)")
	public List<FinancialReport> customDailyFinancialQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Month(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Month(o.deadline)order by YEAR(o.deadline), MONTH(o.deadline), DAY(o.deadline)")
	public List<FinancialReport> customMonthFinancialQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(Year(o.deadline), sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by Year(o.deadline)")
	public List<FinancialReport> customOverallFinancialQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by o.payType")
	public List<PayTypeReport> customDailyPayTypeQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by o.payType")
	public List<PayTypeReport> customMonthPayTypeQuery(Date beginDate, Date endDate, Long id);

	@Query("Select New hu.schonherz.restaurant.dto.PayTypeReport(o.payType, sum(o.totalPrice)) From Delivery d join d.orders o join d.restaurant r where r.id =  ?3 and o.orderState = 'DELIVERED' and date(o.deadline) between ?1 and ?2 group by o.payType")
	public List<PayTypeReport> customOverallPayTypeQuery(Date beginDate, Date endDate, Long id);

}
