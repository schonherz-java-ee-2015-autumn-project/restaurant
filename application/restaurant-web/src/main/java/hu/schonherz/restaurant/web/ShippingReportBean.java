package hu.schonherz.restaurant.web;

import java.util.Calendar;
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
import hu.schonherz.restaurant.service.vo.PayTypeReportVo;

@ManagedBean(name = "shippingReportBean")

public class ShippingReportBean {

	@ManagedProperty("#{out}")
	ResourceBundle resources;
	
	Calendar now = Calendar.getInstance();
	Calendar c = Calendar.getInstance();
	Calendar d = Calendar.getInstance();
	Calendar e = Calendar.getInstance();
	
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
	private BarChartModel dailyPayTypeQueryModel;
	private BarChartModel weeklyPayTypeQueryModel;
	private BarChartModel monthlyPayTypeQueryModel;
	private BarChartModel annualPayTypeQueryModel;
	private BarChartModel overallPayTypeQueryModel;
	

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
	private List<PayTypeReportVo> dailyPayTypeQueryList;
	private List<PayTypeReportVo> weeklyPayTypeQueryList;
	private List<PayTypeReportVo> monthlyPayTypeQueryList;
	private List<PayTypeReportVo> annualPayTypeQueryList;
	private List<PayTypeReportVo> overallPayTypeQueryList;
	
	
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
		
		String title = resources.getString("dailyquery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+now.get(Calendar.DATE);
		dailyQueryModel.setTitle(title);
		dailyQueryModel.setAnimate(true);
		dailyQueryModel.setLegendPosition("ne");
		Axis yAxis = dailyQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!dailyQueryList.isEmpty())
		yAxis.setMax((dailyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
				.getQuantity()) + 10);
		else {
			dailyQueryModel=emptyModel();
				}

		weeklyQueryModel = initWeeklyQueryModel();
		c.set(Calendar.DAY_OF_WEEK, (Calendar.MONDAY)-1);
		d.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		d.add(Calendar.DATE, 6);
		title = resources.getString("weeklyquery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+d.get(Calendar.YEAR)+"."+(d.get(Calendar.MONTH)+1)+"."+(d.get(Calendar.DATE));

		weeklyQueryModel.setTitle(title);
		weeklyQueryModel.setAnimate(true);
		weeklyQueryModel.setLegendPosition("ne");
		yAxis = weeklyQueryModel.getAxis(AxisType.Y);
		DateAxis axis = new DateAxis("Dates");
		yAxis.setMin(0);
		if (!weeklyQueryList.isEmpty())
			yAxis.setMax((weeklyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
					.getQuantity()) + 10);
		else {
			weeklyQueryModel=emptyModel();
				}

		axis.setTickFormat("%#d");
		
		monthlyQueryModel = initMonthlyQueryModel();
		c.set(Calendar.DAY_OF_MONTH, 1);
		e.set(Calendar.DAY_OF_MONTH,1);
		e.add(Calendar.MONTH, 1);
		e.add(Calendar.DATE, -1);
		title = resources.getString("monthlyquery")+" "+c.get(Calendar.YEAR)+"."+(c.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+e.get(Calendar.YEAR)+"."+(e.get(Calendar.MONTH)+1)+"."+(e.get(Calendar.DATE));
		monthlyQueryModel.setTitle(title);
		monthlyQueryModel.setAnimate(true);
		monthlyQueryModel.setLegendPosition("ne");
		yAxis = monthlyQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!monthlyQueryList.isEmpty())
			yAxis.setMax((monthlyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
					.getQuantity()) + 10);
			else {
				monthlyQueryModel=emptyModel();
					}


		annualQueryModel = initAnnualQueryModel();
		title = resources.getString("annualquery")+" "+now.get(Calendar.YEAR);

		annualQueryModel.setTitle(title);
		annualQueryModel.setAnimate(true);
		annualQueryModel.setLegendPosition("ne");
		yAxis = annualQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!annualQueryList.isEmpty())
			yAxis.setMax((annualQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
					.getQuantity()) + 10);
		else {
			annualQueryModel=emptyModel();
				}


		overallQueryModel = initOverallQueryModel();
		overallQueryModel.setTitle(resources.getString("overallquery"));
		overallQueryModel.setAnimate(true);
		overallQueryModel.setLegendPosition("ne");
		yAxis = overallQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!overallQueryList.isEmpty())
			yAxis.setMax((overallQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity())).get()
					.getQuantity()) + 10);
		else {
			overallQueryModel=emptyModel();
				}


		dailyFinancialQueryModel = initDailyFinancialQueryModel();
		title = resources.getString("dailyfinancialquery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+now.get(Calendar.DATE);
		dailyFinancialQueryModel.setTitle(title);
		dailyFinancialQueryModel.setAnimate(true);
		dailyFinancialQueryModel.setLegendPosition("ne");
		
		yAxis = dailyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!dailyFinancialQueryList.isEmpty())
			yAxis.setMax((dailyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			dailyFinancialQueryModel=emptyModel();
				}

		weeklyFinancialQueryModel = initWeeklyFinancialQueryModel();
		
		title = resources.getString("weeklyfinancialquery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+d.get(Calendar.YEAR)+"."+(d.get(Calendar.MONTH)+1)+"."+(d.get(Calendar.DATE));

		weeklyFinancialQueryModel.setTitle(title);
		weeklyFinancialQueryModel.setAnimate(true);
		weeklyFinancialQueryModel.setLegendPosition("ne");
		yAxis = weeklyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!weeklyFinancialQueryList.isEmpty())
			yAxis.setMax((weeklyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			weeklyFinancialQueryModel=emptyModel();
				}

		monthlyFinancialQueryModel = initMonthlyFinancialQueryModel();
		
		title = resources.getString("monthlyfinancialquery")+" "+c.get(Calendar.YEAR)+"."+(c.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+e.get(Calendar.YEAR)+"."+(e.get(Calendar.MONTH)+1)+"."+(e.get(Calendar.DATE));
		monthlyFinancialQueryModel.setTitle(title);
		monthlyFinancialQueryModel.setAnimate(true);
		monthlyFinancialQueryModel.setLegendPosition("ne");
		yAxis = monthlyFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!monthlyFinancialQueryList.isEmpty())
			yAxis.setMax((monthlyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			monthlyFinancialQueryModel=emptyModel();
				}
					

		annualFinancialQueryModel = initAnnualFinancialQueryModel();
		title = resources.getString("annualfinancialquery")+" "+now.get(Calendar.YEAR);
		annualFinancialQueryModel.setTitle(title);
		annualFinancialQueryModel.setAnimate(true);
		annualFinancialQueryModel.setLegendPosition("ne");
		yAxis = annualFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!annualFinancialQueryList.isEmpty())
			yAxis.setMax((annualFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			annualFinancialQueryModel=emptyModel();
				}

		overallFinancialQueryModel = initOverallFinancialQueryModel();
		overallFinancialQueryModel.setTitle(resources.getString("overallfinancialquery"));
		overallFinancialQueryModel.setAnimate(true);
		overallFinancialQueryModel.setLegendPosition("ne");
		yAxis = overallFinancialQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!overallFinancialQueryList.isEmpty())
			yAxis.setMax((overallFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			overallFinancialQueryModel=emptyModel();
				}

		dailyPayTypeQueryModel = initDailyPayTypeQueryModel();
		title = resources.getString("dailypaytypequery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+now.get(Calendar.DATE);

		dailyPayTypeQueryModel.setTitle(title);
		dailyPayTypeQueryModel.setAnimate(true);
		dailyPayTypeQueryModel.setLegendPosition("ne");
		
		yAxis = dailyPayTypeQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!dailyPayTypeQueryList.isEmpty())
			yAxis.setMax((dailyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			dailyPayTypeQueryModel=emptyModel();
				}

		weeklyPayTypeQueryModel = initWeeklyPayTypeQueryModel();
		
		title = resources.getString("weeklypaytypequery")+" "+now.get(Calendar.YEAR)+"."+(now.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+d.get(Calendar.YEAR)+"."+(d.get(Calendar.MONTH)+1)+"."+(d.get(Calendar.DATE));

		weeklyPayTypeQueryModel.setTitle(title);
		weeklyPayTypeQueryModel.setAnimate(true);
		weeklyPayTypeQueryModel.setLegendPosition("ne");
		yAxis = weeklyPayTypeQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!weeklyPayTypeQueryList.isEmpty())
			yAxis.setMax((weeklyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			weeklyPayTypeQueryModel=emptyModel();
				}

		monthlyPayTypeQueryModel = initMonthlyPayTypeQueryModel();
		
		title = resources.getString("monthlypaytypequery")+" "+c.get(Calendar.YEAR)+"."+(c.get(Calendar.MONTH) + 1)+"."+c.get(Calendar.DATE)+" - "+e.get(Calendar.YEAR)+"."+(e.get(Calendar.MONTH)+1)+"."+(e.get(Calendar.DATE));
		monthlyPayTypeQueryModel.setTitle(title);
		monthlyPayTypeQueryModel.setAnimate(true);
		monthlyPayTypeQueryModel.setLegendPosition("ne");
		yAxis = monthlyPayTypeQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!monthlyPayTypeQueryList.isEmpty())
			yAxis.setMax((monthlyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			monthlyPayTypeQueryModel=emptyModel();
				}
					

		annualPayTypeQueryModel = initAnnualPayTypeQueryModel();
		title = resources.getString("annualpaytypequery")+" "+now.get(Calendar.YEAR);

		annualPayTypeQueryModel.setTitle(title);
		annualPayTypeQueryModel.setAnimate(true);
		annualPayTypeQueryModel.setLegendPosition("ne");
		yAxis = annualPayTypeQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!annualPayTypeQueryList.isEmpty())
			yAxis.setMax((annualPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			annualPayTypeQueryModel=emptyModel();
				}

		overallPayTypeQueryModel = initOverallPayTypeQueryModel();
		overallPayTypeQueryModel.setTitle(resources.getString("overallpaytypequery"));
		overallPayTypeQueryModel.setAnimate(true);
		overallPayTypeQueryModel.setLegendPosition("ne");
		yAxis = overallPayTypeQueryModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		if (!overallPayTypeQueryList.isEmpty())
			yAxis.setMax((overallPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) + 1000);
		else {
			overallPayTypeQueryModel=emptyModel();
				}


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
		model.setShowPointLabels(true);
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
		model.setShowPointLabels(true);
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
		model.setShowPointLabels(true);
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
		model.setShowPointLabels(true);

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
			order.set(item.getDate(), item.getPrice()-item.getPrice()*cost);
			orderWithCost.set(item.getDate(), item.getPrice()*cost);
		}
		model.addSeries(order);
		model.addSeries(orderWithCost);
		model.setShowPointLabels(true);

		model.setStacked(true);
		
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
			order.set(item.getDate(), item.getPrice()-item.getPrice()*cost);
			orderWithCost.set(item.getDate(),  item.getPrice()*cost);
		}
		model.addSeries(order);
		model.addSeries(orderWithCost);
		model.setShowPointLabels(true);

		model.setStacked(true);
		
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
			order.set(item.getDate(), item.getPrice()-item.getPrice()*cost);
			orderWithCost.set(item.getDate(), item.getPrice()*cost);
		}
		model.addSeries(order);
		model.addSeries(orderWithCost);
		model.setShowPointLabels(true);

		model.setStacked(true);
		
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
			order.set(item.getDate(), item.getPrice()-item.getPrice()*cost);
			orderWithCost.set(item.getDate(),  item.getPrice()*cost);
		}
		model.addSeries(order);
		model.addSeries(orderWithCost);
		model.setShowPointLabels(true);

		model.setStacked(true);
		
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
			order.set(item.getDate(), item.getPrice()-item.getPrice()*cost);
			orderWithCost.set(item.getDate(),  item.getPrice()*cost);
		}
		
		model.addSeries(order);
		model.addSeries(orderWithCost);
		model.setShowPointLabels(true);

		model.setStacked(true);
		
		return model;
	}
	
	private BarChartModel initEmptyModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		order.setLabel(resources.getString("empty_chart"));
		order.set("0", 0);
		
		model.addSeries(order);
		model.setShowPointLabels(true);
		return model;
	}

	
	private BarChartModel initDailyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		payType.setLabel(resources.getString("paytype"));
		dailyPayTypeQueryList = reportService.dailyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		for (PayTypeReportVo item : dailyPayTypeQueryList) {
			payType.set( item.getPayType(),item.getPrice() );
		}
		
		
		model.addSeries(payType);
		model.setShowPointLabels(true);
		return model;
	}

	private BarChartModel initWeeklyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		payType.setLabel(resources.getString("paytype"));
		weeklyPayTypeQueryList = reportService.weeklyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		for (PayTypeReportVo item : weeklyPayTypeQueryList) {
			payType.set( item.getPayType(),item.getPrice() );
		}
		
		
		model.addSeries(payType);
		model.setShowPointLabels(true);
		return model;
	}


	private BarChartModel initMonthlyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		payType.setLabel(resources.getString("paytype"));
		monthlyPayTypeQueryList = reportService.monthlyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		for (PayTypeReportVo item : monthlyPayTypeQueryList) {
			payType.set( item.getPayType(),item.getPrice() );
		}
		
		
		model.addSeries(payType);
		model.setShowPointLabels(true);
		return model;
	}

	private BarChartModel initAnnualPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		payType.setLabel(resources.getString("paytype"));
		annualPayTypeQueryList = reportService.annualPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		for (PayTypeReportVo item : annualPayTypeQueryList) {
			payType.set( item.getPayType(),item.getPrice() );
		}
		
		
		model.addSeries(payType);
		model.setShowPointLabels(true);
		return model;
	}


	private BarChartModel initOverallPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		payType.setLabel(resources.getString("paytype"));
		overallPayTypeQueryList = reportService.overallPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		for (PayTypeReportVo item : overallPayTypeQueryList) {
			payType.set( item.getPayType(),item.getPrice() );
		}
		
		
		model.addSeries(payType);
		model.setShowPointLabels(true);
		return model;
	}
	
	public BarChartModel emptyModel(){
		BarChartModel model = new BarChartModel();
		model=initEmptyModel();
		model.setTitle(resources.getString("empty_chart"));
		model.setAnimate(true);
		model.setLegendPosition("ne");
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(1);
		
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

	public BarChartModel getDailyPayTypeQueryModel() {
		return dailyPayTypeQueryModel;
	}

	public void setDailyPayTypeQueryModel(BarChartModel dailyPayTypeQueryModel) {
		this.dailyPayTypeQueryModel = dailyPayTypeQueryModel;
	}

	public BarChartModel getWeeklyPayTypeQueryModel() {
		return weeklyPayTypeQueryModel;
	}

	public void setWeeklyPayTypeQueryModel(BarChartModel weeklyPayTypeQueryModel) {
		this.weeklyPayTypeQueryModel = weeklyPayTypeQueryModel;
	}

	public BarChartModel getMonthlyPayTypeQueryModel() {
		return monthlyPayTypeQueryModel;
	}

	public void setMonthlyPayTypeQueryModel(BarChartModel monthlyPayTypeQueryModel) {
		this.monthlyPayTypeQueryModel = monthlyPayTypeQueryModel;
	}

	public BarChartModel getAnnualPayTypeQueryModel() {
		return annualPayTypeQueryModel;
	}

	public void setAnnualPayTypeQueryModel(BarChartModel annualPayTypeQueryModel) {
		this.annualPayTypeQueryModel = annualPayTypeQueryModel;
	}

	public BarChartModel getOverallPayTypeQueryModel() {
		return overallPayTypeQueryModel;
	}

	public void setOverallPayTypeQueryModel(BarChartModel overallPayTypeQueryModel) {
		this.overallPayTypeQueryModel = overallPayTypeQueryModel;
	}

	public List<PayTypeReportVo> getDailyPayTypeQueryList() {
		return dailyPayTypeQueryList;
	}

	public void setDailyPayTypeQueryList(List<PayTypeReportVo> dailyPayTypeQueryList) {
		this.dailyPayTypeQueryList = dailyPayTypeQueryList;
	}

	public List<PayTypeReportVo> getWeeklyPayTypeQueryList() {
		return weeklyPayTypeQueryList;
	}

	public void setWeeklyPayTypeQueryList(List<PayTypeReportVo> weeklyPayTypeQueryList) {
		this.weeklyPayTypeQueryList = weeklyPayTypeQueryList;
	}

	public List<PayTypeReportVo> getMonthlyPayTypeQueryList() {
		return monthlyPayTypeQueryList;
	}

	public void setMonthlyPayTypeQueryList(List<PayTypeReportVo> monthlyPayTypeQueryList) {
		this.monthlyPayTypeQueryList = monthlyPayTypeQueryList;
	}

	public List<PayTypeReportVo> getAnnualPayTypeQueryList() {
		return annualPayTypeQueryList;
	}

	public void setAnnualPayTypeQueryList(List<PayTypeReportVo> annualPayTypeQueryList) {
		this.annualPayTypeQueryList = annualPayTypeQueryList;
	}

	public List<PayTypeReportVo> getOverallPayTypeQueryList() {
		return overallPayTypeQueryList;
	}

	public void setOverallPayTypeQueryList(List<PayTypeReportVo> overallPayTypeQueryList) {
		this.overallPayTypeQueryList = overallPayTypeQueryList;
	}

}
