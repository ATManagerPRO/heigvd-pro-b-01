package com.heig.atmanager.userData;

import android.util.Log;

import com.heig.atmanager.Interval;
import com.heig.atmanager.Utils;
import com.heig.atmanager.folders.Folder;
import com.heig.atmanager.goals.Goal;
import com.heig.atmanager.goals.GoalTodo;
import com.heig.atmanager.taskLists.TaskList;
import com.heig.atmanager.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class User {

    private static final String TAG = "User";

    private long userId;
    private String userName;
    private String googleToken;
    private String backEndToken;
    private String email;
    private ArrayList<Goal> goals;
    private ArrayList<TaskList> taskLists;
    private ArrayList<Task> tasks;
    private ArrayList<String> tags;
    private ArrayList<Folder> folders;

    public User(String userName, String googleToken, String email) {
        this.userName = userName;
        this.googleToken = googleToken;
        this.email = email;
        this.goals       = new ArrayList<>();
        this.taskLists   = new ArrayList<>();
        this.tasks       = new ArrayList<>();
        this.folders     = new ArrayList<>();
        this.tags        = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addTaskList(TaskList taskList) {
        taskLists.add(taskList);
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public int getTotalTasksForDay(Date day) {
        int totalTasks = 0;

        for (Task task : tasks) {
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day)) {
                totalTasks++;
            }
        }

        return totalTasks;
    }

    private boolean isSameSimpleDate(Date d1, Date d2) {
        if(d1 == null || d2 == null)
            return false;

        Calendar calendar_d1 = Calendar.getInstance();
        Calendar calendar_d2 = Calendar.getInstance();
        calendar_d1.setTime(d1);
        calendar_d2.setTime(d2);
        return calendar_d1.get(Calendar.YEAR) == calendar_d2.get(Calendar.YEAR) &&
                calendar_d1.get(Calendar.MONTH) == calendar_d2.get(Calendar.MONTH) &&
                calendar_d1.get(Calendar.DAY_OF_MONTH) == calendar_d2.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isBetweenDates(Date d, Date startDate, Date endDate) {
        Calendar calendar_d = Calendar.getInstance();
        Calendar calendar_startDate = Calendar.getInstance();
        Calendar calendar_endDate = Calendar.getInstance();
        calendar_d.setTime(d);
        calendar_startDate.setTime(startDate);
        calendar_endDate.setTime(endDate);
        return calendar_d.getTime().before(calendar_endDate.getTime()) && calendar_d.getTime().after(calendar_startDate.getTime());
    }

    private  Date getDateXDaysAgo(int numberOfDaysAgo){
        Calendar cal=Calendar.getInstance();
        int currentDay=cal.get(Calendar.DAY_OF_YEAR);
        //Set the date to 2 days ago
        cal.set(Calendar.DAY_OF_YEAR, currentDay-numberOfDaysAgo);

        return cal.getTime();

    }

    public ArrayList<Task> getTasksForDay(Date day) {
        ArrayList<Task> tasksForDay = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null && isSameSimpleDate(task.getDueDate(), day)) {
                tasksForDay.add(task);
            }
        }

        return tasksForDay;
    }

    public ArrayList<Task> getHomeViewTasks() {
        Date today = Calendar.getInstance().getTime();
        ArrayList<Task> homeViewTasks = new ArrayList<>();

        for (Task task : tasks) {
            Log.d(TAG, "getHomeViewTasks: " + task.getTitle() + " : " + task.isDone());
            if (!task.isDone() && (task.getDueDate() == null ||
                    isSameSimpleDate(task.getDueDate(), today))) {
                homeViewTasks.add(task);
            }
        }

        return homeViewTasks;
    }

    public ArrayList<Task> getTasksDone() {
        ArrayList<Task> tasksForDay = new ArrayList<>();

        for (Task task : tasks) {
            if (task.isDone()) {
                tasksForDay.add(task);
            }
        }

        return tasksForDay;
    }

    public Goal getGoal(long goal_id) {
        for(Goal goal : goals) {
            if(goal.getId() == goal_id)
                return goal;
        }

        return null;
    }

    public TaskList getTaskList(long tasklist_id) {
        for(TaskList taskList : taskLists) {
            if(taskList.getId() == tasklist_id)
                return taskList;
        }

        for(Folder folder : folders){
            for(TaskList taskList : folder.getTaskLists()){
                if(taskList.getId() == tasklist_id)
                    return taskList;
            }
        }

        return null;
    }

    public ArrayList<TaskList> getAllTaskLists() {
        ArrayList<TaskList> list = new ArrayList<TaskList>();
        for(Folder folder:folders){
            for(TaskList taskList : folder.getTaskLists()){
                list.add(taskList);
            }
        }
        list.addAll(taskLists);
        return list;
    }

    public boolean hasGoal(long goal_id) {
        return getGoal(goal_id) != null;
    }

    public boolean hasTaskList(long tasklist_id) {
        return getTaskList(tasklist_id) != null;
    }

    public ArrayList<GoalTodo> getGoalTodosOfDay(Date day) {
        ArrayList<GoalTodo> goalTodos = new ArrayList<>();
        for(Goal goal : goals) {
            for(GoalTodo goalTodo : goal.getGoalTodos()) {
                if(isSameSimpleDate(goalTodo.getDoneDate(), day)) {
                    goalTodos.add(goalTodo);
                }
            }
        }

        return goalTodos;
    }

    public ArrayList<Task> getTasksForLastWeek() {
        ArrayList<Task> tasksForLastWeek = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null && isBetweenDates(task.getDueDate(), getDateXDaysAgo(7), Calendar.getInstance().getTime())) {
                tasksForLastWeek.add(task);
            }
        }

        return tasksForLastWeek;
    }

    public ArrayList<Task> getTasksForLastMonth() {
        ArrayList<Task> tasksForLastMonth = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null && isBetweenDates(task.getDueDate(), getDateXDaysAgo(30), Calendar.getInstance().getTime())) {
                tasksForLastMonth.add(task);
            }
        }

        return tasksForLastMonth;
    }

    public ArrayList<Task> getTasksForLastYear() {
        ArrayList<Task> tasksForLastYear = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDueDate() != null && isBetweenDates(task.getDueDate(), getDateXDaysAgo(365), Calendar.getInstance().getTime())) {
                tasksForLastYear.add(task);
            }
        }

        return tasksForLastYear;
    }

    public ArrayList<GoalTodo> getDailyGoalTodo(){
        return getXlyGoalTodo(Interval.DAY);
    }

    public ArrayList<GoalTodo> getWeeklyGoalTodo(){
        return getXlyGoalTodo(Interval.WEEK);
    }

    public ArrayList<GoalTodo> getMonthlyGoalTodo(){
        return getXlyGoalTodo(Interval.MONTH);
    }

    public ArrayList<GoalTodo> getYearlyGoalTodo(){
        return getXlyGoalTodo(Interval.YEAR);
    }

    private ArrayList<GoalTodo> getXlyGoalTodo(Interval interval){
        ArrayList<GoalTodo> result = new ArrayList<>();
        Calendar current = Calendar.getInstance();
        Date currentDate = new Date();
        current.setTime(currentDate);

        for(Goal g : goals){
            for(GoalTodo gt : g.getGoalTodos()){
                switch(interval) {
                    case YEAR:
                        if (Utils.getYear(currentDate) == Utils.getYear(gt.getDoneDate()) && g.getInterval() == Interval.YEAR)
                            result.add(gt);
                        break;
                    case MONTH:
                        if(Arrays.equals(Utils.getMonthYear(gt.getDoneDate()), Utils.getMonthYear(currentDate))
                                && g.getInterval() == Interval.MONTH)
                            result.add(gt);
                        break;
                    case WEEK:
                        if(Arrays.equals(Utils.getWeekMonthYear(gt.getDoneDate()), Utils.getWeekMonthYear(currentDate))
                                && g.getInterval() == Interval.WEEK)
                            result.add(gt);
                        break;
                    case DAY:
                        if(Arrays.equals(Utils.getDayWeekMonthYear(gt.getDoneDate()), Utils.getDayWeekMonthYear(currentDate))
                                && g.getInterval() == Interval.DAY)
                            result.add(gt);
                        break;
                    default: break;
                }
            }
        }

        return result;
    }
    
    public void addTag(String tagName){
        tags.add(tagName);
    }

    public String getBackEndToken() {
        return backEndToken;
    }

    public void setBackEndToken(String backEndToken) {
        this.backEndToken = backEndToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }
}