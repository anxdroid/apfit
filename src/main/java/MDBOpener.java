import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

public class MDBOpener {
	final String PASSWD_DB = "ilink@2012";
	
	public MDBOpener() {
		Table table = null;
		try {
			table = DatabaseBuilder.open(new File("my.mdb")).getTable("MyTable");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Row row : table) {
		  System.out.println("Column 'a' has value: " + row.get("a"));
		}
	}


}
