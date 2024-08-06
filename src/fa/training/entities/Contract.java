package fa.training.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import fa.training.exception.CarNumberUnForMatException;
import fa.training.exception.ContractDuplicateException;
import fa.training.utils.Validator;

public abstract class Contract implements Comparable<Contract> {

	private String type;
	private String contractID;
	private String fullName;
	private String phoneNumber;
	private String carNumber;
	static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	public Contract() {

	}

	public Contract(String type, String contractID, String fullName, String phoneNumber, String carNumber) {
		this.type = type;
		this.contractID = contractID;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.carNumber = carNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public static SimpleDateFormat getSdf() {
		return SDF;
	}

	public abstract void showMe();

	@Override
	public String toString() {
		return "type=" + type + ", contractID=" + contractID + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber
				+ ", carNumber=" + carNumber;
	}

	public void inputInfo(boolean isInsertFlg) {

		Scanner sc = new Scanner(System.in);

		// NHẬP TYPE
		do {
			System.out.println("Nhap type name");
			type = sc.nextLine().trim();
			if (type.equals("")) {
				System.out.println("Nhap khong dung dinh dang");
				continue;
			}
			break;
		} while (true);
		this.setType(type);

		// NHẬP ContractID
		String contractID = Validator.isValidID(isInsertFlg, sc);
		this.setContractID(contractID);// flag=true;

		// NHẬP fullName
		do {
			System.out.println("Nhap full name");
			fullName = sc.nextLine().trim();
			if (fullName.equals("")) {
				System.out.println("Nhap khong dung dinh dang");
				continue;
			}
			break;
		} while (true);
		this.setFullName(fullName);

		// NHẬP phoneNumber
		do {
			System.out.println("Nhap so dien thoai");
			phoneNumber = sc.nextLine().trim();
			if (phoneNumber.equals("")) {
				System.out.println("Nhap khong dung dinh dang");
				continue;
			}
			break;
		} while (true);
		this.setPhoneNumber(phoneNumber);

		// NHẬP carNumber
		do {
			System.out.println("Nhap bien so xe (43XX-YYYYY)");
			carNumber = sc.nextLine().trim().toUpperCase();
			try {
				carNumber = Validator.isValidCarNumber(carNumber);
			} catch (CarNumberUnForMatException e) {
				continue;
			}
			break;
		} while (true);
		this.setCarNumber(carNumber);
	}

	@Override
	public int compareTo(Contract o) {
		Date startDateofO;
		Date startDateofThis;
		try {
			startDateofO = SDF.parse(SDF.format(new Date()));
			startDateofThis = SDF.parse(SDF.format(new Date()));
		} catch (ParseException e) {
			return 0;
		}

		if (o instanceof CarSaleContract) {
			startDateofO = ((CarSaleContract) o).getStartDate();
		}
		if (o instanceof CarParkingContract) {
			startDateofO = ((CarParkingContract) o).getStartDate();
		}
		if (this instanceof CarSaleContract) {
			startDateofThis = ((CarSaleContract) this).getStartDate();
		}
		if (this instanceof CarParkingContract) {
			startDateofThis = ((CarParkingContract) this).getStartDate();
		}
		if (startDateofO == null || startDateofThis == null) {
			return 0;
		}
		if (startDateofThis.compareTo(startDateofO) != 0)
			return startDateofO.compareTo(startDateofThis);
		return this.getFullName().compareTo(o.getFullName());
	}

}