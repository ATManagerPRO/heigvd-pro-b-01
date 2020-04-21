package com.heig.atmanager.stats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.heig.atmanager.DummyData;
import com.heig.atmanager.Interval;
import com.heig.atmanager.R;
import com.heig.atmanager.UserViewModel;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author : Stéphane Bottin, Mattei Simon
 * Date   : 09.04.2020
 * TODO : GRAPH DON'T REFRESH PRORERLY
 */
public class StatsFragment  extends Fragment {

    private Spinner menu;
    private static final String[] items = new String[]{"Today","This Week","This Month","This Year"};

    private AnyChartView pieChartTasks;
    private AnyChartView lineChartTasks;
    private AnyChartView pieChartGoals;

    private static String bgColor;

    private UserViewModel user;
    private ArrayList<Task> tasks;
    private ArrayList<Goal> goals;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        //DropDown menu
        menu = v.findViewById(R.id.menuXML);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getActivity()), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter);
        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0 : makeCharts(Interval.DAY); break;
                    case 1 : makeCharts(Interval.WEEK); break;
                    case 2 : makeCharts(Interval.MONTH); break;
                    case 3 : makeCharts(Interval.YEAR); break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Charts
        user = DummyData.getUser();
        //TODO : get this by values.colors, not working for some reason
        bgColor = "#F1F1F1";

        pieChartTasks = v.findViewById(R.id.pie_chart_tasks);
        pieChartTasks.setBackgroundColor(bgColor);
        lineChartTasks = v.findViewById(R.id.line_chart_tasks);
        lineChartTasks.setProgressBar(v.findViewById(R.id.progress_bar));
        lineChartTasks.setBackgroundColor(bgColor);
        pieChartGoals = v.findViewById(R.id.pie_chart_goals);
        pieChartGoals.setBackgroundColor(bgColor);

        return v;
    }

    private void makeCharts(Interval interval){

        //TODO : Select goals/Tasks based on Interval
        tasks = user.getTasks().getValue();
        goals = user.getGoals().getValue();

        APIlib.getInstance().setActiveAnyChartView(pieChartTasks);
        makePieChartTasks();
        APIlib.getInstance().setActiveAnyChartView(lineChartTasks);
        makeLineChartTasks(interval);
        APIlib.getInstance().setActiveAnyChartView(pieChartGoals);
        makePieChartGoals();
    }

    private void makePieChartTasks(){

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        int tasksDone = 0, tasksToDo = 0;

        for(Task t : tasks){
            if(t.isDone())
                ++tasksDone;
            else
                ++tasksToDo;
        }

        //since all tasks are not done, im adding 1 just to see colors TODO: remove 1
        data.add(new ValueDataEntry("Done", tasksDone + 1));
        data.add(new ValueDataEntry("Todo", tasksToDo));

        pie.palette(new String[]{"#3F58FF","#FF2E2E"}); //colors
        pie.data(data); //data
        pie.title(Task.class.getSimpleName()+ "s"); //title
        pie.background().fill(bgColor);

        pieChartTasks.setChart(pie);
    }

    private void makeLineChartTasks(Interval interval){

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Tasks done last " + interval.toString().toLowerCase());

        //TODO : set title and stuff accordingly to interval
        cartesian.xAxis(0).title("Hours/Days/Months");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new ValueDataEntry(1,1));
        seriesData.add(new ValueDataEntry(2,2));
        seriesData.add(new ValueDataEntry(3,3));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(false);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        cartesian.background().fill(bgColor);

        lineChartTasks.setChart(cartesian);
    }

    private void makePieChartGoals(){

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        int tasksDone = 0, tasksToDo = 0;

        //TODO : use goals
        for(Task t : tasks){
            if(t.isDone())
                ++tasksDone;
            else
                ++tasksToDo;
        }

        data.add(new ValueDataEntry("Done", tasksDone + 1));
        data.add(new ValueDataEntry("Todo", tasksToDo));

        pie.palette(new String[]{"#80EB5A","#FF9745"}); //colors
        pie.data(data); //data
        pie.title(Goal.class.getSimpleName()+ "s"); //title
        pie.background().fill(bgColor);

        pieChartGoals.setChart(pie);
    }


}
