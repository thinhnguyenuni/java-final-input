/**
 * 
 */
package fa.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fa.training.exception.CarNumberUnForMatException;
import fa.training.exception.CommonException;
import fa.training.exception.ContractDuplicateException;
import fa.training.exception.EndDateOverException;
import fa.training.exception.PassportException;
import fa.training.services.Function;

/**
 * @author Administrator
 *
 */
public class Validator {

	/**
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static String isValidID(boolean isInsertFlg, Scanner sc) {
		String contractID = "";
		do {
			System.out.println("Nhập ContractID (ví dụ CD0009) ");
			contractID = sc.nextLine().strip().toUpperCase();
			if (isInsertFlg && kiemTraTonTai(contractID) == true) {
				System.out.println("contractID đã tồn tại, vui lòng nhập lại");
			}
		} while ( isInsertFlg && kiemTraTonTai(contractID) == true);// flag==false
		return contractID;
	}
	
	/**
	 * kiểm tra tồn tại candidateId
	 * 
	 * @author: ThinhNV30
	 * @birthday: 03/03/1993
	 * @param:
	 * @return:
	 */
	public static boolean kiemTraTonTai(String candidateID) {

		try (Connection con = ConnectionUtil.getConnection();
				PreparedStatement prst = con
						.prepareStatement("select [contractID] from [dbo].[Contract] where [contractID]=?")) {

			prst.setString(1, candidateID);

			ResultSet rs = prst.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			System.out.println("Lỗi truy vấn");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @author: NhiDNP
	 * @param line
	 * @param birthDateString
	 * @return
	 * @throws EndDateOverException
	 * @throws ParseException
	 */
	public static Date isValidDate(String dateString) throws ParseException {
		try {
			Function.SDF.setLenient(false);
			Date date = Function.SDF.parse(dateString);
			return date;
		} catch (ParseException e) {
			System.out.println("Nhap ngay khong dung dinh dang");
			throw e;
		}

	}
	
	
	/**
	 * Check if endDate after BeginDate over 100 days
	 * 
	 * @author: NhiDNP
	 * @param line
	 * @param Date beginDate, String endDate
	 * @return Date endDate if false
	 * @throws EndDateOverException
	 * @throws ParseException
	 */
	public static Date isValidEndDate(Date beginDate, String endDateString)
			throws EndDateOverException, ParseException {
		try {
			Function.SDF.setLenient(false);
			Date endDate = Function.SDF.parse(endDateString);
			Date maxEndDate = addDate(beginDate, 100);
			if (endDate.compareTo(beginDate) > 0 && endDate.before(maxEndDate)) {
				return endDate;
			}
			throw new EndDateOverException();
		} catch (ParseException e) {
			System.out.println("Nhap ngay khong dung dinh dang");
			throw e;
		}

	}

	/**
	 * Check if contractID is exist on database
	 * 
	 * @author: NhiDNP
	 * @param contractID
	 * @return String contractID if false
	 * @throws ContractDuplicateException
	 * @throws SQLException
	 */
	public static int isValidcontractID(int contractID) throws ContractDuplicateException, SQLException {

		String sql = "SELECT * from Contract where contractID=?";
		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement prstmt = conn.prepareStatement(sql)) {
			prstmt.setInt(1, contractID);
			ResultSet rs = prstmt.executeQuery();
			if (!rs.next()) {
				rs.close();
				return contractID;
			}
			throw new ContractDuplicateException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Check if carNumber is "43XX-YYYYY"
	 * 
	 * @author: NhiDNP
	 * @param carNumber
	 * @return
	 * @throws CarNumberUnForMatException
	 */
	public static String isValidCarNumber(String carNumber) throws CarNumberUnForMatException {
		if (carNumber.matches("^(43)+[A-Z]{2}+(-)+[0-9]{5}$"))
			return carNumber;
		throw new CarNumberUnForMatException();
	}

	

	/**
	 * Check input double
	 * 
	 * @author: NhiDNP
	 * @param String input
	 * @return double number
	 */
	public static double isValidDouble(String input) {
		double number;
		try {
			number = Double.parseDouble(input);
			return number;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Check input int
	 * 
	 * @author: NhiDNP
	 * @param String input
	 * @return int number
	 */
	public static int isValidDInt(String input) {
		int number;
		try {
			number = Integer.parseInt(input);
			return number;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Kiểm tra có nhập đúng type (0,1,2) không
	 * 
	 * @param inputString
	 * @return true nếu đúng, false nếu sai
	 */
	public static boolean isValidType(String input) {
		int type;
		try {
			type = Integer.parseInt(input);
		} catch (Exception e) {
			System.out.println("Nhap sai dinh dang");
			return false;
		}
		if (type < 1 || type > 3) {
			System.out.println("Lua chon khong hop le");
			return false;
		}

		return true;
	}

	/**
	 * Check if a passport contain only number and character
	 * 
	 * @author: NhiDNP
	 * @birthday: 1989/11/24
	 * @param line
	 * @param passport
	 * @return
	 * @throws PassportException
	 */
	public static String isValidPassPort(int line, String passport) throws PassportException {
		if (passport.matches("^^[a-zA-Z0-9]*$")) {
			return passport;
		}
		throw new PassportException(line);

	}

	/**
	 * Kiểm tra số điện thoại có nhập đúng yêu cầu không
	 * 
	 * @param courseCode
	 * @return true nếu đúng, false nếu sai
	 */
	public static boolean isValidPhoneNumber(String input) {
		if (Pattern.matches("^0\\d{9}$", input)) {
			return true;
		}
		System.out.println("Giá trị không hợp lệ.");
		return false;
	}

	/**
	 * Check if fullname length must be between 10 and 50 characters.
	 * 
	 * @author: NhiDNP
	 * @birthday: 1989/11/24
	 * @param name
	 * @return
	 * @throws CommonException
	 */
	public static boolean isCheckFullName(String name) throws CommonException {
		String NAME_REGEX = "^[a-zA-Z]{10,50}$";
		Pattern pattern = Pattern.compile(NAME_REGEX);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			throw new CommonException("Full name length must be between 10 and 50 characters.");
		}
		return matcher.matches();
	}

	/**
	 * Check if a string contains only number and character
	 * 
	 * @author: NhiDNP
	 * @birthday: 1989/11/24
	 * @param str
	 * @return
	 */
	public static boolean isNumAndChar(String str) {
		return str.matches("^[a-zA-Z0-9]+$");
	}

	/**
	 * @author : Quyenlx1
	 * @birthday: 1990-12-25 check validate for email
	 * @param email
	 * @return
	 * @throws CommonException
	 */
	public static boolean isValidateEmail(String email) throws CommonException {
//		return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$");
		String EMAIL_REGEX = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-z]{2,}$";
		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			throw new CommonException("dinh dang email khong dung");
		}
		return matcher.matches();

	}

	/**
	 * @author : Quyenlx1
	 * @birthday: 1990-12-25 kiểm tra xem : 1900 < năm nhập vào < năm hiện tại
	 *            không?
	 * @param dateString
	 * @return
	 * @throws CommonException
	 */
	public static boolean isCheckDate(String dateString) throws CommonException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		boolean isCheck = false;
		try {
			Date inputDate = dateFormat.parse(dateString);

			Calendar cal = Calendar.getInstance();
			cal.setTime(inputDate);
			// kiểm tra xem : 1900 < năm nhập vào < 2023
			if (cal.get(Calendar.YEAR) <= 2023 && cal.get(Calendar.YEAR) >= 1900) {
				isCheck = true;
			} else {
				throw new CommonException("year khong dung yeu cau: 1900 < year <= hientai");
			}

		} catch (ParseException e) {
			isCheck = false;
			e.getStackTrace();
		}
		return isCheck;
	}

//	public static boolean isValidPhone(String phone) {
//        return phone.matches("^(03|05|07|08|09)[0-9]{8}$");
//    }

	/**
	 * @author : Quyenlx1
	 * @birthday: 1990-12-25 kiem ta string la 1 so nguyen
	 * @param numberString
	 * @return
	 */
	public static boolean isIntNumber(String numberString) {
		try {
			Integer.parseInt(numberString.trim());
		} catch (NumberFormatException nfe) {
			nfe.getStackTrace();
			System.out.println(nfe.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @author : Quyenlx1
	 * @birthday: 1990-12-25 kiem ta string la 1 so double
	 * @param numberString
	 * @return
	 */
	public static boolean isDoubleNumber(String numberString) {
		try {
			Double.parseDouble(numberString.trim());
		} catch (NumberFormatException nfe) {
			nfe.getStackTrace();
			System.out.println(nfe.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Kiem ta string la 1 so float
	 * 
	 * @param numberString
	 * @return
	 */
	public static boolean isFloatNumber(String numberString) {
		try {
			Float.parseFloat(numberString.trim());
		} catch (NumberFormatException nfe) {
			nfe.getStackTrace();
			System.out.println(nfe.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * kiểm tra String startwith "IM" and 3 digits: ^[Ii][Mm][0-9]{3}$ kiểm tra
	 * String startwith "IM" hoạc "ab" and 3 digits: "^(IM|ab)\\d{3}$"
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isValidStringStartWith(String str) {
		return str.matches("PS\\d{3}$");
	}

	/**
	 * Ham cong, tru ngay
	 * 
	 * @author: NhiDNP
	 * @param number
	 * @return
	 */
	public static Date addDate(Date date, int number) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, number);// nếu number -: trừ ngày, nếu number +: cộng ngày
		return c.getTime();
	}
}
