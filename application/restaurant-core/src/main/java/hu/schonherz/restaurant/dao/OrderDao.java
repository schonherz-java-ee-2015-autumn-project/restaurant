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
    @Query("SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Order o where o.deadline = CURRENT_DATE Group by (o.deadline)")
	public List<OrderCountReport> dailyQuery();
    @Query("SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Order o where MONTH(o.deadline) = MONTH(CURRENT_DATE) Group by DAY(o.deadline)")
	public List<OrderCountReport> weeklyQuery();
	@Query("SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Order o where DAY(o.deadline) between DAY(CURRENT_DATE) AND DAY(CURRENT_DATE)-7 Group by day(o.deadline)")
    public List<OrderCountReport> monthlyQuery();
	@Query( "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Order o where YEAR(o.deadline) = YEAR(CURRENT_DATE) Group by o.deadline")
	public List<OrderCountReport> annualQuery();
	@Query("SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.deadline, count(o)) From Order o Group by o.deadline")
	public List<OrderCountReport> overallQuery();
	@Query("SELECT New hu.schonherz.restaurant.dto.AddressReport(o.address) FROM Order o")
	public List<AddressReport> addressQuery();
	
	@Query("Select New hu.schonherz.restaurant.dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Order o join Delivery.orders d where d.guid like ?1% and o.orderState = 'SUCCESS' and o.deadline = Current_Date group by Hour(o.deadline)")
	public List<FinancialReport> dailyFinancialQuery(String res);
	@Query("Select New hu.schonherz.restaurant,dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Order o join Delivery.orders d where d.guid like ?1% and o.orderState = 'SUCCESS' and week(o.deadline) = week(Current_Date) group by Day(o.deadline)")
	public List<FinancialReport> weeklyFinancialQuery(String res);
	@Query("Select New hu.schonherz.restaurant,dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Order o join Delivery.orders d where d.guid like ?1% and o.orderState = 'SUCCESS' and Month(o.deadline) = Month(Current_Date) group by Week(o.deadline)")
	public List<FinancialReport> monthlyFinancialQuery(String res);
	@Query("Select New hu.schonherz.restaurant,dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Order o join Delivery.orders d where d.guid like ?1% and o.orderState = 'SUCCESS' and YEAR(o.deadline) = YEAR(Current_Date) group by Month(o.deadline)")
	public List<FinancialReport> annualFinancialQuery(String res);
	@Query("Select New hu.schonherz.restaurant,dto.FinancialReport(o.deadline, sum(o.totalPrice)) From Order o join Delivery.orders d where d.guid like ?1% and o.orderState = 'SUCCESS' group by Year(o.deadline)")
	public List<FinancialReport> overallFinancialQuery(String res);

}
