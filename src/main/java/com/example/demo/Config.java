package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	 
	
	   private static Properties properties;
	
	   static {
		   properties= new Properties();
		   //readProperties("/msconf/config.properties");
		   readProperties("/users/victorvenegas/config/kafkaservice/config.properties");
	   }

		
		public static void readProperties(String file){
			
		    try {
		      properties.load(new FileInputStream(new File(file)));
		      
		      /*System.out.println(properties.get("DRIVER"));
		      System.out.println(properties.get("URL"));
		      System.out.println(properties.get("USUARIO"));
		      System.out.println(properties.get("CLAVE"));*/
		    } catch (FileNotFoundException e) {
		      // TODO Auto-generated catch block
		      System.err.println(e.getMessage());
		    } catch (IOException e) {
		      // TODO Auto-generated catch block
		      System.err.println(e.getMessage());
		    }
			

		}
		
		public static String getProperty(String property) {
			return (String)properties.get(property);
		}
	

}
