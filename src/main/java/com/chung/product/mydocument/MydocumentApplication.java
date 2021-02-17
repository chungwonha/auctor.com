package com.chung.product.mydocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MydocumentApplication {

	public static void main(String[] args) {
		SpringApplication.run(MydocumentApplication.class, args);


//		MySqsMonitoringForPdf mySqsMonitoringForPdf = getMySqsMonitoringForPdf();
////				new MySqsMonitoringForPdf();
//		mySqsMonitoringForPdf.setMonitoringSwtich(true);
//		mySqsMonitoringForPdf.start();
//
	}
//
//	@Bean
//	public static MySqsMonitoringForPdf getMySqsMonitoringForPdf(){
//		return new MySqsMonitoringForPdf();
//	}

}
