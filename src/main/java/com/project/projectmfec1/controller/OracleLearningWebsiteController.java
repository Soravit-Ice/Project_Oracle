package com.project.projectmfec1.controller;
import com.project.projectmfec1.entity.TimeSheetEntity;
import com.project.projectmfec1.model.TimeSheetModel;
import com.project.projectmfec1.repository.UserRepository;
import com.project.projectmfec1.service.UserService;
import com.project.projectmfec1.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OracleLearningWebsiteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UtilsService utilsService;

    @GetMapping("/")
    public String index(Model model){
       String auth =  utilsService.auth();
       model.addAttribute("mode",auth);
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model){
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
        return "aboutus";
    }

    @GetMapping("/contract")
    public String contract(Model model){
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
        return "contract";
    }


    @GetMapping("/lesson")
    public String lesson(Model model){
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
        return "lesson";
    }


    @GetMapping("/lessonDetail/{topic}")
    public String lessonDetail(Model model , @PathVariable String topic){
        List<String> pdfFiles = new ArrayList<>();
        String auth =  utilsService.auth();
        model.addAttribute("mode",auth);
//        List<String> pdfFiles = Arrays.asList("file1.pdf", "file2.pdf", "file3.pdf", "file4.pdf", "file5.pdf", "file6.pdf");
        switch (topic) {
            case "document" -> {
                topic = "คู่มือการฝึกงาน";
                pdfFiles = List.of("document.pdf");
            }
            case "tools" -> {
                topic = "วิธีติดตั้งโปรแกรม";
                pdfFiles = List.of("tools.pdf");
            }
            case "Database Oracle" -> pdfFiles = List.of("DatabaseOracle.pdf");
            case "SQL ORACLE" ->
                    pdfFiles = Arrays.asList("SQL ORACLE_01.pdf", "SQL ORACLE_02.pdf", "SQL ORACLE_03.pdf", "SQL ORACLE_04.pdf",
                            "SQL ORACLE_05.pdf", "SQL ORACLE_06.pdf", "SQL ORACLE_07.pdf", "SQL ORACLE_08.pdf", "SQL ORACLE_09.pdf");
            case "Database Administrator" ->
                    pdfFiles = Arrays.asList("DBA ORACLE_01.pdf", "DBA ORACLE_02.pdf", "DBA ORACLE_03.pdf", "DBA ORACLE_04.pdf");
            case "Coding languages" -> pdfFiles = Arrays.asList("Linux_shell_scripting_tutorial.pdf", "Linux_shell_scripting_tutorial_02.pdf");
        }
        model.addAttribute("nameTopic", topic);
        model.addAttribute("pdfFiles", pdfFiles);
        return "lesson_detail";
    }


    @GetMapping("/pdf/{fileName:.+}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String fileName) throws IOException {
        Resource resource = new ClassPathResource("/pdf/" + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.getFile().length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(resource.getInputStream()));
    }


    @GetMapping("/timesheet")
    public String showTimesheet(Model model) {
        String auth =  utilsService.auth();
       List<TimeSheetEntity> result = utilsService.findAllTimeSheet();
        model.addAttribute("mode",auth);
        model.addAttribute("TimeSheetEntity" , result);
        return "timesheet";
    }

    @GetMapping("/timesheetSave")
    public String showTimesheetSave(Model model) {
        String auth =  utilsService.auth();
        TimeSheetModel timeSheetModel = new TimeSheetModel();
        model.addAttribute("mode",auth);
        model.addAttribute("TimeSheetModel",timeSheetModel);
        return "timesheet_save";
    }


    @PostMapping("/timesheetSave")
    public String TimesheetSave(@ModelAttribute("TimeSheetModel")TimeSheetModel timeSheetModel, Model model) {
        String auth =  utilsService.auth();
        String result = utilsService.saveTimeSheet(timeSheetModel);
        model.addAttribute("mode",auth);
        return "redirect:/timesheet";
    }


}
