package hu.schonherz.restaurant.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.FinancialReport;
import hu.schonherz.restaurant.dto.OrderCountReport;
import hu.schonherz.restaurant.entities.Order;

/**
 * Created by tothd on 2015. 12. 19..
 */



@Repository
public interface OrderDao extends JpaRepository<Order, Long> {

	
    Page<Order> findByAddressContaining(String filter, Pageable pageable);
	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and date(o.deadline) = date(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Hour(o.deadline)")
	public List<OrderCountReport> dailyQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and week(o.deadline) = week(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Date(o.deadline)")
	public List<OrderCountReport> weeklyQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Date(o.deadline)")
    public List<OrderCountReport> monthlyQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o))From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and YEAR(o.deadline)= YEAR(Current_Date) group by Month(o.deadline)")
	public List<OrderCountReport> annualQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Delivery d join d.orders o where d.guid like ?1% and o.orderState = 'SUCCESS' group by o.deadline")
	public List<OrderCountReport> overallQuery(Long id);
	@Query("SELECT New hu.schonherz.restaurant.dto.AddressReport(o.address) FROM Delivery d join d.orders o join d.Restaurant where r.id = ?1%")
	public List<AddressReport> addressQuery(Long id);
	
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and date(o.deadline) = date(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Hour(o.deadline)")
	public List<FinancialReport> dailyFinancialQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and week(o.deadline) = week(Current_Date) and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Date(o.deadline)")
	public List<FinancialReport> weeklyFinancialQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and Month(o.deadline)=Month(Current_Date) and YEAR(o.deadline)= YEAR(Current_Date) group by Date(o.deadline)")
	public List<FinancialReport> monthlyFinancialQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Delivery d join d.orders o join d.Restaurant r where r.id =  ?1% and o.orderState = 'SUCCESS' and YEAR(o.deadline)= YEAR(Current_Date) group by Month(o.deadline)")
	public List<FinancialReport> annualFinancialQuery(Long id);
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Delivery d join d.orders o where d.guid like ?1% and o.orderState = 'SUCCESS' group by o.deadline")
	public List<FinancialReport> overallFinancialQuery(Long id);

}
