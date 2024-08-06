package fa.training.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionUtil {

	private static final String URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;";

	private static final String DATABASE = "databaseName=READY;";

	private static final String USER_NAME = "sa";

	private static final String PASSWORD = "123";

	public static Connection getConnection() {

		Connection con = null;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			con = DriverManager.getConnection(

					URL + DATABASE,

					USER_NAME, PASSWORD);

			// localhost:1433 - <IP may co DB, truong hop cai tai may ca nhan thi la

			// localhost>:<port - mac dinh luc cai SQL server ko thay doi gi thi la 1433>

		} catch (ClassNotFoundException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (SQLException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

			System.out.println("Connect that bai");

		}

		return con;

	}

	public static void main(String[] args) {
		Connection con = ConnectionUtil.getConnection();
		if (con != null)
			System.out.println("Connect thanh cong");
		else
			System.out.println("Connect that bai");
	}

	public static List<Object[]> extractData(ResultSet resultSet) throws SQLException {
		List<Object[]> dataList = new ArrayList<>();

		int columnCount = resultSet.getMetaData().getColumnCount();

		while (resultSet.next()) {
			Object[] row = new Object[columnCount];
			for (int i = 0; i < columnCount; i++) {
				row[i] = getValueFromResultSet(resultSet, i + 1);
			}
			dataList.add(row);
		}

		return dataList;
	}

	private static Object getValueFromResultSet(ResultSet resultSet, int columnIndex) throws SQLException {
		// Kiểm tra kiểu dữ liệu của cột hiện tại
		int columnType = resultSet.getMetaData().getColumnType(columnIndex);

		switch (columnType) {
		case java.sql.Types.INTEGER:
			return resultSet.getInt(columnIndex);
		case java.sql.Types.FLOAT:
			return resultSet.getFloat(columnIndex);
		case java.sql.Types.DOUBLE:
			return resultSet.getDouble(columnIndex);
		case java.sql.Types.BOOLEAN:
			return resultSet.getBoolean(columnIndex);
		case java.sql.Types.DATE:
			return resultSet.getDate(columnIndex);
		case java.sql.Types.TIMESTAMP:
			return resultSet.getTimestamp(columnIndex);
		case java.sql.Types.NVARCHAR:
			return resultSet.getNString(columnIndex);
		case java.sql.Types.VARCHAR:
		default:
			return resultSet.getString(columnIndex);
		}
	}
}
