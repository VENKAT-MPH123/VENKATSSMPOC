package com.joshi.stmac.casemgmt.controller;


import com.joshi.stmac.casemgmt.model.UserRole;
import com.joshi.stmac.casemgmt.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRoleController {

    @Autowired
    UserRoleRepository userRoleRepository;

    @RequestMapping("/saveuserrole")
    public String saveUserRole(){
        userRoleRepository.save(new UserRole("501","Requestor","UnAssigned","12/27/2017"));

        userRoleRepository.save(new UserRole("503","Manager","Accepted","12/27/2017"));
        userRoleRepository.save(new UserRole("504","Case Worker","Accepted","12/27/2017"));
        return "Done";
    }
}
