import java.sql.*;

public class Db {

    final String URL ="jdbc:postgresql://localhost:5432/learning";
    final String USER = "postgres";
    final String PASSWORD ="postgres";

    Connection connection = null;
    ResultSet rs = null;
    ResultSetMetaData rsmd = null;


    public Db() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Sikeres csatlakozás!");
        } catch (SQLException e) {
            System.out.println("Sikertelen csatlakozás!");
            e.printStackTrace();
        }
    }


    public void addUser(String name,String password,String email) {
        String sql = "INSERT INTO account (user_name,password,email,created_on) VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,email);
            preparedStatement.setTimestamp(4,getCurrentTimeStamp());
            int succesfull = preparedStatement.executeUpdate();
            System.out.println(succesfull + " db sor sikeresen módosítva!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static java.sql.Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public void getALLColumns () {
        String sql = "Select * FROM account";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           rs =  preparedStatement.executeQuery();
           rsmd = rs.getMetaData();
           int countColumn = rsmd.getColumnCount();

           for(int i = 1; i<=countColumn;i++) {
               System.out.print(rsmd.getColumnName(i)+ " | ");
           }

        } catch (SQLException e) {
            System.out.println("Sikertelen lekérdezés!");
            e.printStackTrace();
        }
    }

    public void getAllAccount() {
        String sql = "Select * FROM account";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            rs =  preparedStatement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("user_id")+ " | ");
                System.out.print(rs.getString("user_name")+ " | ");
                System.out.print(rs.getString("password")+ " | ");
                System.out.print(rs.getString("email")+ " | ");
                System.out.print(rs.getString("created_on")+ " | ");
                System.out.print(rs.getString("last_login"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
