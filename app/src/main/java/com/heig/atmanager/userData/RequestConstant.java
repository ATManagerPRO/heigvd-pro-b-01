package com.heig.atmanager.userData;

public final class RequestConstant {

    // /!\ All URL ends with a slash
    public final static String BASE_URL = "https://atmanager.gollgot.app/api/v1/";

    public final static String DONE_EXTENSION = "done/";
    public final static String FAVORITE_EXTENSION = "favorite/";
    public final static String FOLDERS_EXTENSION = "folders/";
    public final static String TODOLISTS_EXTENSION = "todolists/";
    public final static String GOALS_EXTENSION = "goals/";
    public final static String TODOS_EXTENSION = "todos/";
    public final static String TAGS_EXTENSION = "tags/";
    public final static String TODAY_EXTENSION = "today/";
    public final static String GOAL_TODOS = "goaltodos/";



    // Data keywords
    // - Folders
    private static final String FOLDERS_KEY       = "folders";
    private static final String FOLDERS_ID        = "id";
    private static final String FOLDERS_TITLE     = "label";
    private static final String FOLDERS_TASKLISTS = "todolist";
    // - TaskLists
    private static final String TASKLISTS_KEY       = "todoLists";
    private static final String TASKLISTS_ID        = "id";
    private static final String TASKLISTS_TITLE     = "title";
    private static final String TASKLISTS_FOLDER_ID = "folder_id";
    // - Tasks
    private static final String TASK_KEY           = "todos";
    private static final String TASK_ID            = "id";
    private static final String TASK_TASKLIST_ID   = "todo_list_id";
    private static final String TASK_TITLE         = "title";
    private static final String TASK_DESCRIPTION   = "details";
    private static final String TASK_FAVORITE      = "favorite";
    private static final String TASK_DUE_DATE      = "dueDate";
    private static final String TASK_DONE_DATE     = "dateTimeDone";
    private static final String TASK_REMINDER_DATE = "reminderDateTime";
    private static final String TASK_ARCHIVED = "archived";
    // - Goals
    private static final String GOAL_KEY               = "goals";
    private static final String GOAL_ID                = "goal_id";
    private static final String GOAL_DUE_DATE          = "endDate_goal";
    private static final String GOAL_INTERVAL          = "interval_id";
    private static final String GOAL_INTERVAL_NUMBER   = "intervalValue";
    private static final String GOAL_TODO_CREATED_DATE = "created_at";
    private static final String GOAL_TODO_UPDATED_DATE = "updated_at";
    private static final String GOAL_LABEL             = "label";
    private static final String GOAL_QUANTITY          = "quantity";
    // - GoalTodo
    private static final String GOAL_TODO_KEY           = "goalTodos";
    private static final String GOAL_TODO_ID            = "id";
    private static final String GOAL_TODO_QUANTITY_DONE = "quantityDone";
    private static final String GOAL_TODO_DONE_DATE     = "dateTimeDone";
    private static final String GOAL_TODO_DUE_DATE      = "dueDate";
}
