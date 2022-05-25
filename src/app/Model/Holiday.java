package app.Model;

public class Holiday {
    private int id;

    private String preferDate;
    private String deadlineDate;

    private String description;
    private boolean isNotification;
    private boolean isFinish;
    private int priority;
    private String type;


    public Holiday() {
        super();
    }

    public Holiday(int id, String preferDate, String deadlineDate, String description, boolean isNotification, boolean isFinish, int priority,String type) {
        super();
        this.id = id;
        this.preferDate = preferDate;
        this.deadlineDate = deadlineDate;
        this.description = description;
        this.isNotification = isNotification;
        this.isFinish = isFinish;
        this.priority = priority;
        this.type=type;
    }

    public Holiday(String preferDate, String deadlineDate, String description, boolean isNotification, int priority,String type) {
        super();
        this.preferDate = preferDate;
        this.deadlineDate = deadlineDate;
        this.description = description;
        this.isNotification = isNotification;
        this.priority = priority;
        this.type=type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreferDate() {
        return preferDate;
    }

    public void setPreferDate(String preferDate) {
        this.preferDate = preferDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean isNotification) {
        this.isNotification = isNotification;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Task [id=" + id + ", preferDate=" + preferDate + ", deadlineDate=" + deadlineDate + ", description=" + description + ", isNotification="
                + isNotification + ", isFinish=" + isFinish + ", priority=" + priority + ", type=" + type +"]";
    }

}
