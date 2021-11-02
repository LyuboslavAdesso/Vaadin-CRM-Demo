package com.example.application.views.dashboard;

import com.example.application.data.service.CrmService;
import com.example.application.views.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = MainView.class) // Parent view
@PageTitle("Dashboard | Vaadin CRM")
public class DashboardView extends VerticalLayout {

    private final CrmService service;

    public DashboardView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getContactStats()/*, getCompaniesChart()*/);
    }

    private Component getContactStats() {
        Span stats = new Span(String.format("%d contacts", service.countContacts()));
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }

    //Vaadin charts are not free for commercial purpose
    private Chart getCompaniesChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        service.findAllCompanies()
                .forEach(company -> dataSeries.add(new DataSeriesItem(company.getName(), company.getEmployeeCount())));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}