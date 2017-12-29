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
    public String caseState;

    //second model: Userrole,state,timestamp
    public CaseManagementModel() {
    }

    public CaseManagementModel(String caseId, String caseState, String legalCompanyName, String accountName) {
        this.caseId = caseId;
        this.caseState=caseState;
        this.legalCompanyName = legalCompanyName;
        this.accountName = accountName;
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

    @Override
    public String toString() {
        return String.format("Case[caseId=%s,caseState=%s, legalCompanyName='%s', accountName='%s']", caseId,caseState, legalCompanyName, accountName);
    }
}
