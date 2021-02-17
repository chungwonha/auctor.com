package com.chung.product.mydocument;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MySqsMonitoringForPdfTest {

    @Autowired
    MySqsMonitoringForPdf mySqsMonitoringForPdf;

    @Test
    public void testReceiveMessage(){

//        MySqsMonitoringForPdf mySqsMonitoringForPdf = new MySqsMonitoringForPdf();
        mySqsMonitoringForPdf.receiveMessage();

    }
}
