package hu.schonherz.restaurant.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.map.MapModel;

import hu.schonherz.restaurant.service.ReportServiceLocal;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.FinancialReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

@ManagedBean(name = "shippingReportBean")

public class ShippingReportBean {

	@ManagedProperty("#{out}")
	ResourceBundle resources;

	private BarChartModel dailyQueryModel;
	private BarChartModel weeklyQueryModel;
	private BarChartModel monthlyQueryModel;
	private BarChartModel annualQueryModel;
	private BarChartModel overallQueryModel;
	private BarChartModel dailyFinancialQueryModel;
	private BarChartModel weeklyFinancialQueryModel;
	private BarChartModel monthlyFinancialQueryModel;
	private BarChartModel annualFinancialQueryModel;
	private BarChartModel overallFinancialQueryModel;

	private MapModel geoModel;

	private List<OrderCountReportVo> dailyQueryList;
	private List<OrderCountReportVo> weeklyQueryList;
	private List<OrderCountReportVo> monthlyQueryList;
	private List<OrderCountReportVo> annualQueryList;
	private List<OrderCountReportVo> overallQueryList;
	private List<AddressReportVo> addressQueryList;
	private List<FinancialReportVo> dailyFinancialQueryList;
	private List<FinancialReportVo> weeklyFinancialQueryList;
	private List<FinancialReportVo> monthlyFinancialQueryList;
	private List<FinancialReportVo> annualFinancialQueryList;
	private List<FinancialReportVo> overallFinancialQueryList;
	
	
	@EJB
	ReportServiceLocal reportService;

	@ManagedProperty(value = "#{userSessionBean}")
	UserSessionBean userSessionBean;

	@PostConstruct
	public void init() {
		createAnimatedModels();
		
		if (userSessionBean.getUser() == null) {
				userSessionBean.init();
			}
	}

	private void createAnimatedModels() {

		dailyQueryModel = initDailyQueryModel();
		dailyQueryModel.setTitle(resources.getString("dailyquery"));
		dailyQueryModel.setAnimate(true);
		dailyQueryModel.setLegendPosition("ne");
		Axis yAxis = dailyQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax((dailyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);

		weeklyQueryModel = initWeeklyQueryModel();
		weeklyQueryModel.setTitle(resources.getString("weeklyquery"));
		weeklyQueryModel.setAnimate(true);
		weeklyQueryModel.setLegendPosition("ne");
		yAxis = weeklyQueryModel.getAxis(AxisType.Y);
		DateAxis axis = new DateAxis("Dates");
		yAxis.setMin(0);
		yAxis.setMax((weeklyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);
		axis.setTickFormat("%#d");
		
		monthlyQueryModel = initMonthlyQueryModel();
		monthlyQueryModel.setTitle(resources.getString("monthlyquery"));
		monthlyQueryModel.setAnimate(true);
		monthlyQueryModel.setLegendPosition("ne");
		yAxis = monthlyQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax((monthlyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);

		annualQueryModel = initAnnualQueryModel();
		annualQueryModel.setTitle(resources.getString("annualquery"));
		annualQueryModel.setAnimate(true);
		annualQueryModel.setLegendPosition("ne");
		yAxis = annualQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax((annualQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);

		overallQueryModel = initOverallQueryModel();
		overallQueryModel.setTitle(resources.getString("overallquery"));
		overallQueryModel.setAnimate(true);
		overallQueryModel.setLegendPosition("ne");
		yAxis = overallQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax((overallQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);

		dailyFinancialQueryModel = initDailyFinancialQueryModel();
		dailyFinancialQueryModel.setTitle(resources.getString("dailyfinancialquery"));
		dailyFinancialQueryModel.setAnimate(true);
		dailyFinancialQueryModel.setLegendPosition("ne");
		
		yAxis = dailyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
	//	yAxis.setMax((dailyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice())).get()
	//			.getPrice()) + 10);

		weeklyFinancialQueryModel = initWeeklyFinancialQueryModel();
		weeklyFinancialQueryModel.setTitle(resources.getString("weeklyfinancialquery"));
		weeklyFinancialQueryModel.setAnimate(true);
		weeklyFinancialQueryModel.setLegendPosition("ne");
		yAxis = weeklyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
//		yAxis.setMax((weeklyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
//				.get().getPrice()) + 10);

		monthlyFinancialQueryModel = initMonthlyFinancialQueryModel();
		monthlyFinancialQueryModel.setTitle(resources.getString("monthlyfinancialquery"));
		monthlyFinancialQueryModel.setAnimate(true);
		monthlyFinancialQueryModel.setLegendPosition("ne");
		yAxis = monthlyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
//		yAxis.setMax((monthlyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
//				.get().getPrice()) + 10);

		annualFinancialQueryModel = initAnnualFinancialQueryModel();
		annualFinancialQueryModel.setTitle(resources.getString("annualfinancialquery"));
		annualFinancialQueryModel.setAnimate(true);
		annualFinancialQueryModel.setLegendPosition("ne");
		yAxis = annualFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
//		yAxis.setMax((annualFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
//				.get().getPrice()) + 10);

		overallFinancialQueryModel = initOverallFinancialQueryModel();
		overallFinancialQueryModel.setTitle(resources.getString("overallfinancialquery"));
		overallFinancialQueryModel.setAnimate(true);
		overallFinancialQueryModel.setLegendPosition("ne");
		yAxis = overallFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
//		yAxis.setMax((overallFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
//				.get().getPrice()) + 10);

	}

	private BarChartModel initDailyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("number_of_orders"));
		dailyQueryList = reportService.dailyQuery(userSessionBean.getUser().getRestaurant().getId());

		for (OrderCountReportVo daily : dailyQueryList) {
			order.set(daily.getGroupped(), daily.getQuantity());
		}
		
		model.addSeries(order);
		return model;
	}

	private BarChartModel initWeeklyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("number_of_orders"));
		weeklyQueryList = reportService.weeklyQuery(userSessionBean.getUser().getRestaurant().getId());

		for (OrderCountReportVo weekly : weeklyQueryList) {
			order.set(weekly.getGroupped(), weekly.getQuantity());
		}

		
		model.addSeries(order);
		return model;
	}

	private BarChartModel initMonthlyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("number_of_orders"));
		monthlyQueryList = reportService.monthlyQuery(userSessionBean.getUser().getRestaurant().getId());

		for (OrderCountReportVo monthly : monthlyQueryList) {
			order.set(monthly.getGroupped(), monthly.getQuantity());
		}
		
		model.addSeries(order);
		return model;
	}

	private BarChartModel initAnnualQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("number_of_orders"));
		annualQueryList = reportService.annualQuery(userSessionBean.getUser().getRestaurant().getId());
		

		for (OrderCountReportVo annual : annualQueryList) {
			order.set(annual.getGroupped(), annual.getQuantity());
		}
		
		model.addSeries(order);
		return model;
	}

	private BarChartModel initOverallQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("number_of_orders"));
		overallQueryList = reportService.overallQuery(userSessionBean.getUser().getRestaurant().getId());

		for (OrderCountReportVo overall : overallQueryList) {
			order.set(overall.getGroupped(), overall.getQuantity());
		}
		
		model.addSeries(order);
		return model;
	}

	private BarChartModel initDailyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService()/100;
		order.setLabel(resources.getString("income"));
		orderWithCost.setLabel(resources.getString("incomeWithCost"));
		dailyFinancialQueryList = reportService.dailyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		
		for (FinancialReportVo item : dailyFinancialQueryList) {
			order.set(item.getDate(), item.getPrice());
			orderWithCost.set(item.getDate(),  item.getPrice()-item.getPrice()*cost);
		}
		model.addSeries(orderWithCost);
		model.addSeries(order);
		
		
		return model;
	}

	private BarChartModel initWeeklyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService()/100;
		order.setLabel(resources.getString("income"));
		orderWithCost.setLabel(resources.getString("incomeWithCost"));
		weeklyFinancialQueryList = reportService.weeklyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		
		for (FinancialReportVo item : weeklyFinancialQueryList) {
			order.set(item.getDate(), item.getPrice());
			orderWithCost.set(item.getDate(),  item.getPrice()-item.getPrice()*cost);
		}
		model.addSeries(orderWithCost);
		model.addSeries(order);
		
		
		return model;
	}
	private BarChartModel initMonthlyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService()/100;
		order.setLabel(resources.getString("income"));
		orderWithCost.setLabel(resources.getString("incomeWithCost"));
		monthlyFinancialQueryList = reportService.monthlyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		
		for (FinancialReportVo item : monthlyFinancialQueryList) {
			order.set(item.getDate(), item.getPrice());
			orderWithCost.set(item.getDate(),  item.getPrice()-item.getPrice()*cost);
		}
		model.addSeries(orderWithCost);
		model.addSeries(order);
		
		
		return model;
	}

	private BarChartModel initAnnualFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService()/100;
		order.setLabel(resources.getString("income"));
		orderWithCost.setLabel(resources.getString("incomeWithCost"));
		annualFinancialQueryList = reportService.annualFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		
		for (FinancialReportVo item : annualFinancialQueryList) {
			order.set(item.getDate(), item.getPrice());
			orderWithCost.set(item.getDate(),  item.getPrice()-item.getPrice()*cost);
		}
		model.addSeries(orderWithCost);
		model.addSeries(order);
		
		
		return model;
	}

	private BarChartModel initOverallFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService()/100;
		order.setLabel(resources.getString("income"));
		orderWithCost.setLabel(resources.getString("incomeWithCost"));
		overallFinancialQueryList = reportService.overallFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		
		for (FinancialReportVo item : overallFinancialQueryList) {
			order.set(item.getDate(), item.getPrice());
			orderWithCost.set(item.getDate(),  item.getPrice()-item.getPrice()*cost);
		}
		model.addSeries(orderWithCost);
		model.addSeries(order);
		
		
		return model;
	}

	// public void addAddressToTheMap() {
	// addressQueryList = reportService.addressQuery();
	//
	//
	// if (addressQueryList != null && !addressQueryList.isEmpty()) {
	// for (AddressReportVo<String> address : addressQueryList) {
	//
	//
	// LatLng center = address.getAddress().
	// centerGeoMap = center.getLat() + "," + center.getLng();
	//
	// for (int i = 0; i < results.size(); i++) {
	// GeocodeResult result = results.get(i);
	// geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
	// }
	// }
	// }
	// }

	public BarChartModel getDailyQueryModel() {
		return dailyQueryModel;
	}

	public void setDailyQueryModel(BarChartModel dailyQueryModel) {
		this.dailyQueryModel = dailyQueryModel;
	}

	public BarChartModel getWeeklyQueryModel() {
		return weeklyQueryModel;
	}

	public void setWeeklyQueryModel(BarChartModel weeklyQueryModel) {
		this.weeklyQueryModel = weeklyQueryModel;
	}

	public BarChartModel getMonthlyQueryModel() {
		return monthlyQueryModel;
	}

	public void setMonthlyQueryModel(BarChartModel monthlyQueryModel) {
		this.monthlyQueryModel = monthlyQueryModel;
	}

	public BarChartModel getAnnualQueryModel() {
		return annualQueryModel;
	}

	public void setAnnualQueryModel(BarChartModel annualQueryModel) {
		this.annualQueryModel = annualQueryModel;
	}

	public BarChartModel getOverallQueryModel() {
		return overallQueryModel;
	}

	public void setOverallQueryModel(BarChartModel overallQueryModel) {
		this.overallQueryModel = overallQueryModel;
	}

	public ResourceBundle getResources() {
		return resources;
	}

	public void setResources(ResourceBundle resources) {
		this.resources = resources;
	}

	public BarChartModel getDailyFinancialQueryModel() {
		return dailyFinancialQueryModel;
	}

	public void setDailyFinancialQueryModel(BarChartModel dailyFinancialQueryModel) {
		this.dailyFinancialQueryModel = dailyFinancialQueryModel;
	}

	public BarChartModel getWeeklyFinancialQueryModel() {
		return weeklyFinancialQueryModel;
	}

	public void setWeeklyFinancialQueryModel(BarChartModel weeklyFinancialQueryModel) {
		this.weeklyFinancialQueryModel = weeklyFinancialQueryModel;
	}

	public BarChartModel getMonthlyFinancialQueryModel() {
		return monthlyFinancialQueryModel;
	}

	public void setMonthlyFinancialQueryModel(BarChartModel monthlyFinancialQueryModel) {
		this.monthlyFinancialQueryModel = monthlyFinancialQueryModel;
	}

	public BarChartModel getAnnualFinancialQueryModel() {
		return annualFinancialQueryModel;
	}

	public void setAnnualFinancialQueryModel(BarChartModel annualFinancialQueryModel) {
		this.annualFinancialQueryModel = annualFinancialQueryModel;
	}

	public BarChartModel getOverallFinancialQueryModel() {
		return overallFinancialQueryModel;
	}

	public void setOverallFinancialQueryModel(BarChartModel overallFinancialQueryModel) {
		this.overallFinancialQueryModel = overallFinancialQueryModel;
	}

	public List<FinancialReportVo> getDailyFinancialQueryList() {
		return dailyFinancialQueryList;
	}

	public void setDailyFinancialQueryList(List<FinancialReportVo> dailyFinancialQueryList) {
		this.dailyFinancialQueryList = dailyFinancialQueryList;
	}

	public List<FinancialReportVo> getWeeklyFinancialQueryList() {
		return weeklyFinancialQueryList;
	}

	public void setWeeklyFinancialQueryList(List<FinancialReportVo> weeklyFinancialQueryList) {
		this.weeklyFinancialQueryList = weeklyFinancialQueryList;
	}

	public List<FinancialReportVo> getMonthlyFinancialQueryList() {
		return monthlyFinancialQueryList;
	}

	public void setMonthlyFinancialQueryList(List<FinancialReportVo> monthlyFinancialQueryList) {
		this.monthlyFinancialQueryList = monthlyFinancialQueryList;
	}

	public List<FinancialReportVo> getAnnualFinancialQueryList() {
		return annualFinancialQueryList;
	}

	public void setAnnualFinancialQueryList(List<FinancialReportVo> annualFinancialQueryList) {
		this.annualFinancialQueryList = annualFinancialQueryList;
	}

	public List<FinancialReportVo> getOverallFinancialQueryList() {
		return overallFinancialQueryList;
	}

	public void setOverallFinancialQueryList(List<FinancialReportVo> overallFinancialQueryList) {
		this.overallFinancialQueryList = overallFinancialQueryList;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}

}
