//import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class DB {
	@SuppressWarnings("finally")
	public static Connection connect() {
		Connection c = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:BankAccountDB.db");
			// System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			return c;
		}
	}

	public static void insert(String act, double amount, String type, String description, String date, double balance) {
		// This method puts data into a database and update the balance.
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			double newBalance = balance + amount;
			String sql = "insert into accountOperations values(" + "\'" + act + "\', " + amount + ", " + "\'" + type
					+ "\', " + "\'" + description + "\', " + "\'" + date + "\'" + ");";
			st.executeUpdate(sql);
			String sql1 = "delete from balance;";
			st.executeUpdate(sql1);
			String sql2 = "insert into balance values (" + "\'" + newBalance + "\'" + ");";
			st.executeUpdate(sql2);

			con.commit();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static double getBalance() {
		// This method return the balance.
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();
			String sql = "select * from balance";
			ResultSet rs = st.executeQuery(sql);
			double balance = rs.getDouble(1);

			con.commit();
			st.close();
			con.close();
			return balance;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void print_operations_inMonth(String act, String date) {
		// This method prints all the actions performed in a particular month.
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();

			String sql = "select * from accountOperations where act = \'" + act + "\' and aDate > \'" + date
					+ ".01\' and aDate < \'" + date + ".31\';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String s = rs.getString(1) + "|" + rs.getDouble(2) + "|" + rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5);
				System.out.println(s);
			}
			System.out.println();
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void print_type(String act, String type) {
		// This method prints all operations of a certain type.
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();

			String sql = "select * from accountOperations where act = \'" + act + "\' and aType = \'" + type + "\';";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String s = rs.getString(1) + "|" + rs.getDouble(2) + "|" + rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5);
				System.out.println(s);
			}
			System.out.println();
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void print_bigger_smaller(String act, String op, int num) {
		// This method prints all the large / small values from the amount the user
		// selects.
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();

			String sql = "select * from accountOperations where act = \'" + act + "\' and amount ";
			if ((act == "out" && op == "big") || act == "in" && op == "small") {
				sql += "< ";
			}
			if ((act == "out" && op == "small") || act == "in" && op == "big") {
				sql += "> ";
			}
			if (act == "out") {
				sql += num * (-1) + ";";
			} else {
				sql += num + ";";
			}
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String s = rs.getString(1) + "|" + rs.getDouble(2) + "|" + rs.getString(3) + "|" + rs.getString(4) + "|"
						+ rs.getString(5);
				System.out.println(s);
			}
			System.out.println();
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void reset() {
		try {
			Connection con = connect();
			con.setAutoCommit(false);
			Statement st = con.createStatement();

			String delAcc = "delete from accountOperations;";
			st.executeUpdate(delAcc);
			String delBal = "delete from balance;";
			st.executeUpdate(delBal);
			String resetBal = "insert into balance values (" + "\'" + 0 + "\'" + ");";
			st.executeUpdate(resetBal);

			con.commit();
			st.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
