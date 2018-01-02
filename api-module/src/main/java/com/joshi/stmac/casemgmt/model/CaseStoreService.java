package com.joshi.stmac.casemgmt.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joshi.stmac.casemgmt.repository.CaseRepository;


@Service
public class CaseStoreService {
	private static final Logger logger = LogManager.getLogger(CaseStoreService.class);
	
	private Map<Integer, CaseRequestObj> reqObjMap = new HashMap<Integer, CaseRequestObj>();
	
	@Autowired
	CaseResponseObj caseResponseObj;
	
	@Autowired
	CaseMgmtMapper caseMgmtMapper;
	
	public CaseRequestObj caseStoreProcess(CaseRequestObj obj) {
		logger.info("Entering caseStoreProcess :: CaseStoreService");
		if (obj.getLegalCompanyName() != null && obj.getAccountName() != null) {
			if (reqObjMap.size() == 0) {
				obj.setCaseNumber(1);
				reqObjMap.put(1, obj);
			} else {
				int i = reqObjMap.size();
				obj.setCaseNumber(i + 1);
				reqObjMap.put(i + 1, obj);
				caseResponseObj.setStatus("New Case created");
			}
		} else {
			obj.setLegalCompanyName("");
			obj.setAccountName("");
			obj.setCaseNumber(0);
			obj.setState("Invalid Data");
		}
		return obj;
	}
	
	public boolean casePersistanceStoreProcess(CaseRequestObj obj, CaseRepository repository) {
		boolean flag = caseMgmtMapper.caseDetailsStore(obj, repository);
		return flag;
	}
	
	public void updateStateStatus(CaseRequestObj obj, String state, String role) {
		logger.info("Entering updateStateStatus :: CaseStoreService");
		CaseRequestObj tempObj = reqObjMap.get(obj.getCaseNumber());
		tempObj.setState(state);
		tempObj.setRole(role);
		reqObjMap.put(obj.getCaseNumber(), tempObj);
	}
	
	public CaseResponseObj getTheCase(CaseRequestObj obj) {
		logger.info("Entering getTheCase :: CaseStoreService");
		if (reqObjMap.containsKey(obj.getCaseNumber())) {
			obj = reqObjMap.get(obj.getCaseNumber());
			caseResponseObj.setLegalCompanyName(obj.getLegalCompanyName());
			caseResponseObj.setAccountName(obj.getAccountName());
			caseResponseObj.setCaseNumber(obj.getCaseNumber());
			caseResponseObj.setRole(obj.getRole());
			caseResponseObj.setStatus("Case Found");
		} else {
			caseResponseObj.setLegalCompanyName("");
			caseResponseObj.setAccountName("");
			caseResponseObj.setCaseNumber(0);
			caseResponseObj.setStatus("No Case Found");
		}

		return caseResponseObj;
	}
	
	public CaseResponseObj getTheCasePersistance(int caseId, CaseRepository repository)
	{
		logger.info("Entering getMyCase :: CaseStoreService");
		CaseManagementModel caseManagementModel = null;
		try {
			caseManagementModel = caseMgmtMapper.getRequiredCase(caseId, repository);
			int i = Integer.valueOf(caseManagementModel.getCaseId());
			caseResponseObj.setCaseNumber(i);
			caseResponseObj.setLegalCompanyName(caseManagementModel.getLegalCompanyName());
			caseResponseObj.setAccountName(caseManagementModel.getAccountName());
			caseResponseObj.setRole(caseManagementModel.getUserRole());
			caseResponseObj.setState(caseManagementModel.getCaseState());
			caseResponseObj.setStatus("Case Created succeesfully");
		} catch (Exception e) {
			caseResponseObj.setCaseNumber(0);
			caseResponseObj.setLegalCompanyName("");
			caseResponseObj.setAccountName("");
			caseResponseObj.setRole("");
			caseResponseObj.setState("");
			caseResponseObj.setStatus("Problem while creating case");
			e.printStackTrace();
		}
		return caseResponseObj;
	}
	
	public void updateStateStatusPersistance(CaseRequestObj obj, String state, String role, CaseRepository repository) {
		logger.info("Entering updateStateStatusPersistance :: CaseStoreService");
		caseMgmtMapper.updateCaseStateandRole(obj.getCaseNumber(), state, role, repository);
	}
	
	public Map getAllCases() {
		logger.info("Entering getAllCases :: CaseStoreService");
		return reqObjMap;
	}
	
	public CaseRequestObj getRequestedCase(int i) {
		logger.info("Entering getRequestedCase :: CaseStoreService");
		CaseRequestObj tempRequestObj = (CaseRequestObj) reqObjMap.get(i);
		return tempRequestObj;
	}

}
