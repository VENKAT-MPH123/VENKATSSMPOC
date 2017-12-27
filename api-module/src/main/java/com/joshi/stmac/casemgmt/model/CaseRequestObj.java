package com.joshi.stmac.casemgmt.model;

import org.springframework.stereotype.Service;

@Service
public class CaseRequestObj {
	private String legalCompanyName;
	private String accountName;
	private int caseNumber;
	private String state;
	private String role;
	
	public String getLegalCompanyName() {
		return legalCompanyName;
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
	public int getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(int caseNumber) {
		this.caseNumber = caseNumber;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AccountBean [legalCompanyName=" + legalCompanyName + ", accountName=" + accountName + ", caseNumber="
				+ caseNumber + "]";
	}

}
