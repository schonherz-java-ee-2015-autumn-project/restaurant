package hu.schonherz.restaurant.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
	Calendar f = Calendar.getInstance();

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
	private BarChartModel customQueryModel;
	private BarChartModel customFinancialModel;
	private BarChartModel customPayTypeModel;

	Date beginDate;
	Date endDate;

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
	private List<OrderCountReportVo> customQueryList;
	private List<FinancialReportVo> customFinancialQueryList;
	private List<PayTypeReportVo> customPayTypeQueryList;

	String option;

	@EJB
	ReportServiceLocal reportService;

	@ManagedProperty(value = "#{userSessionBean}")
	UserSessionBean userSessionBean;

	@PostConstruct
	public void init() {

		if (userSessionBean.getUser() == null) {
			userSessionBean.init();
		}
		dateSetting();
	}

	public void dateSetting() {
		c.set(Calendar.DAY_OF_WEEK, (Calendar.MONDAY));
		if (now.get(Calendar.DAY_OF_WEEK) == 1) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			d.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			d.add(Calendar.DATE, 6);
		} else
			c.add(Calendar.DATE, -1);
		d.set(Calendar.DAY_OF_WEEK, 7);
		// d.add(Calendar.DATE, 6);
		f.set(Calendar.DAY_OF_MONTH, 1);
		e.set(Calendar.DAY_OF_MONTH, 1);
		e.add(Calendar.MONTH, 1);
		e.add(Calendar.DATE, -1);
	}

	public void action(ActionEvent event) {
		try {
			customQueryModel = createCustomQuery(option, beginDate, endDate);

		} catch (Exception e) {
			addMessage(resources.getString("error_message"));
			e.printStackTrace();
		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void actionFinancial(ActionEvent event) {
		try {
			customFinancialModel = createCustomFinancialQuery(option, beginDate, endDate);

		} catch (Exception e) {
			addMessage(resources.getString("error_message"));
			e.printStackTrace();
		}
	}

	public void actionPayType(ActionEvent event) {
		try {
			customPayTypeModel = createCustomPayTypeQuery(option, beginDate, endDate);

		} catch (Exception e) {
			addMessage(resources.getString("error_message"));
			e.printStackTrace();
		}
	}

	private BarChartModel initDailyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		dailyQueryList = reportService.dailyQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!dailyQueryList.isEmpty()) {

			order.setLabel(resources.getString("number_of_orders"));

			for (OrderCountReportVo daily : dailyQueryList) {
				order.set(daily.getGroupped(), daily.getQuantity());
			}

			String title = resources.getString("dailyquery") + " " + now.get(Calendar.YEAR) + "."
					+ (now.get(Calendar.MONTH) + 1) + "." + now.get(Calendar.DATE);
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			model.addSeries(order);
			model.setShowPointLabels(true);
			yAxis.setMax((dailyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initWeeklyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		weeklyQueryList = reportService.weeklyQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!weeklyQueryList.isEmpty()) {
			order.setLabel(resources.getString("number_of_orders"));
			for (OrderCountReportVo weekly : weeklyQueryList) {
				order.set(weekly.getGroupped(), weekly.getQuantity());
			}
			String title = resources.getString("weeklyquery") + " " + c.get(Calendar.YEAR) + "."
					+ (c.get(Calendar.MONTH) + 1) + "." + (c.get(Calendar.DATE)) + " - " + d.get(Calendar.YEAR) + "."
					+ (d.get(Calendar.MONTH) + 1) + "." + (d.get(Calendar.DATE));

			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			DateAxis axis = new DateAxis("Dates");
			yAxis.setMin(0);
			yAxis.setMax((weeklyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
			axis.setTickFormat("%#d");

			model.addSeries(order);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initMonthlyQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		monthlyQueryList = reportService.monthlyQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!monthlyQueryList.isEmpty()) {
			order.setLabel(resources.getString("number_of_orders"));
			for (OrderCountReportVo monthly : monthlyQueryList) {
				order.set(monthly.getGroupped(), monthly.getQuantity());
			}

			String title = resources.getString("monthlyquery") + " " + f.get(Calendar.YEAR) + "."
					+ (f.get(Calendar.MONTH) + 1) + "." + f.get(Calendar.DATE) + " - " + e.get(Calendar.YEAR) + "."
					+ (e.get(Calendar.MONTH) + 1) + "." + (e.get(Calendar.DATE));
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((monthlyQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
			model.addSeries(order);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initAnnualQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		annualQueryList = reportService.annualQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!annualQueryList.isEmpty()) {
		order.setLabel(resources.getString("number_of_orders"));
			for (OrderCountReportVo annual : annualQueryList) {
				order.set(annual.getGroupped(), annual.getQuantity());
			}

			String title = resources.getString("annualquery") + " " + now.get(Calendar.YEAR);

			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((annualQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
			model.addSeries(order);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initOverallQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		overallQueryList = reportService.overallQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!overallQueryList.isEmpty()) {
			order.setLabel(resources.getString("number_of_orders"));
			for (OrderCountReportVo overall : overallQueryList) {
				order.set(overall.getGroupped(), overall.getQuantity());
			}

			model.setTitle(resources.getString("overallquery"));
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((overallQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
			model.addSeries(order);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initDailyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;
		dailyFinancialQueryList = reportService.dailyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!dailyFinancialQueryList.isEmpty()) {

			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));

			for (FinancialReportVo item : dailyFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}

			String title = resources.getString("dailyfinancialquery") + " " + now.get(Calendar.YEAR) + "."
					+ (now.get(Calendar.MONTH) + 1) + "." + now.get(Calendar.DATE);
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");

			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((dailyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);

			model.setStacked(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initWeeklyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;

		weeklyFinancialQueryList = reportService
				.weeklyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!weeklyFinancialQueryList.isEmpty()) {

			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));
			for (FinancialReportVo item : weeklyFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);

			model.setStacked(true);
			yAxis.setMax((weeklyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initMonthlyFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;

		monthlyFinancialQueryList = reportService
				.monthlyFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!monthlyFinancialQueryList.isEmpty()) {

			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));
			for (FinancialReportVo item : monthlyFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}

			String title = resources.getString("monthlyfinancialquery") + " " + c.get(Calendar.YEAR) + "."
					+ (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.DATE) + " - " + e.get(Calendar.YEAR) + "."
					+ (e.get(Calendar.MONTH) + 1) + "." + (e.get(Calendar.DATE));
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((monthlyFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);

			model.setStacked(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initAnnualFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;

		annualFinancialQueryList = reportService
				.annualFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!annualFinancialQueryList.isEmpty()) {
			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));
			for (FinancialReportVo item : annualFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}
			String title = resources.getString("annualfinancialquery") + " " + now.get(Calendar.YEAR);
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);

			yAxis.setMax((annualFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);

			model.setStacked(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initOverallFinancialQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;

		overallFinancialQueryList = reportService
				.overallFinancialQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!overallFinancialQueryList.isEmpty()) {

			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));
			for (FinancialReportVo item : overallFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}
			model.setTitle(resources.getString("overallfinancialquery"));
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((overallFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);

			model.setStacked(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initEmptyModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		model.setTitle(resources.getString("empty_chart"));
		order.setLabel(" ");
		order.set("0", 0);

		model.addSeries(order);
		model.setShowPointLabels(true);
		return model;
	}

	private BarChartModel initDailyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		dailyPayTypeQueryList = reportService.dailyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!dailyPayTypeQueryList.isEmpty()) {
			payType.setLabel(resources.getString("paytype"));

			for (PayTypeReportVo item : dailyPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}
			String title = resources.getString("dailypaytypequery") + " " + now.get(Calendar.YEAR) + "."
					+ (now.get(Calendar.MONTH) + 1) + "." + now.get(Calendar.DATE);

			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");

			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);

			yAxis.setMax((dailyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(payType);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initWeeklyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		weeklyPayTypeQueryList = reportService.weeklyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!weeklyPayTypeQueryList.isEmpty()) {

			payType.setLabel(resources.getString("paytype"));

			for (PayTypeReportVo item : weeklyPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}
			String title = resources.getString("weeklypaytypequery") + " " + now.get(Calendar.YEAR) + "."
					+ (now.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.DATE) + " - " + d.get(Calendar.YEAR) + "."
					+ (d.get(Calendar.MONTH) + 1) + "." + (d.get(Calendar.DATE));

			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((weeklyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(payType);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initMonthlyPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		monthlyPayTypeQueryList = reportService.monthlyPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());

		if (!monthlyPayTypeQueryList.isEmpty()) {
			payType.setLabel(resources.getString("paytype"));
			for (PayTypeReportVo item : monthlyPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}

			String title = resources.getString("monthlypaytypequery") + " " + c.get(Calendar.YEAR) + "."
					+ (c.get(Calendar.MONTH) + 1) + "." + c.get(Calendar.DATE) + " - " + e.get(Calendar.YEAR) + "."
					+ (e.get(Calendar.MONTH) + 1) + "." + (e.get(Calendar.DATE));
			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((monthlyPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(payType);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initAnnualPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		annualPayTypeQueryList = reportService.annualPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!annualPayTypeQueryList.isEmpty()) {

			payType.setLabel(resources.getString("paytype"));

			for (PayTypeReportVo item : annualPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}

			String title = resources.getString("annualpaytypequery") + " " + now.get(Calendar.YEAR);

			model.setTitle(title);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((annualPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(payType);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	private BarChartModel initOverallPayTypeQueryModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		overallPayTypeQueryList = reportService.overallPayTypeQuery(userSessionBean.getUser().getRestaurant().getId());
		if (!overallPayTypeQueryList.isEmpty()) {

			payType.setLabel(resources.getString("paytype"));

			for (PayTypeReportVo item : overallPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}
			model.setTitle(resources.getString("overallpaytypequery"));
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((overallPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.addSeries(payType);
			model.setShowPointLabels(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	public BarChartModel emptyModel() {
		BarChartModel model = new BarChartModel();
		model = initEmptyModel();
		model.setAnimate(true);
		model.setLegendPosition("ne");
		Axis yAxis = model.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(1);

		return model;
	}

	BarChartModel createCustomQuery(String option, Date beginDate, Date endDate) {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		if (option.equals("Day")) {
			customQueryList = reportService.customDailyQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Month")) {
			customQueryList = reportService.customMonthQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Year")) {
			customQueryList = reportService.customOverallQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (!customQueryList.isEmpty()) {

			order.setLabel(resources.getString("number_of_orders"));

			for (OrderCountReportVo item : customQueryList) {
				order.set(item.getGroupped(), item.getQuantity());
			}

			model.addSeries(order);
			model.setShowPointLabels(true);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);

			yAxis.setMax((customQueryList.stream().max((q1, q2) -> Long.compare(q1.getQuantity(), q2.getQuantity()))
					.get().getQuantity()) * 1.1);
		} else {
			model = emptyModel();
		}

		return model;
	}

	BarChartModel createCustomFinancialQuery(String option, Date beginDate, Date endDate) {
		BarChartModel model = new BarChartModel();
		ChartSeries order = new ChartSeries();
		ChartSeries orderWithCost = new ChartSeries();
		if (option.equals("Day")) {
			customFinancialQueryList = reportService.customDailyFinancialQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Month")) {
			customFinancialQueryList = reportService.customMonthFinancialQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Year")) {
			customFinancialQueryList = reportService.customOverallFinancialQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (!customFinancialQueryList.isEmpty()) {

			float cost = userSessionBean.getUser().getRestaurant().getCostOfService() / 100;
			order.setLabel(resources.getString("income"));
			orderWithCost.setLabel(resources.getString("incomeWithCost"));
			for (FinancialReportVo item : customFinancialQueryList) {
				order.set(item.getDate(), item.getPrice() - item.getPrice() * cost);
				orderWithCost.set(item.getDate(), item.getPrice() * cost);
			}

			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			model.addSeries(order);
			model.addSeries(orderWithCost);
			model.setShowPointLabels(true);
			yAxis.setMax((customFinancialQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
			model.setStacked(true);
		} else {
			model = emptyModel();
		}

		return model;
	}

	BarChartModel createCustomPayTypeQuery(String option, Date beginDate, Date endDate) {
		BarChartModel model = new BarChartModel();
		ChartSeries payType = new ChartSeries();
		if (option.equals("Day")) {
			customPayTypeQueryList = reportService.customDailyPayTypeQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Month")) {
			customPayTypeQueryList = reportService.customMonthPayTypeQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (option.equals("Year")) {
			customPayTypeQueryList = reportService.customOverallPayTypeQuery(beginDate, endDate,
					userSessionBean.getUser().getRestaurant().getId());
		}
		if (!customPayTypeQueryList.isEmpty()) {

			payType.setLabel(resources.getString("paytype"));

			for (PayTypeReportVo item : customPayTypeQueryList) {
				payType.set(item.getPayType(), item.getPrice());
			}

			model.addSeries(payType);
			model.setShowPointLabels(true);
			model.setAnimate(true);
			model.setLegendPosition("ne");
			Axis yAxis = model.getAxis(AxisType.Y);
			yAxis.setMin(0);
			yAxis.setMax((customPayTypeQueryList.stream().max((q1, q2) -> Long.compare(q1.getPrice(), q2.getPrice()))
					.get().getPrice()) * 1.1);
		} else {
			model = emptyModel();
		}
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

		return initDailyQueryModel();
	}

	public void setDailyQueryModel(BarChartModel dailyQueryModel) {
		this.dailyQueryModel = dailyQueryModel;
	}

	public BarChartModel getWeeklyQueryModel() {
		return initWeeklyQueryModel();
	}

	public void setWeeklyQueryModel(BarChartModel weeklyQueryModel) {
		this.weeklyQueryModel = weeklyQueryModel;
	}

	public BarChartModel getMonthlyQueryModel() {
		return initMonthlyQueryModel();
	}

	public void setMonthlyQueryModel(BarChartModel monthlyQueryModel) {
		this.monthlyQueryModel = monthlyQueryModel;
	}

	public BarChartModel getAnnualQueryModel() {
		return initAnnualQueryModel();
	}

	public void setAnnualQueryModel(BarChartModel annualQueryModel) {
		this.annualQueryModel = annualQueryModel;
	}

	public BarChartModel getOverallQueryModel() {
		return initOverallQueryModel();
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
		return initDailyFinancialQueryModel();
	}

	public void setDailyFinancialQueryModel(BarChartModel dailyFinancialQueryModel) {
		this.dailyFinancialQueryModel = dailyFinancialQueryModel;
	}

	public BarChartModel getWeeklyFinancialQueryModel() {
		return initWeeklyFinancialQueryModel();
	}

	public void setWeeklyFinancialQueryModel(BarChartModel weeklyFinancialQueryModel) {
		this.weeklyFinancialQueryModel = weeklyFinancialQueryModel;
	}

	public BarChartModel getMonthlyFinancialQueryModel() {
		return initMonthlyFinancialQueryModel();
	}

	public void setMonthlyFinancialQueryModel(BarChartModel monthlyFinancialQueryModel) {
		this.monthlyFinancialQueryModel = monthlyFinancialQueryModel;
	}

	public BarChartModel getAnnualFinancialQueryModel() {
		return initAnnualFinancialQueryModel();
	}

	public void setAnnualFinancialQueryModel(BarChartModel annualFinancialQueryModel) {
		this.annualFinancialQueryModel = annualFinancialQueryModel;
	}

	public BarChartModel getOverallFinancialQueryModel() {
		return initOverallFinancialQueryModel();
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
		return initDailyPayTypeQueryModel();
	}

	public void setDailyPayTypeQueryModel(BarChartModel dailyPayTypeQueryModel) {
		this.dailyPayTypeQueryModel = dailyPayTypeQueryModel;
	}

	public BarChartModel getWeeklyPayTypeQueryModel() {
		return initWeeklyPayTypeQueryModel();
	}

	public void setWeeklyPayTypeQueryModel(BarChartModel weeklyPayTypeQueryModel) {
		this.weeklyPayTypeQueryModel = weeklyPayTypeQueryModel;
	}

	public BarChartModel getMonthlyPayTypeQueryModel() {
		return initMonthlyPayTypeQueryModel();
	}

	public void setMonthlyPayTypeQueryModel(BarChartModel monthlyPayTypeQueryModel) {
		this.monthlyPayTypeQueryModel = monthlyPayTypeQueryModel;
	}

	public BarChartModel getAnnualPayTypeQueryModel() {
		return initAnnualPayTypeQueryModel();
	}

	public void setAnnualPayTypeQueryModel(BarChartModel annualPayTypeQueryModel) {
		this.annualPayTypeQueryModel = annualPayTypeQueryModel;
	}

	public BarChartModel getOverallPayTypeQueryModel() {
		return initOverallPayTypeQueryModel();
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

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
		System.out.println(option);
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BarChartModel getCustomQueryModel() {
		return customQueryModel;
	}

	public void setCustomQueryModel(BarChartModel customQueryModel) {
		this.customQueryModel = customQueryModel;
	}

	public BarChartModel getCustomFinancialModel() {
		return customFinancialModel;
	}

	public void setCustomFinancialModel(BarChartModel customFinancialModel) {
		this.customFinancialModel = customFinancialModel;
	}

	public BarChartModel getCustomPayTypeModel() {
		return customPayTypeModel;
	}

	public void setCustomPayTypeModel(BarChartModel customPayTypeModel) {
		this.customPayTypeModel = customPayTypeModel;
	}

	public List<OrderCountReportVo> getCustomQueryList() {
		return customQueryList;
	}

	public void setCustomQueryList(List<OrderCountReportVo> customQueryList) {
		this.customQueryList = customQueryList;
	}
	public List<OrderCountReportVo> getDailyQueryList() {
		return dailyQueryList;
	}

	public void setDailyQueryList(List<OrderCountReportVo> dailyQueryList) {
		this.dailyQueryList = dailyQueryList;
	}
	
	public List<OrderCountReportVo> getWeeklyQueryList() {
		return weeklyQueryList;
	}

	public void setWeeklyQueryList(List<OrderCountReportVo> weeklyQueryList) {
		this.weeklyQueryList = weeklyQueryList;
	}

	


}
