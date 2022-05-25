package app.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Model {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    List<Task> tasks = new ArrayList<Task>();

    private Connection conn;

    public Model() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Event.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Task> getTasks() {
        tasks.clear();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from tasks t");
            while (rs.next()) {
                Task task = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), "1".equals(rs.getString(5)), "1".equals(rs.getString(6)), rs.getInt(7),rs.getString(8));
                System.out.println("tsk type in rs"+ task.getType());
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    /**
     * deadline
     *
     * @return
     */
    public List<Task> getNoDeadLineTasks(String day) {
        List<Task> t = new ArrayList<Task>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
        try {
            // Date today = sdf.parse(day);
            for (Task task : tasks) {
                if (task.getPreferDate().equals(day) || (task.getDeadlineDate() == null && task.getPreferDate() == dateFormat.format(date))) {
                    t.add(task);
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * deadline
     *
     * @return
     */
    public List<Task> getDeadLineTasks(String day) {
        List<Task> t = new ArrayList<Task>();
        try {
            Date today = sdf.parse(day);
            for (Task task : tasks) {
                if (task.getDeadlineDate() != null) {
                    Date deadlineDate = sdf.parse(task.getDeadlineDate());
                    long bt = (deadlineDate.getTime() - today.getTime()) / (60 * 60 * 24 * 1000);
                    if (bt >= 0 && bt <= 3) {
                        t.add(task);
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public boolean add(Task task) {
        tasks.add(task);
        try {
            String sql = "insert into tasks(preferDate,deadlineDate,description,isNotification,isFinish,priority,type) " + "values (?,?,?,?,?,?,?)";
            // String sql =
            // "insert into tasks(preferDate,deadlineDate,description,isNotification,isFinish,priority) "
            // +
            // "values ('"+task.getPreferDate()+"','"+task.getDeadlineDate()+"','"+task.getDescription()+"','"+task.isNotification()+"','"+task.isFinish()+"',"+task.getPriority()+")";
            System.out.println(sql);
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setObject(1, task.getPreferDate());
            prepareStatement.setObject(2, task.getDeadlineDate());
            prepareStatement.setObject(3, task.getDescription());
            prepareStatement.setObject(4, task.isNotification());
            prepareStatement.setObject(5, task.isFinish());
            prepareStatement.setObject(6, task.getPriority());
            prepareStatement.setObject( 7, task.getType());
            System.out.println("type "+ task.getType());

            prepareStatement.execute();
            prepareStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Task task) {
        try {
            // String sql =
            // "update tasks set preferDate='"+task.getPreferDate()+"',deadlineDate='"+task.getDeadlineDate()+"',description='"+task.getDescription()+"',isNotification='"+task.isNotification()+"',isFinish='"+task.isFinish()+"',priority="+task.getPriority()+" where id="+task.getId();
            String sql = "update tasks set preferDate=?,deadlineDate=?,description=?,isNotification=?,isFinish=?,priority=? ,type=? where id=?";
            PreparedStatement prepareStatement = conn.prepareStatement(sql);
            prepareStatement.setObject(1, task.getPreferDate());
            prepareStatement.setObject(2, task.getDeadlineDate());
            prepareStatement.setObject(3, task.getDescription());
            prepareStatement.setObject(4, task.isNotification());
            prepareStatement.setObject(5, task.isFinish());
            prepareStatement.setObject(6, task.getPriority());
            prepareStatement.setObject( 7, task.getType());
            prepareStatement.setObject(8, task.getId());

            prepareStatement.execute();
            prepareStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Task> search(int day, String order) {
        List<Task> tasks = new ArrayList<Task>();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
        String sql = "select * from tasks t where preferDate <= '" + sdf.format(c.getTime()) + "'  and preferDate >= '"+dateFormat.format(date)+"' " + (order == null ? "" : " order by " + order);
        try {
            System.out.println(sql);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Task task = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), "1".equals(rs.getString(5)), "1".equals(rs.getString(6)), rs.getInt(7),rs.getString(8));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void delete(Task task) {
        try {
            conn.createStatement().execute("delete from tasks where id = " + task.getId());
            tasks.remove(task);
        } catch (Exception e) {
        }
    }

}
