package it.paoletti.mdbhandling;

import java.math.BigDecimal;
import java.util.Date;

import com.healthmarketscience.jackcess.Row;

public class Measurement {
	Integer ScaleMeasurementID = null;
	Integer PatientId = null;
	Date MeasurementTime = null;
	BigDecimal WeightKg = null;
	BigDecimal BodyFatPct = null;
	BigDecimal WaterPct = null;
	BigDecimal MusclePct = null;
	BigDecimal BMI = null;
	Integer ActivityGrade = null;
	
	public Measurement(Row row) {
		/*
		String[] fields = {"ScaleMeasurementID", "PatientId", "MeasurementTime", "WeightKg", "BodyFatPct", "WaterPct", "MusclePct", "BMI", "ActivityGrade"};
		for (String field : fields) {
			System.out.print(field+" = "+row.get(field).toString()+" ");
		}
		System.out.println();
		*/
		ScaleMeasurementID = row.getInt("ScaleMeasurementID");
		PatientId = row.getInt("PatientId");
		MeasurementTime = row.getDate("MeasurementTime");
		WeightKg = row.getBigDecimal("WeightKg");
		BodyFatPct = row.getBigDecimal("BodyFatPct");
		WaterPct = row.getBigDecimal("WaterPct");
		MusclePct = row.getBigDecimal("MusclePct");
		BMI = row.getBigDecimal("BMI");
		ActivityGrade = row.getInt("ActivityGrade");	
	}
	
	public String toString() {
		return ScaleMeasurementID+" "+PatientId+" "+MeasurementTime+" "+WeightKg+"Kg "+BodyFatPct+"% fat "+WaterPct+"% water "+MusclePct+"% muscle "+BMI+" ["+ActivityGrade+"]";
	}
}
