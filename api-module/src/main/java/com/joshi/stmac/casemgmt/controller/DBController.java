package com.joshi.stmac.casemgmt.controller;

import com.joshi.stmac.casemgmt.model.CaseManagementModel;
import com.joshi.stmac.casemgmt.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DBController {
    @Autowired
    CaseRepository repository;

    @RequestMapping("/save")
    public String process(){
        repository.save(new CaseManagementModel("105","Unassigned","Mphasis", "Mph"));
        repository.save(new CaseManagementModel("106","Assigned","Mphasis", "WF"));
        repository.save(new CaseManagementModel("109","Accepted","Mphasis", "JPMC"));
        repository.save(new CaseManagementModel("110","Deny","Mphasis", "CS"));

        return "Done";
    }

    @RequestMapping("/findById")
    public String findById(@RequestParam("id") String id ){
        String result="";
        result=repository.findOne(id).toString();
        return result;
    }
}

