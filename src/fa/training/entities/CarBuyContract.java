/**
 * 
 */
package fa.training.entities;

import java.util.Scanner;

import fa.training.utils.Validator;

/**
 * @author Administrator
 *
 */
public class CarBuyContract extends Contract {
	private String currentPrices;
	private String priceOfSelling;
	private String usedTime;

	public String getCurrentPrices() {
		return currentPrices;
	}

	public void setCurrentPrices(String currentPrices) {
		this.currentPrices = currentPrices;
	}

	public String getPriceOfSelling() {
		return priceOfSelling;
	}

	public void setPriceOfSelling(String priceOfSelling) {
		this.priceOfSelling = priceOfSelling;
	}

	public String getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(String usedTime) {
		this.usedTime = usedTime;
	}

	public CarBuyContract() {
		super();
	}



	public CarBuyContract(String type, String contractID, String fullName, String phoneNumber, String carNumber,
			String currentPrices, String priceOfSelling, String usedTime) {
		super(type, contractID, fullName, phoneNumber, carNumber);
		this.currentPrices = currentPrices;
		this.priceOfSelling = priceOfSelling;
		this.usedTime = usedTime;
	}

	@Override
	public String toString() {
		return "CarBuyContract [" + super.toString() + ",currentPrices=" + currentPrices + ", priceOfSelling="
				+ priceOfSelling + ", usedTime=" + usedTime + "]";
	}

	@Override
	public void showMe() {
		System.out.println(this.toString());

	}

	@Override
	public void inputInfo(boolean isInsertFlg) {
		
		super.inputInfo(isInsertFlg);
		Scanner sc = new Scanner(System.in);
		
		//NHAP 
		System.out.println("Nhập currentPrices : ");
		this.setCurrentPrices(sc.nextLine().toUpperCase());
		
		//NHAP 
		System.out.println("Nhập priceOfSelling : ");
		this.setPriceOfSelling(sc.nextLine().toUpperCase());
		
		//NHAP 
		System.out.println("Nhập usedTime : ");
		this.setUsedTime(sc.nextLine().toUpperCase());
	}



}
