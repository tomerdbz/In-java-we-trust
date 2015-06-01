package boot;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import presenter.Properties;

/** This class main has to run prior to running main Run. it will set the Default Properties.
 * @author Tomer
 *
 */
public class WriteProperties {

	public static void main(String[] args) {
		XMLEncoder e;
		Properties prop=new Properties();//fine with the defaults I have defined
		try {
			e = new XMLEncoder(new FileOutputStream("properties.xml"));
			e.writeObject(prop);
			e.flush();
			e.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		

		
	}

}
