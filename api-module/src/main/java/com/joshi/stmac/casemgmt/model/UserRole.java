package com.joshi.stmac.casemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "UserRole")
public class UserRole {

    @Id
    public String userRoleId;

    @Column(name="userRole")
    public String userRole;

    @Column(name="caseState")
    public String caseState;

    @Column(name="time_stamp")
    public String time_stamp;

    public UserRole() {
    }

    public UserRole(String userRoleId, String userRole, String caseState, String timestamp) {
        this.userRoleId=userRoleId;
        this.userRole = userRole;
        this.caseState = caseState;
        this.time_stamp = timestamp;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public String toString() {
        return String.format("UserRole[userRoleId=%s,userRole=%s,caseState=%s, timeStamp='%s']", userRoleId,userRole,caseState,time_stamp);
    }
}
