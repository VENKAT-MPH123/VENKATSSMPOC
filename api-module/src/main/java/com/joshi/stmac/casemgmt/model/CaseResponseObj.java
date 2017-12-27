package com.joshi.stmac.casemgmt.model;

import org.springframework.stereotype.Service;

@Service
public class CaseResponseObj {
	private String status;
	private String legalCompanyName;
	private String accountName;
	private int caseNumber;
	private String state;
	private String role;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
