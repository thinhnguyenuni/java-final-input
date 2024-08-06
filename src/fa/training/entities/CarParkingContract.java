/**
 * 
 */
package fa.training.entities;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import fa.training.exception.EndDateOverException;
import fa.training.utils.Validator;

/**
 * @author Administrator
 *
 */
public class CarParkingContract extends Contract {
	
	private Date startDate;
	private Date endDate;
	private String feeOfMonth;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getFeeOfMonth() {
		return feeOfMonth;
	}

	public void setFeeOfMonth(String feeOfMonth) {
		this.feeOfMonth = feeOfMonth;
	}

	public CarParkingContract() {
		super();
	}

	public CarParkingContract(String type, String contractID, String fullName, String phoneNumber, String carNumber,
			Date startDate, Date endDate, String feeOfMonth) {
		super(type, contractID, fullName, phoneNumber, carNumber);
		this.startDate = startDate;
		this.endDate = endDate;
		this.feeOfMonth = feeOfMonth;
	}

	@Override
	public String toString() {
		return "CarParkingContract [" + super.toString() + ",startDate=" + startDate + ", endDate=" + endDate
				+ ", feeOfMonth=" + feeOfMonth + "]";
	}

	@Override
	public void showMe() {
		System.out.println(this.toString());

	}

	@Override
	public void inputInfo(boolean isInsertFlg) {

		super.inputInfo(isInsertFlg);
		Scanner sc = new Scanner(System.in);

		// NHAP
		String input;
		do {
			System.out.println("Nhap ngay bat dau (Dinh dang dd/MM/yyyy)");
			input = sc.nextLine().trim();
			try {
				startDate = Validator.isValidDate(input);
			} catch (ParseException e) {
				continue;
			}
			break;
		} while (true);
		this.setStartDate(startDate);

		// NHAP
		do {
			System.out.println("Nhap ngay ket thuc (Dinh dang dd/MM/yyyy)");
			input = sc.nextLine().trim();
			try {
				endDate = Validator.isValidEndDate(startDate, input);
				break;
			} catch (ParseException e) {
			} catch (EndDateOverException e) {
			}

		} while (true);
		this.setEndDate(endDate);
		
		//NHAP
		System.out.println("Nhập feeOfMonth : ");
		this.setFeeOfMonth(sc.nextLine().toUpperCase());
	}

}
