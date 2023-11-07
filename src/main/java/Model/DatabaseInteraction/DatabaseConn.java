package Model.DatabaseInteraction;
import Controller.DataLoggingHandler.DietLoggingController;

import java.sql.*;
public class DatabaseConn {
    private static final String url = "jdbc:mysql://localhost:3306/fitnessjournal?useSSL=false&serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "Abby6!644";
    private static Connection con = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getDatabaseConn(){
        return con;

			/*
			String stm = "show tables";
			PreparedStatement p = con.prepareStatement(stm);
			ResultSet r = p.executeQuery();
			while (r.next()){
				System.out.println(r.getString(1));
			}

			 */
        }



}


