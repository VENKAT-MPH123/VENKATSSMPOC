package com.joshi.stmac.casemgmt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.joshi.stmac.casemgmt.conf.CaseEvents;
import com.joshi.stmac.casemgmt.conf.CaseStates;
import com.joshi.stmac.casemgmt.model.CaseRequestObj;
import com.joshi.stmac.casemgmt.model.CaseResponseObj;
import com.joshi.stmac.casemgmt.model.CaseStoreService;
import com.joshi.stmac.casemgmt.model.Roles;


@RestController
@RequestMapping("h3d/poc/")
public class SSMAccountCaseController {
	
	private static final Logger logger = LogManager.getLogger(SSMAccountCaseController.class);
	
	@Autowired
	StateMachineFactory<CaseStates, CaseEvents> stateMachineFactory;
	
	@Autowired
	CaseRequestObj caseRequestObj;
	
	@Autowired
	CaseResponseObj caseResponseObj;
	
	@Autowired
	CaseStoreService caseStoreService;
	
	@Autowired
	Roles roles;
	
//	StateMachine<CaseStates, CaseEvents> stateMachine;
	
	List resObjList = new ArrayList();
	
	Map smMap = new HashMap();
	
	Map allCasesMap = new HashMap();
	
	
	@RequestMapping(method=RequestMethod.POST, value="mph")
	public List processOfCreatingCase(@RequestBody CaseRequestObj obj, @RequestHeader ("role") String role) {
		logger.info("Entering processOfCreatingCase :: APICasesController");
		if (role.equals(roles.requester)) {
			obj = caseStoreService.caseStoreProcess(obj);
			if (obj != null) {
				StateMachine<CaseStates, CaseEvents> stateMachine = stateMachineFactory.getStateMachine(String.valueOf(caseResponseObj.getCaseNumber()));
				smMap.put(obj.getCaseNumber(), stateMachine);
				obj.setState(stateMachine.getState().getId().toString());
				smMap.put(caseResponseObj.getCaseNumber(), stateMachine);
			}
		obj.setRole(roles.requester);
		} else {
			obj.setState("Invalid Role");
			obj.setRole("");
		}
		resObjList.add(obj);
		return resObjList;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="mph/assignCase")
	public CaseResponseObj processOfAssignCase(@RequestBody CaseRequestObj obj, @RequestHeader ("role") String role) {
		logger.info("Entering processOfAssignCase :: APICasesController");
		if (role.equals(roles.dispatcher)) {
			caseResponseObj = caseStoreService.getTheCase(obj);
			if(caseResponseObj.getStatus().equals("Case Found")) {
				StateMachine<CaseStates, CaseEvents> stateMachine =(StateMachine) smMap.get(caseResponseObj.getCaseNumber());
				if (stateMachine.getState().getId().toString().equals("UNASSIGNED")) {
					stateMachine.sendEvent(CaseEvents.ASSIGN);
					caseResponseObj.setState(stateMachine.getState().getId().toString());
					caseStoreService.updateStateStatus(obj, stateMachine.getState().getId().toString(), role);
					caseResponseObj.setRole(roles.dispatcher);
				} else {
					caseResponseObj.setState("Invalid WorkFlow");
					obj.setRole("");
				}
			}
		} else {
			caseResponseObj.setState("Invalid Role");
			obj.setRole("");
		}
		return caseResponseObj;
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="mph/reassignCase")
	public CaseResponseObj processOfReassignCase(@RequestBody CaseRequestObj obj, @RequestHeader ("role") String role) {
		logger.info("Entering processOfAssignCase :: APICasesController");
		if (role.equals(roles.manager)) {
			caseResponseObj = caseStoreService.getTheCase(obj);
			if(caseResponseObj.getStatus().equals("Case Found")) {
				StateMachine<CaseStates, CaseEvents> stateMachine =(StateMachine) smMap.get(caseResponseObj.getCaseNumber());
				if (stateMachine.getState().getId().toString().equals("ASSIGNED")) {
			//		stateMachine.sendEvent(CaseEvents.ASSIGN);
					caseResponseObj.setState(stateMachine.getState().getId().toString());
					caseStoreService.updateStateStatus(obj, stateMachine.getState().getId().toString(),role);
					caseResponseObj.setRole(roles.manager);
				} else {
					caseResponseObj.setState("Invalid WorkFlow");
					obj.setRole("");
				}
			}
		} else {
			caseResponseObj.setState("Invalid Role");
			obj.setRole("");
		}
		return caseResponseObj;
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="mph/acceptCase")
	public CaseResponseObj processOfAcceptCase(@RequestBody CaseRequestObj obj, @RequestHeader ("role") String role) {
		logger.info("Entering processOfAcceptCase :: APICasesController");
		if (role.equals(roles.case_worker)) {
			caseResponseObj = caseStoreService.getTheCase(obj);
			if(caseResponseObj.getStatus().equals("Case Found")) {
				//	StateMachine<CaseStates, CaseEvents> stateMachine = stateMachineFactory.getStateMachine(String.valueOf(caseResponseObj.getCaseNumber()));
				StateMachine<CaseStates, CaseEvents> stateMachine =(StateMachine) smMap.get(caseResponseObj.getCaseNumber());
				if (stateMachine.getState().getId().toString().equals("ASSIGNED")) {
					stateMachine.sendEvent(CaseEvents.ACCEPT);
					caseResponseObj.setState(stateMachine.getState().getId().toString());
					caseStoreService.updateStateStatus(obj, stateMachine.getState().getId().toString(),role);
					caseResponseObj.setRole(roles.case_worker);
				} else {
					caseResponseObj.setState("Invalid WorkFlow");
					obj.setRole("");
				}
			}
		} else {
			caseResponseObj.setState("Invalid Role");
			obj.setRole("");
		}
		return caseResponseObj;
		
	}
	
	@RequestMapping(method=RequestMethod.POST, value="mph/denyCase")
	public CaseResponseObj processOfDenyCase(@RequestBody CaseRequestObj obj, @RequestHeader ("role") String role) {
		logger.info("Entering processOfAcceptCase :: APICasesController");
		if (role.equals(roles.case_worker)) {
			caseResponseObj = caseStoreService.getTheCase(obj);
			if(caseResponseObj.getStatus().equals("Case Found")) {
				StateMachine<CaseStates, CaseEvents> stateMachine =(StateMachine) smMap.get(caseResponseObj.getCaseNumber());
				if (stateMachine.getState().getId().toString().equals("ASSIGNED")) {
					stateMachine.sendEvent(CaseEvents.DENY);
					caseResponseObj.setState(stateMachine.getState().getId().toString());
					caseStoreService.updateStateStatus(obj, stateMachine.getState().getId().toString(),role);
					caseResponseObj.setRole(roles.case_worker);
				}else {
					caseResponseObj.setState("Invalid WorkFlow");
					obj.setRole("");
				}
				
			}
		} else {
			caseResponseObj.setState("Invalid Role");
			obj.setRole("");
		}
		return caseResponseObj;
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="mph")
	public Map processOfGettingAllCases() {
		logger.info("Entering processOfGettingAllCases :: APICasesController");
		Map allCasesMap = new HashMap();
		allCasesMap = caseStoreService.getAllCases();
		return allCasesMap;
	}
	
	@RequestMapping(value="mph/caseNo/{id}", method=RequestMethod.GET)
	public CaseRequestObj processOfGettingRequeirdCase(@PathVariable("id") int i) {
		logger.info("Entering processOfGettingRequeirdCase :: APICasesController");
		Map allCasesMap = new HashMap();
		CaseRequestObj tempRequestObj = caseStoreService.getRequestedCase(i);
		return tempRequestObj;
	}
}
