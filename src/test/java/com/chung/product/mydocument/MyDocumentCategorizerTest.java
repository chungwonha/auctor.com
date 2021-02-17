package com.chung.product.mydocument;

import com.chung.product.mydocument.nlp.MyDocumentCategorizer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
public class MyDocumentCategorizerTest {
    private static final Logger logger = LoggerFactory.getLogger(MyDocumentCategorizerTest.class);

    @Autowired
    MyDocumentCategorizer myDocumentCategorizer;

//    @Test
//    public void testCategorize(){
//        this.myDocumentCategorizer.setModelFileName("doc_cat_model.bin");
//
//        myDocumentCategorizer.trainModel("en-movie-category.train");
//
//        String thriller_s ="personal aide to the U S ambassador in France James Reese Jonathan Rhys Meyers has an enviable life";
//        String computer_s ="software architecture that allows multiple applications to work as a single coherent, resilient platform";
//        String romantic_s = "Crystal is a beautiful yet fragile girl in her early twenties But something dark and brooding burns inside her and she fights to hold it back She falls for the charms of the equally beautiful";
//
//        try {
//            String cat = myDocumentCategorizer.categorize(thriller_s);
//            logger.info(thriller_s+" is categorized as : "+cat);
//            Assert.isTrue(cat.equals("Thriller"),"categorized as : "+cat);
//
//            cat = myDocumentCategorizer.categorize(computer_s);
//            logger.info(computer_s+" is categorized as : "+cat);
//            Assert.isTrue(cat.equals("Computer"),"categorized as : "+cat);
//
//            cat = myDocumentCategorizer.categorize(romantic_s);
//            logger.info(romantic_s+" is categorized as : "+cat);
//            Assert.isTrue(cat.equals("Romantic"),"categorized as : "+cat);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testCategorize2(){
        this.myDocumentCategorizer.setModelFileName("doc_cat_model2.bin");

        myDocumentCategorizer.trainModel("training.txt");

        String school_s ="Middle School students at Stone Ridge are no strangers to Laudato Si, and in fact, have something to celebrate at this Fifth Anniversary of Pope Francis’ encyclical urging us all to “Care for Our Common Home.” The Grade 6 religion curriculum is devoted to the study of God’s saving love as it is revealed in Sacred Scripture, particularly the Old Testament. Early in the academic year, students delve into the Book of Genesis and are introduced to the Creation stories. After a bit of exegesis, they arrive at the conclusion that we humans have a moral responsibility to be stewards of God’s creation. As curious and motivated students, this leads them to ask, “What can we do?” Laudato Si’ offers many suggestions for young people to get involved in protecting the Earth. An exploration of the encyclical reveals five simple directives for getting started. They are, “live in harmony, listen to one another, care for nature, get involved in society and politics, and listen to the cry of the Earth and the cry of the poor.";
        String credit_s ="Made for people who have experienced past credit problems or who have lower credit scores, secured credit cards give the user a second chance to restore their credit and prove their creditworthiness again. The downfall is that applying for this card requires the user to place a substantial amount of cash upfront as security against the credit purchases.";
        String receipt_s ="Timberline STEAKS & GRILLIE Timberline Grill @ DIA A Mission Yogurt Commpany (303) 342-6670 Server: Kristen 06/10/201E Table 101/1 8:31 AN Guests: 8 40007 OM Meat Lovers 12.00 Pancake Breakfast 9.00 French Toast (2 @9.00) 18.00 Light & Healthy";// B.0C SD Pancakes (2 @5.00) 10.00 Mountainside Breakfast 9.00 Subtotal 66.00 Tax 5.28 Total 71.28 Priority 28.00 Priority 28.00 Priority 15.28 Balance Due 0.00 Thank You For Your Business Tell us about your experience: omments@missionyogurt.com Timberline STEAKS & GRILLIE Timberline Grill @ DIA A Mission Yogurt Commpany (303) 342-6670 Server: Kristen 06/10/201E Table 101/1 8:31 AN Guests: 8 40007 OM Meat Lovers 12.00 Pancake Breakfast 9.00 French Toast (2 @9.00) 18.00 Light & Healthy B.0C SD Pancakes (2 @5.00) 10.00 Mountainside Breakfast 9.00 Subtotal 66.00 Tax 5.28 Total 71.28 Priority 28.00 Priority 28.00 Priority 15.28 Balance Due 0.00 Thank You For Your Business Tell us about your experience: omments@missionyogurt.com ";
                //"Thank You! Activity Name Assigned Student Profile Price Quantity Line Total DB111-1 Marching Band Fees Ha ,Jinwoo 856758 $400.00 1 $400.00";
        String contract_s ="Independent Contractor Agreement THIS INDEPENDENT CONTRACTOR AGREEMENT (\\\"Agreement\\\") is made and entered on this day of 200_ by and between COMPANY NAME, (\\\"The Company\\\"), a [COUNTRY NAME] Corporation, whose address is and: Name: (\\\"The Contractor\\\") Address: This Agreement is made with reference to the following facts: COMPANY NAME will contract services with The Contractor, under the following conditions: 1. Relationship of Parties: The parties agree and intend that the relationship between them created by this Agreement is that of a principal-independent contractor.";
        String bill_s  = "AEC Physicans, PLLC PLEASE SEND ALL PAYMENTS TO THE PO BOX P,O Box 847 ADDRESS LISTED DIRECTLY BELOW Fulshear, TX 77441 To Pay your bill online visit: RETURN SERVICE REQUESTED httpsillwww.patientnotebook.comaustinepandersonmills STATEMENT DATE PAY THIS AMOUNT ACCOUNT NO. Billing phone: 832-807-2956 11/01/2018 Billing Fax: 281-238-5578 Office Hours: 7:00AM-4:00PM To Pay your bill online visit: CHARGES AND CREDITS MADE AFTER STATEMENT SHOW AMOUNT tosllvwwpatientnotebook.comaustinepandersonmills DATE WILL APPEAR ON NEXT STATEMENT PAID HERE $ Stmt ID#: 1012676924 MAKE CHECKS PAYABLE/ REMIT TO: iphpmplappllhplllitphpliillwhlil 126253 12 AEC Physicians, PLLC PO BOX 371863 PITTSBURGH PA 15250-7863 000012 lltluudhHlluuullululluullullullulul 0001012676924000011313500000083410004 Please check box if above address is incorrect or insurance STATEMENT PLEASE DETACH AND RETURN TOP PORTION WITH information has changed. and indicate change(s) on reverse side. ";
                //"You are not nsured against loss costs attorneys fees and expenses resulting from b Proof Of Your Loss defend a legal action or take other appropriate action under Governmanta police power and the axatence or violation of those portions of any law or goverment regulaton conceming (1) We may require You to give Us a written statement this Policy By doing SO We do not give up any rights building signed by You describing Your loss which includes 6. LIMITATION OF OUR LIABILITY b 2oning (a) the basis of Your claim; a After subtracting Your Deductible Amount if applies We will land use (b) the Covered Risks which resulted in Your loss; pay no than the least of d mprovemants on the Land (c) the dollar amount of Your loss; and (1) Your actual loss division (d) the method You used to compute the amount of (2) Our Maximum Dollar Limit of Liability then in force for the enronmental protection Your loss particular Covered Risk for claims covered only under This Exclusion does limit the coverage described in Covered Rank 14 or 27 2 The lolure of Your exisbng structures. Or any part of them to be constructed in accordance with applicable bulding codes.";
        try {
            String cat = myDocumentCategorizer.categorize(school_s);
            logger.info(school_s+" is categorized as : "+cat);
            Assert.isTrue(cat.equals("SCHOOL"),"categorized as : "+cat);

            cat = myDocumentCategorizer.categorize(credit_s);
            logger.info(credit_s+" is categorized as : "+cat);
            Assert.isTrue(cat.equals("CREDIT"),"categorized as : "+cat);

            cat = myDocumentCategorizer.categorize(receipt_s);
            logger.info(receipt_s+" is categorized as : "+cat);
            Assert.isTrue(cat.equals("RECEIPT"),"categorized as : "+cat);

            cat = myDocumentCategorizer.categorize(contract_s);
            logger.info(contract_s+" is categorized as : "+cat);
            Assert.isTrue(cat.equals("CONTRACT"),"categorized as : "+cat);

            cat = myDocumentCategorizer.categorize(bill_s);
            logger.info(bill_s+" is categorized as : "+cat);
            Assert.isTrue(cat.equals("BILL"),"categorized as : "+cat);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
