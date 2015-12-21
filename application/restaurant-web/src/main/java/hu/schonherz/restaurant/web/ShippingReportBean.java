package hu.schonherz.restaurant.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;

import hu.schonherz.restaurant.service.ReportServiceLocal;
import hu.schonherz.restaurant.service.vo.AddressReportVo;
import hu.schonherz.restaurant.service.vo.OrderCountReportVo;

@ManagedBean(name = "shippingReportBean")

public class ShippingReportBean {
	
	 private BarChartModel dailyQueryModel;
	 private BarChartModel  weeklyQueryModel;
	 private BarChartModel  monthlyQueryModel;
	 private BarChartModel  annualQueryModel;
	 private BarChartModel  overallQueryModel;
	 
	 private MapModel geoModel;
	  
	   
	
	private List <OrderCountReportVo<String, Integer>> dailyQueryList;
	private List <OrderCountReportVo<String, Integer>> weeklyQueryList;
	private List <OrderCountReportVo<String, Integer>> monthlyQueryList;
	private List <OrderCountReportVo<String, Integer>> annualQueryList;
	private List <OrderCountReportVo<String, Integer>> overallQueryList;
	private List <AddressReportVo<String>> addressQueryList;

	@EJB
	ReportServiceLocal reportService;

	@PostConstruct
	public void init(){
		 createAnimatedModels();
	}
	
	

	
	 private void createAnimatedModels() {
	       
	         
	       	dailyQueryModel = initDailyQueryModel();
	       	dailyQueryModel.setTitle("Daily Query");
	       	dailyQueryModel.setAnimate(true);
	       	dailyQueryModel.setLegendPosition("ne");
	       	Axis yAxis = dailyQueryModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        weeklyQueryModel = initWeeklyQueryModel();
	        weeklyQueryModel.setTitle("Weekly Query");
	        weeklyQueryModel.setAnimate(true);
	        weeklyQueryModel.setLegendPosition("ne");
	       	yAxis = weeklyQueryModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        monthlyQueryModel = initMonthlyQueryModel();
	        monthlyQueryModel.setTitle("Monthly Query");
	        monthlyQueryModel.setAnimate(true);
	        monthlyQueryModel.setLegendPosition("ne");
	       	yAxis =monthlyQueryModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        annualQueryModel = initAnnualQueryModel();
	        annualQueryModel.setTitle("Annual Query");
	        annualQueryModel.setAnimate(true);
	        annualQueryModel.setLegendPosition("ne");
	       	yAxis = annualQueryModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        
	        overallQueryModel = initOverallQueryModel();
	        overallQueryModel.setTitle("Overall Query");
	        overallQueryModel.setAnimate(true);
	        overallQueryModel.setLegendPosition("ne");
	       	yAxis =  overallQueryModel.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        
	        
	    }
	
	 private BarChartModel initDailyQueryModel(){
		 BarChartModel model = new BarChartModel();
		 ChartSeries order = new ChartSeries();
		 order.setLabel("Number Of Orders");
		 dailyQueryList = reportService.dailyQuery();
		 for (OrderCountReportVo<String, Integer> daily : dailyQueryList) {
			 order.set(daily.getGroupped(), daily.getQuantity());
		} {
			
		}
		 return model;
	 }
	 
	 private BarChartModel initWeeklyQueryModel(){
		 BarChartModel model = new BarChartModel();
		 ChartSeries order = new ChartSeries();
		 order.setLabel("Number Of Orders");
		 dailyQueryList = reportService.weeklyQuery();
		 for (OrderCountReportVo<String, Integer> weekly : weeklyQueryList) {
			 order.set(weekly.getGroupped(), weekly.getQuantity());
		} {
			
		}
		 return model;
	 }
	 
	 private BarChartModel initMonthlyQueryModel(){
		 BarChartModel model = new BarChartModel();
		 ChartSeries order = new ChartSeries();
		 order.setLabel("Number Of Orders");
		 dailyQueryList = reportService.monthlyQuery();
		 for (OrderCountReportVo<String, Integer> monthly : monthlyQueryList) {
			 order.set(monthly.getGroupped(), monthly.getQuantity());
		} {
			
		}
		 return model;
	 }
	 
	 private BarChartModel initAnnualQueryModel(){
		 BarChartModel model = new BarChartModel();
		 ChartSeries order = new ChartSeries();
		 order.setLabel("Number Of Orders");
		 dailyQueryList = reportService.annualQuery();
		 for (OrderCountReportVo<String, Integer> annual : annualQueryList) {
			 order.set(annual.getGroupped(), annual.getQuantity());
		} {
			
		}
		 return model;
	 }
	 
	 private BarChartModel initOverallQueryModel(){
		 BarChartModel model = new BarChartModel();
		 ChartSeries order = new ChartSeries();
		 order.setLabel("Number Of Orders");
		 dailyQueryList = reportService.overallQuery();
		 for (OrderCountReportVo<String, Integer> overall : overallQueryList) {
			 order.set(overall.getGroupped(), overall.getQuantity());
		} {
			
		}
		 return model;
	 }
	 
	 public void addAddressToTheMap() {
		 	addressQueryList = reportService.addressQuery();        
		 	

	        if (addressQueryList != null && !addressQueryList.isEmpty()) {
	        	for (AddressReportVo<String> address : addressQueryList) {
					
				
	            LatLng center = address.getAddress().
	            centerGeoMap = center.getLat() + "," + center.getLng();
	             
	            for (int i = 0; i < results.size(); i++) {
	                GeocodeResult result = results.get(i);
	                geoModel.addOverlay(new Marker(result.getLatLng(), result.getAddress()));
	            }
	        }
	    }
	 }
}
