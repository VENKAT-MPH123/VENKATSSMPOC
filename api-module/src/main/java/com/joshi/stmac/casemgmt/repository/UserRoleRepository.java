package com.joshi.stmac.casemgmt.repository;


import com.joshi.stmac.casemgmt.model.UserRole;
import org.springframework.data.repository.CrudRepository;


public interface UserRoleRepository extends CrudRepository<UserRole, String> {
    //List<UserRole> findByRole(String userRoleId);
}
