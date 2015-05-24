package gui;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanPropertiesSetter {
	PropertyDescriptor[] descs;
	Class c;
	public BeanPropertiesSetter(Class c) {
		descs=PropertyUtils.getPropertyDescriptors(c);
	}
	public Class createWindow()
	{
		PropertiesWindow win=new PropertiesWindow("Write Properties",500,500);
		win.setPropertyDescriptors(descs);
		win.setClass(c);
		//try {
			/*System.out.println(c.getName());
			Class<?> clazz = Class.forName(c.getName());
			Constructor<?> ctor = clazz.getConstructor();
			Class n=ctor.newInstance().getClass();
			if(c.newInstance()!=null)
			{
//				Class n=(Class) c.newInstance();*/
			//win.setOutputInstance(n);
			win.run();
			return c;
			
		//} /*catch (InstantiationException e) {
			 /*TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//return null;
	}
}
