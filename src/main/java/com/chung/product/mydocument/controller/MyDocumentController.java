package com.chung.product.mydocument.controller;

import com.chung.product.mydocument.MyDocumentConstants;
import com.chung.product.mydocument.MySqsMonitoringForPdf;
import com.chung.product.mydocument.TrainModelCreator;
import com.chung.product.mydocument.service.MyDocumentService;
//import com.chung.product.mydocument.vo.Document;
import com.chung.product.mydocument.vo.Document2;
import com.chung.product.mydocument.vo.MyDocumentValueObject;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mydocument")
public class MyDocumentController {
    private static final Logger logger = LoggerFactory.getLogger(MyDocumentController.class);

    @Autowired
    StorageService storageService;

    @Autowired
    MyDocumentService myDocumentService;

    @Autowired
    MySqsMonitoringForPdf mySqsMonitoringForPdf;

    @Autowired
    TrainModelCreator trainModelCreator;

    @GetMapping
    public String goHome(Model model){
        MyDocumentValueObject myDocumentValueObject = new MyDocumentValueObject();
        myDocumentValueObject.setFileName("TEST FILE NAME");
        model.addAttribute("mydocumentvo",myDocumentValueObject);


        return "index";
    }

    @GetMapping("/openUpload")
    public String openUpload(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("category") String category,
                         RedirectAttributes redirectAttributes) {
        logger.info("category: "+category);

        this.storageService.store(file,MyDocumentConstants.tempOwnerId);
        Document2 document2 = this.persist2(file,category);

        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/mydocument/goservice?category="+document2.getCategoryCode();
    }

    @GetMapping("/runsqsmonitor")
    public String runSqsMonitor(){
        mySqsMonitoringForPdf.setMonitoringSwtich(true);
        mySqsMonitoringForPdf.start();
        return "sqsmonitor";

    }

    @GetMapping("/runtrainmodelcreator")
    public String runTrainModelCreator(){

        if(!trainModelCreator.isTrainModelCreatorSwitch()) {
            trainModelCreator.setTrainModelCreatorSwitch(true);
            trainModelCreator.start();
        }else{
            logger.info("trainModelCreator is already on");
            trainModelCreator.setTrainModelCreatorSwitch(false);
        }
        return "trainmodelcreator";

    }

    @GetMapping("/goservice")
    public String goService(@RequestParam("category") String category, Model model){
        logger.info("category: "+category);
        model.addAttribute("category",category);
        return "uploadSuccessful2";
    }

    @GetMapping("/view")
    public String goView(Model model){
        List<Document2> document2List = this.myDocumentService.getAllDocuments(MyDocumentConstants.tempOwnerId);
        document2List.stream().forEach(document2 -> {
            logger.debug(document2.getDocumentId()+" "+document2.getDocumentNameInS3());
        });
        model.addAttribute("documentList",document2List);
        return "view";
    }

    @GetMapping("/opendoc")
    public String openDoc(@RequestParam("documentId") String documentId, Model model){
        Document2 doc = this.myDocumentService.getDocument(documentId);
        model.addAttribute("document",doc);
        return "openDoc";
    }

//    private void persist(MultipartFile file, String category){
//
//        String document = file.getOriginalFilename();
//
//        /**
//         * TODO: create a configuration property class
//         */
//        String bucket = "docbankrepository";
//
//        Document doc = new Document();
//
//        /**
//         * TODO: need to get userId from a session or stateless way
//         */
//        doc.setOwnerId("99");
//        doc.setDocumentKey("99/"+document);
//        doc.setCategoryCode(category);
//        doc.setDocumentNameInS3(document);
//        doc.setBucket(bucket);
//
//        this.myDocumentService.persistDocument(doc);
//    }

    private Document2 persist2(MultipartFile file, String category){
        String document = file.getOriginalFilename();

        /**
         * TODO: create a configuration property class
         */
        String bucket = "docbankrepository";

        Document2 doc = new Document2();

        /**
         * TODO: need to get userId from a session or stateless way
         */
        doc.setOwnerId(MyDocumentConstants.tempOwnerId);
        doc.setDocumentkey(MyDocumentConstants.tempOwnerId+"/"+document);
        doc.setCategoryCode(category);
        doc.setDocumentNameInS3(document);
        doc.setBucket(bucket);

        Document2 document2 = this.myDocumentService.persistDocument(doc);
        return document2;
    }
}
