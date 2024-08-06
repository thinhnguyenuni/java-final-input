/**
 * 
 */
package fa.training.utils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import fa.training.entities.CarParkingContract;
import fa.training.entities.CarSaleContract;
import fa.training.entities.Contract;

/**
 * @author Administrator
 *
 */
public class ContractComparator implements Comparator<Contract> {
	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public int compare(Contract o1, Contract o2) {
		Date startDateof1 = null;
		Date startDateof2 = null;

		if (o1 instanceof CarSaleContract) {
			startDateof1 = ((CarSaleContract) o1).getStartDate();
		}
		if (o1 instanceof CarParkingContract) {
			startDateof1 = ((CarParkingContract) o1).getStartDate();
		}
		if (o2 instanceof CarSaleContract) {
			startDateof2 = ((CarSaleContract) o2).getStartDate();
		}
		if (o2 instanceof CarParkingContract) {
			startDateof2 = ((CarParkingContract) o2).getStartDate();
		}
		if (startDateof1 == null && startDateof2 == null) {
			return o1.getFullName().compareTo(o2.getFullName());
		}
		if (startDateof1 == null) {
			return 1;
		}
		if (startDateof2 == null) {
			return -1;
		}
		if (startDateof1.compareTo(startDateof2) != 0)
			return startDateof2.compareTo(startDateof1);
		return o1.getFullName().compareTo(o2.getFullName());

	}
}
