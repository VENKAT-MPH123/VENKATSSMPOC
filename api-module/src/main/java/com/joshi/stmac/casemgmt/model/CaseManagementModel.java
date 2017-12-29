package com.joshi.stmac.casemgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "casedetails")
public class CaseManagementModel {
    @Id
    public String caseId;
    @Column(name = "legalCompanyName")
    public String legalCompanyName;
    @Column(name = "accountName")
    public String accountName;
    @Column(name = "caseState")
    public String caseState;
    @Column(name = "userRole")
    public String userRole;


    public CaseManagementModel() {
    }

    public CaseManagementModel(String caseId, String caseState, String legalCompanyName, String accountName,String userRole) {
        this.caseId = caseId;
        this.caseState=caseState;
        this.legalCompanyName = legalCompanyName;
        this.accountName = accountName;
        this.userRole=userRole;
    }

    public String getLegalCompanyName() {
        return legalCompanyName;
    }

    public String getCaseState() {
        return caseState;
    }

    public void setCaseState(String caseState) {
        this.caseState = caseState;
    }

    public void setLegalCompanyName(String legalCompanyName) {
        this.legalCompanyName = legalCompanyName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return String.format("Case[caseId=%s,caseState=%s, legalCompanyName='%s', accountName='%s',userRole='%s']", caseId,caseState, legalCompanyName, accountName,userRole);
    }
}
