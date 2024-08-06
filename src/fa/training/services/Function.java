package fa.training.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import fa.training.entities.Contract;
import fa.training.entities.CarBuyContract;
import fa.training.entities.CarParkingContract;
import fa.training.entities.CarSaleContract;
import fa.training.exception.CarNumberUnForMatException;
import fa.training.utils.ConnectionUtil;
import fa.training.utils.ContractComparator;
import fa.training.utils.Validator;

public

class Function {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static Scanner sc = new Scanner(System.in);

	/**
	 * phương thức insert bằng result set
	 * 
	 * @param c
	 * @return
	 */
	public static int insertCDR_M(Contract c) {

		String sql = "select * from Contract";
		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement pstm = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE)) {

			ResultSet rs = pstm.executeQuery();

			rs.moveToInsertRow();

			rs.updateString("ContractID", c.getContractID());
			rs.updateString("FullName", c.getFullName());
			rs.updateString("phoneNumber", c.getPhoneNumber());
			rs.updateString("carNumber", c.getCarNumber());
			rs.updateString("type", c.getType());

			if (c instanceof CarParkingContract) {
				Date startDate = new Date();
				startDate = ((CarParkingContract) c).getStartDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate1 = dateFormat.format(startDate);

				Date endDate = new Date();
				endDate = ((CarParkingContract) c).getStartDate();
				String formattedDate2 = dateFormat.format(endDate);

				rs.updateString("startDate", formattedDate1);
				rs.updateString("endDate", formattedDate2);
				rs.updateString("feeOfMonth", ((CarParkingContract) c).getFeeOfMonth());
			}

			if (c instanceof CarSaleContract) {
				Date startDate = new Date();
				startDate = ((CarParkingContract) c).getStartDate();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate1 = dateFormat.format(startDate);

				Date endDate = new Date();
				endDate = ((CarParkingContract) c).getStartDate();
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate2 = dateFormat2.format(endDate);

				rs.updateString("startDate", formattedDate1);
				rs.updateString("endDate", formattedDate2);
				rs.updateString("price", ((CarSaleContract) c).getPrice());
				rs.updateString("priceOfSelling", ((CarSaleContract) c).getPriceOfSelling());
				rs.updateString("feeOfMonth", ((CarSaleContract) c).getFeeOfMonth());
			}

			if (c instanceof CarBuyContract) {
				rs.updateString("currentPrices", ((CarBuyContract) c).getCurrentPrices());
				rs.updateString("priceOfSelling", ((CarBuyContract) c).getPriceOfSelling());
				rs.updateString("University_name", ((CarBuyContract) c).getUsedTime());
			}
			rs.insertRow();
			return 1;

		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * insert data bằng result set
	 */
	public static void insertCDR() {

		int result;
		Contract c;
		System.out.println("nhap Contract_type tuong ung 1:CarParkingContract ,2:CarSaleContract ,3:CarBuyContract");
		int Contract_type = Integer.parseInt(sc.nextLine());
		if (Contract_type == 1) {
			c = new CarParkingContract();
			c.inputInfo(true);
		} else if (Contract_type == 2) {
			c = new CarSaleContract();
			c.inputInfo(true);
		} else {
			c = new CarBuyContract();
			c.inputInfo(true);
		}
		result = insertCDR_M(c);
		if (result > 0) {
			System.out.println("Insert ứng viên thành công!");
		} else
			System.out.println("Insert ứng viên thất bại!");
	}

	/**
	 * Map data info of Student and ResultSet
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 * @param rs
	 * @return Student
	 * @throws SQLException
	 */
	public static Contract mapRows(ResultSet rs) throws SQLException {

		Contract contract = null;
		String typeST = rs.getString("type");
		int type = Integer.parseInt(typeST);

		String contractID = rs.getString("contractID");
		String fullName = rs.getString("fullName");
		String phoneNumber = rs.getString("phoneNumber");
		String carNumber = rs.getString("carNumber");
		if (type == 1) {
			Date startDate = rs.getDate("startDate");
			Date endDate = rs.getDate("endDate");
			String feeOfMonth = rs.getString("feeOfMonth");
			contract = new CarParkingContract(typeST, contractID, fullName, phoneNumber, carNumber, startDate, endDate,
					feeOfMonth);
		}
		if (type == 2) {
			Date startDate = rs.getDate("startDate");
			Date endDate = rs.getDate("endDate");
			String feeOfMonth = rs.getString("feeOfMonth");
			String price = rs.getString("price");
			String priceOfSelling = rs.getString("priceOfSelling");
			contract = new CarSaleContract(typeST, contractID, fullName, phoneNumber, carNumber, startDate, endDate,
					price, priceOfSelling, feeOfMonth);
		}
		if (type == 3) {
			String currentPrices = rs.getString("currentPrices");
			String priceOfSelling = rs.getString("priceOfSelling");
			String usedTime = rs.getString("usedTime");
			contract = new CarBuyContract(typeST, contractID, fullName, phoneNumber, carNumber, currentPrices,
					priceOfSelling, usedTime);
		}
		return contract;
	}

	/**
	 * Show infomation of all Contract from a ArrayList
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 * @param ArrayList Student
	 */
	public static void showInformation(ArrayList<Contract> contractList) {
		contractList.forEach(t -> t.showMe());
	}

	/**
	 * Show info of all Students from database, without sorted
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 */
	public static void showContractsFromDB() {

		String sql = "SELECT * FROM CONTRACT";
		try (Connection conn = ConnectionUtil.getConnection();
				PreparedStatement prstmt = conn.prepareStatement(sql);
				ResultSet rs = prstmt.executeQuery()) {
			if (!rs.next()) {
				System.out.println("No data to display!!!");
				return;
			}
			List<Contract> contractList = new ArrayList<Contract>();
			do {
				Contract contract = mapRows(rs);
				contractList.add(contract);

			} while (rs.next());

			showInformation((ArrayList<Contract>) contractList);
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Update carNumber of Contract table, using ResultSet
	 * 
	 * @author: thinhnv30
	 */
	public static void updateContract() {
		
		Contract contract = null;
		System.out.println("Nhập contractID cần update: ");
		String contractID = sc.nextLine();

		String sql = "SELECT * FROM Contract WHERE ContractID=?";
		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement prstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			prstmt.setString(1, contractID);
			ResultSet rs = prstmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No found Contract");
				return;
			}
			System.out.println("Data before update");
			contract = mapRows(rs);
			contract.showMe();
			String carNumber;
			do {
				System.out.println("Nhap bien so xe muon update (43XX-YYYYY)");
				carNumber = sc.nextLine().trim().toUpperCase();
				try {
					carNumber = Validator.isValidCarNumber(carNumber);
				} catch (CarNumberUnForMatException e) {
					continue;
				}
				break;
			} while (true);
			rs.absolute(1);
			rs.updateString("carNumber", carNumber);
			rs.updateRow();

			System.out.println("\nData after update:");
			rs.first();
			mapRows(rs).showMe();
			rs.close();
			prstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Show info of all Students from database, without sorted
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 */
	public static void showContractsFromDBWithSorted() {

		String sql = "SELECT * FROM CONTRACT";
		try (Connection conn = ConnectionUtil.getConnection();
				PreparedStatement prstmt = conn.prepareStatement(sql);
				ResultSet rs = prstmt.executeQuery()) {
			if (!rs.next()) {
				System.out.println("No data to display!!!");
				return;
			}
			List<Contract> contractList = new ArrayList<Contract>();
			do {
				Contract contract = mapRows(rs);
				contractList.add(contract);

			} while (rs.next());
			Collections.sort(contractList, new ContractComparator());
			contractList.forEach(t -> t.showMe());
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * Sort list of contracts
	 * 
	 * @author: thinhnv30
	 * @param contractList
	 */
	public static void sortListContract(List<Contract> contractList) {
		List<Contract> filterContract = contractList.stream().filter(t -> !(t instanceof CarBuyContract)).map(t -> t)
				.collect(Collectors.toList());
		List<CarBuyContract> carBuyContract = contractList.stream().filter(t -> t instanceof CarBuyContract)
				.map(t -> ((CarBuyContract) t)).collect(Collectors.toList());
		Collections.sort(filterContract);
		Collections.sort(carBuyContract, Comparator.comparing(CarBuyContract::getFullName));
		filterContract.forEach(t -> t.showMe());
		carBuyContract.forEach(t -> t.showMe());
	}

	/**
	 * Update Student's info in database
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 */
	public static void updateFlightDate() {
		String sql = "UPDATE ... SET ... WHERE";

		try (Connection conn = ConnectionUtil.getConnection(); 
				PreparedStatement prstmt = conn.prepareStatement(sql)) {

			int row = prstmt.executeUpdate();
			System.out.println("Update " + row + " rows successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Update failed!");
		}

	}

	/**
	 * Delete Students from database
	 * 
	 * @author: thinhnv30
	 * @birthday: 1989/11/24
	 */
	public static void deleteStd() {
		String sql = "DELETE FROM ... WHERE";

		try (Connection conn = ConnectionUtil.getConnection(); 
				PreparedStatement prstmt = conn.prepareStatement(sql)) {

			int row = prstmt.executeUpdate();
			System.out.println("Delete " + row + " rows successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Delete failed!");
		}

	}

	public static void main(String[] args) {
		updateContract();
	}

}
