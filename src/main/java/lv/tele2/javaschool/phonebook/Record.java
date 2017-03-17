package lv.tele2.javaschool.phonebook;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Dimitrijs Fedotovs <a href="http://www.bug.guru">www.bug.guru</a>
 * @version 1.0
 * @since 1.0
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private List<String> phoneList = new ArrayList<>();

    public Record(String name, String... phones) {
        this.name = name;
        Collections.addAll(this.phoneList, phones);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneList() {
        return phoneList;
    }

    public void save() throws SQLException {
        Connection con = Main.getDatabase().getConnection();
                try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from record")) {
            rs.next();
            int maxId = rs.getInt(1);
            id = maxId + 1;
                }
        try (Statement stmt = Main.getDatabase().getConnection().createStatement()) {
            stmt.executeUpdate("insert into record (id, name) values" + "(" + id + ",'" + name + "')");
                for (String p : phoneList) {
                    stmt.executeUpdate("insert into phone (record_id, phone) values" + "(" + id + ",'" + p + "')");
                }
        }
    }

    public static List<Record> findAll() throws SQLException {
        List<Record> result = new ArrayList<>();
        Connection con = Main.getDatabase().getConnection();
        try (Statement stmt = Main.getDatabase().getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from record"); {
                while (rs.next()) {
                    Record r = construct(rs);
                    result.add(r);
                }
            }
            return result;
        }
    }

    private static Record construct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Record r = new Record(name);
        r.id = id;
        return r;
    }
    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ", phones=" + phoneList;
    }
}