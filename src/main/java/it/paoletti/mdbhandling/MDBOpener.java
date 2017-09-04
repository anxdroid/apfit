package it.paoletti.mdbhandling;
import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class MDBOpener {
	final String PASSWD_DB = "ilink@2012";
	
	//public MDBOpener() {
	public static void main(String[] args) {
		Table table = null;
		try {
			table = DatabaseBuilder.open(new File("Beurer.mdb")).getTable("ScaleMeasurement");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Row row : table) {
		  //System.out.println("Column 'MeasurementDate' has value: " + row.get("MeasurementDate"));
			Measurement meas = new Measurement(row);
			System.out.println(meas);
		}
	}


}
