package hu.schonherz.restaurant.dao;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.schonherz.restaurant.dto.AddressReport;
import hu.schonherz.restaurant.dto.OrderCountReport;

@NamedQueries({
	@NamedQuery(query = "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.order_date, count(o)) From ORDER o where day(o.order_date) = day(CURRENT_DATE) Group by HOUR(o.order_date)", name = "ReportDao.dailyQuery"),
	@NamedQuery(query = "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.order_date, count(o)) From ORDER o where MONTH(o.order_date) = MONTH(CURRENT_DATE) Group by DAY(o.order_date)", name = "ReportDao.monthlyQuery"),
	@NamedQuery(query = "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.order_date, count(o)) From ORDER o where DAY(o.order_date) between DAY(CURRENT_DATE) AND DAY(CUREENT_DATE)-7 Group by day(o.order_date)", name = "ReportDao.weeklyQuery"),
	@NamedQuery(query = "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.order_date, count(o)) From ORDER o where YEAR(O.ORDER_DATE) = YEAR(CURRENT_DATE) Group by o.date", name = "ReportDao.annualQuery"),
	@NamedQuery(query = "SELECT NEW hu.schonherz.restaurant.dto.OrderCountReport(o.order_date, count(o)) From ORDER o Group by o.date", name = "ReportDao.overallQuery"),
	@NamedQuery(query = "SELECT New hu.schonherz.restaurant.dto.AddressReport(o.order_address) FROM ORDER o", name = "addressQuery")

	
})
@Transactional
@Repository
public interface ReportDao extends JpaRepository<OrderCountReport<String, Integer>, Long>{
	public List<OrderCountReport<String, Integer>> dailyQuery();
	public List<OrderCountReport<String, Integer>> weeklyQuery();
	public List<OrderCountReport<String, Integer>> monthlyQuery();
	public List<OrderCountReport<String, Integer>> annualQuery();
	public List<OrderCountReport<String, Integer>> overallQuery();
	public List<AddressReport<String>> addressQuery();
}
