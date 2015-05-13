package boot;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;

import presenter.Properties;

/**
 * @author Tomer
 *
 */
public class WriteProperties {

	public static void main(String[] args) {
		XMLEncoder e;
		Properties prop=new Properties();//fine with the defaults I have defined
		try {
			FileOutputStream out=new FileOutputStream("properties.xml");
			e = new XMLEncoder(out);
			e.writeObject(prop);
			e.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
