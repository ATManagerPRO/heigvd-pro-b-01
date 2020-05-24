package com.heig.atmanager.stats;

import android.annotation.SuppressLint;
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
import com.heig.atmanager.Interval;
import com.heig.atmanager.MainActivity;
import com.heig.atmanager.R;
import com.heig.atmanager.Utils;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.tasks.Task;
import com.heig.atmanager.userData.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author : Mattei Simon
 * Date   : 09.04.2020
 */
public class StatsFragment  extends Fragment {

    public static final String FRAG_STATS_ID = "Goals_Fragment";

    private AnyChartView pieChartTasksView;
    private AnyChartView columnChartTasksView;
    private AnyChartView pieChartGoalsView;

    private Cartesian columnChartTasks;
    private Pie pieChartTasks;
    private Pie pieChartGoals;

    private static String bgColor;

    private User user;
    private ArrayList<Task> tasks;
    private ArrayList<Task> tasksWithoutDate;
    private ArrayList<GoalTodo> goals;


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stats, container, false);

        String[] items = new String[]{getResources().getString(R.string.goals_today),
                getResources().getString(R.string.goals_week),
                getResources().getString(R.string.goals_month),
                getResources().getString(R.string.goals_year)};

        //DropDown menu
        Spinner menu = v.findViewById(R.id.menuXML);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(this.getActivity()),
                                                    android.R.layout.simple_spinner_dropdown_item, items);
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

        //init user and charts
        user = MainActivity.getUser();
        bgColor = "#" + getResources().getString(R.color.colorPrimary).substring(3);
        initCharts(v);

        return v;
    }

    /**
     * Rafraichi les graphes
     * @param interval temps
     */
    private void makeCharts(Interval interval){

        String lineChartLegend = "";
        tasksWithoutDate = user.getTasksWithoutDate();

        //keep interval tasks
        switch(interval){
            case DAY:
                tasks = user.getTasksForDay(new Date());
                goals = user.getDailyGoalTodo();
                lineChartLegend = "Hours";
                break;
            case WEEK:
                tasks = user.getTasksForLastWeek();
                goals = user.getWeeklyGoalTodo();
                lineChartLegend = "Days";
                break;
            case MONTH:
                tasks = user.getTasksForLastMonth();
                goals = user.getMonthlyGoalTodo();
                lineChartLegend = "Days";
                break;
            case YEAR:
                tasks = user.getTasksForLastYear();
                goals = user.getYearlyGoalTodo();
                lineChartLegend = "Months";
                break;
        }

        //make charts
        makePieChartTasks(interval);
        makeColumnChartTasks(interval,lineChartLegend);
        makePieChartGoals(interval);
    }

    /**
     * Pie chart for tasks
     * @param interval period
     */
    private void makePieChartTasks(Interval interval){

        APIlib.getInstance().setActiveAnyChartView(pieChartTasksView);

        List<DataEntry> data = new ArrayList<>();
        int tasksDone = 0, tasksToDo = 0;

        pieChartTasks.title("Past " + Utils.firstLetterCapped(interval.name()) + "´s " + Task.class.getSimpleName() + "s"); //title

        for(Task t : tasks){
            if(t.isDone())
                ++tasksDone;
            else
                ++tasksToDo;
        }

        if(tasksDone != 0 || tasksToDo != 0) {
            data.add(new ValueDataEntry("Done", tasksDone));
            data.add(new ValueDataEntry("Todo", tasksToDo));
            pieChartTasks.data(data);
        } else {
            pieChartTasks.data((com.anychart.data.View) null);
        }

    }

    /**
     * Will setup column task graph according to period
     * @param interval period
     * @param lineChartLegend unité de temps selon l'interval choisi
     */
    private void makeColumnChartTasks(Interval interval, String lineChartLegend){

        APIlib.getInstance().setActiveAnyChartView(columnChartTasksView);

        columnChartTasks.title("Tasks done the past " + Utils.firstLetterCapped(interval.name()));
        columnChartTasks.xAxis(0).title(lineChartLegend + " ago");

        if(interval == Interval.DAY)
            columnChartTasks.xAxis(0).drawLastLabel(false);
        else
            columnChartTasks.xAxis(0).drawLastLabel(true);

        List<DataEntry> data = calculateValuesLineChart(interval);

        columnChartTasks.removeSeries(0);
        columnChartTasks.column(data);

    }

    /**
     * Calculates data according to period for column chart
     * @param interval period
     * @return table of values x y
     */
    private List<DataEntry> calculateValuesLineChart(Interval interval) {
        List<DataEntry> result = new ArrayList<>();

        int[] values = new int[0];

        switch(interval) {

            case DAY:
                values = new int[24];
                getDoneTasks(values,Calendar.HOUR_OF_DAY);
                break;

            case WEEK:
                values = new int[7];
                getDoneTasks(values,Calendar.DAY_OF_WEEK);
                break;

            case MONTH:
                values = new int[31];
                getDoneTasks(values,Calendar.DAY_OF_MONTH);
                break;

            case YEAR:
                values = new int[12];
                getDoneTasks(values,Calendar.MONTH);
                break;
        }


        for(int i = 0; i < values.length; ++i){
            result.add(new ValueDataEntry(i,values[i]));
        }

        return result;
    }

    /**
     * Calculates what tasks where done and where
     * @param values table of data
     * @param calendarInterval Interval used by the calendar
     */
    private void getDoneTasks(int[] values, int calendarInterval){

        Calendar current = Calendar.getInstance();
        int currentPeriod = current.get(calendarInterval);
        tasks.addAll(tasksWithoutDate);

        for (Task t : tasks) {
            if (t.isDone()) {
                if(t.getDoneDate() != null) {
                    current.setTime(t.getDoneDate());
                    int taskPeriod = current.get(calendarInterval);
                    if (taskPeriod > currentPeriod)
                        ++values[values.length + currentPeriod - taskPeriod];
                    else
                        ++values[currentPeriod - taskPeriod];
                }
            }
        }
    }

    /**
     * Data change of Pie chart for goals. Called every time we choose a period
     * @param interval period
     */
    private void makePieChartGoals(Interval interval){

        APIlib.getInstance().setActiveAnyChartView(pieChartGoalsView);

        List<DataEntry> data = new ArrayList<>();
        float goalsDone = 0, goalsToDo = 0;

        pieChartGoals.title(Utils.firstLetterCapped(interval.getAdverb()) + " " + Goal.class.getSimpleName() + "s");

        for(GoalTodo gt : goals){
            float p = gt.getPercentage();
            goalsDone += p;
            goalsToDo += 100-p;
        }

        if(goalsDone != 0 || goalsToDo != 0){
            data.add(new ValueDataEntry("Done", goalsDone));
            data.add(new ValueDataEntry("Todo", goalsToDo));
            pieChartGoals.data(data);
        } else {
            pieChartGoals.data((com.anychart.data.View) null);
        }

    }

    /**
     * Initiations of graphics views, including graphics themselves, for what is not changing
     * according to period
     * @param v View
     */
    private void initCharts(View v){

        //PieChartTasks
        pieChartTasksView = v.findViewById(R.id.pie_chart_tasks);
        APIlib.getInstance().setActiveAnyChartView(pieChartTasksView);
        pieChartTasks = AnyChart.pie();
        pieChartTasksView.setBackgroundColor(bgColor);
        pieChartTasksView.setChart(pieChartTasks);
        pieChartTasks.palette(new String[]{"#de390b","#2c53c7"}); //colors
        pieChartTasks.background().fill(bgColor); //bgcolor
        pieChartTasks.noData().label().enabled(true);
        pieChartTasks.noData().label().text("Could not retrieve any tasks!");
        pieChartTasks.animation(true);

        //LineChartTasks
        columnChartTasksView = v.findViewById(R.id.line_chart_tasks);
        APIlib.getInstance().setActiveAnyChartView(columnChartTasksView);
        columnChartTasks = AnyChart.column();
        columnChartTasksView.setProgressBar(v.findViewById(R.id.progress_bar));
        columnChartTasksView.setBackgroundColor(bgColor);
        columnChartTasksView.setChart(columnChartTasks);
        columnChartTasks.noData().label().enabled(true);
        columnChartTasks.noData().label().text("Could not retrieve any tasks!");
        columnChartTasks.background().fill(bgColor);
        columnChartTasks.animation(true);
        columnChartTasks.yScale().ticks().allowFractional(false);
        columnChartTasks.xScale().inverted(true);
        columnChartTasks.xAxis(0).labels().fontSize(13);

        //PieChartGoals
        pieChartGoalsView = v.findViewById(R.id.pie_chart_goals);
        APIlib.getInstance().setActiveAnyChartView(pieChartGoalsView);
        pieChartGoals = AnyChart.pie();
        pieChartGoalsView.setBackgroundColor(bgColor);
        pieChartGoalsView.setChart(pieChartGoals);
        pieChartGoals.palette(new String[]{"#80EB5A","#FF9745"}); //Colors
        pieChartGoals.background().fill(bgColor); //bgColor
        pieChartGoals.noData().label().enabled(true);
        pieChartGoals.noData().label().text("Could not retrieve any goals!");
        pieChartGoals.animation(true);
    }


}
