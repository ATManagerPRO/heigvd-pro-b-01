package com.heig.atmanager.userData;


public final class RequestConstant {

    // /!\ All URL begins with a slash
    private final static String BASE_URL = "https://atmanager.gollgot.app/api/v1";

    public final static String DONE_EXTENSION = "/done";
    public final static String FAVORITE_EXTENSION = "/favorite";
    private final static String FOLDERS_EXTENSION = "/folders";
    public final static String TODOLISTS_EXTENSION = "/todolists";
    private final static String GOALS_EXTENSION = "/goals";
    public final static String TODOS_EXTENSION = "/todos";
    private final static String TAGS_EXTENSION = "/tags";
    public final static String TODAY_EXTENSION = "/today";
    public final static String GOAL_TODOS_EXTENSION = "/goaltodos";
    private final static String USER__EXTENSION = "/users";

    // Data keywords
    // - Folders
    public static final String FOLDERS_KEY       = "folders";
    public static final String FOLDERS_ID        = "folder_id";
    public static final String FOLDERS_LABEL     = "label";
    public static final String FOLDERS_TASKLISTS = "todolist";
    // - TaskLists
    public static final String TASKLISTS_KEY       = "todoLists";
    public static final String TASKLISTS_ID        = "todo_list_id";
    public static final String TASKLISTS_TITLE     = "title";
    public static final String TASKLISTS_FOLDER_ID = "folder_id";
    // - Tasks
    public static final String TASK_KEY           = "todos";
    public static final String TASK_ID            = "id";
    public static final String TASK_TASKLIST_ID   = "todo_list_id";
    public static final String TASK_TITLE         = "title";
    public static final String TASK_DESCRIPTION   = "details";
    public static final String TASK_FAVORITE      = "favorite";
    public static final String TASK_DUE_DATE      = "dueDate";
    public static final String TASK_DONE_DATE     = "dateTimeDone";
    public static final String TASK_REMINDER_DATE = "reminderDateTime";
    public static final String TASK_ARCHIVED      = "archived";
    public static final String TODO_ID            = "todo_id";
    // - Goals
    public static final String GOAL_KEY               = "goals";
    public static final String GOAL_ID                = "goal_id";
    public static final String GOAL_DUE_DATE          = "endDate_goal";
    public static final String GOAL_INTERVAL          = "interval_id";
    public static final String GOAL_INTERVAL_NUMBER   = "intervalValue";
    public static final String GOAL_TODO_CREATED_DATE = "created_at";
    public static final String GOAL_TODO_UPDATED_DATE = "updated_at";
    public static final String GOAL_LABEL             = "label";
    public static final String GOAL_QUANTITY          = "quantity";
    public static final String GOAL_INTERVAL_LABEL    = "intervalLabel";
    public static final String GOAL_END_DATE          = "endDate";
    public static final String GOAL_BEGIN_DATE        = "beginDate";
    // - GoalTodo
    public static final String GOAL_TODO_KEY           = "goalTodos";
    public static final String GOAL_TODO_ID            = "id";
    public static final String GOAL_TODO_QUANTITY_DONE = "quantityDone";
    public static final String GOAL_TODO_DONE_DATE     = "dateTimeDone";
    public static final String GOAL_TODO_DUE_DATE      = "dueDate";

    // - Tags
    public static final String TAG_KEY                 = "tags";
    public static final String TAG_LABEL               = "label";

    // - Misc
    public static final String DONE                    = "done";
    public static final String ID                      = "id";

    // URL
    public final static String TASK_URL = BASE_URL + TODOS_EXTENSION;
    public final static String FOLDER_URL = BASE_URL + FOLDERS_EXTENSION;
    public final static String TODOLISTS_URL = BASE_URL + TODOLISTS_EXTENSION;
    public final static String GOAL_URL = BASE_URL + GOALS_EXTENSION;
    public final static String TAGS_URL = BASE_URL +TAGS_EXTENSION;
    public final static String USER_BASE_URL = BASE_URL + USER__EXTENSION;

}
