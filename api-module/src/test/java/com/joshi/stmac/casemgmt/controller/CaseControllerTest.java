package com.joshi.stmac.casemgmt.controller;

import com.joshi.stmac.casemgmt.conf.CaseEvents;
import com.joshi.stmac.casemgmt.conf.CaseStates;
import com.joshi.stmac.casemgmt.model.CaseRequestObj;
import com.joshi.stmac.casemgmt.model.CaseResponseObj;
import com.joshi.stmac.casemgmt.model.CaseStoreService;
import com.joshi.stmac.casemgmt.model.Roles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CaseControllerTest {

	@Autowired
    StateMachineFactory<CaseStates,CaseEvents> stateMachineFactory;

    @Autowired
    CaseStoreService caseStoreService;

    @Autowired
    CaseRequestObj caseRequestObj;

    @Autowired
    CaseResponseObj caseResponseObj;
    
    @Autowired
	Roles roles;


    Map<Integer, CaseRequestObj> reqObjMap = new HashMap<Integer, CaseRequestObj>();

    @Before()
    public void setup()throws Exception{
        assertNotNull(stateMachineFactory);
        assertNotNull(caseStoreService);
        assertNotNull(reqObjMap);
        assertNotNull(caseRequestObj);
        assertNotNull(caseResponseObj);
    }

    @Test
    public void testCreatingCaseEmptyMap(){
        caseRequestObj.setAccountName("Mph");
        caseRequestObj.setLegalCompanyName("Mphasis");
        caseRequestObj.setRole("REQUESTER");
        reqObjMap.put(1,caseRequestObj);
        caseRequestObj=caseStoreService.caseStoreProcess(caseRequestObj);
        assertEquals("REQUESTER",caseRequestObj.getRole());
        assertEquals(1, caseRequestObj.getCaseNumber());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("ASSIGNED");
        assertNotNull(stateMachine);
        stateMachine.sendEvent(CaseEvents.ASSIGN);
        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
    }

    @Test
    public void testCreatingCaseInvalidData(){
        caseRequestObj.setLegalCompanyName("");
        caseRequestObj=caseStoreService.caseStoreProcess(caseRequestObj);
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("UNASSIGNED");
        assertNotNull(stateMachine);
        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
    }

    @Test
    public void testAssignCaseNotFound(){
        caseRequestObj.setAccountName("");
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("No Case Found", caseResponseObj.getStatus());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("UNASSIGNED");
        assertNotNull(stateMachine);

        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
    }

    @Test
    public void testAssignCaseFound(){
        caseRequestObj.setAccountName("Mph");
        caseRequestObj.setLegalCompanyName("Mphasis");
        caseRequestObj.setCaseNumber(1);
        caseRequestObj.setRole("DISPATCHER");
        reqObjMap.put(1,caseRequestObj);
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("DISPATCHER",caseRequestObj.getRole());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("ASSIGNED");
        assertNotNull(stateMachine);
        stateMachine.sendEvent(CaseEvents.ASSIGN);
        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
    }
    
    @Test
    public void testReassignCaseNotFound(){
        caseRequestObj.setAccountName("");
        caseRequestObj.setCaseNumber(0);
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("No Case Found", caseResponseObj.getStatus());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("UNASSIGNED");
        assertNotNull(stateMachine);

        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
    }
    
    @Test
    public void testReassignCaseFound(){
        caseRequestObj.setAccountName("Mph");
        caseRequestObj.setLegalCompanyName("Mphasis");
        caseRequestObj.setCaseNumber(1);
        caseRequestObj.setRole("MANAGER");
        reqObjMap.put(1,caseRequestObj);
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("MANAGER",caseRequestObj.getRole());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("ASSIGNED");
        assertNotNull(stateMachine);
        stateMachine.sendEvent(CaseEvents.ASSIGN);
        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
    }

    @Test
    public void testAcceptCaseNotFound(){
        caseRequestObj.setAccountName("");

        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("No Case Found", caseResponseObj.getStatus());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("UNASSIGNED");
        assertNotNull(stateMachine);

        assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
    }

    @Test
    public void testAcceptCaseFound(){
        caseRequestObj.setAccountName("Mph");
        caseRequestObj.setLegalCompanyName("Mphasis");
        caseRequestObj.setCaseNumber(1);
        caseRequestObj.setRole("CASE WORKER");
        reqObjMap.put(1,caseRequestObj);
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("CASE WORKER",caseRequestObj.getRole());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("ACCEPTED");
        assertNotNull(stateMachine);

    }

    @Test
    public void testDenyCaseNotFound(){
        caseRequestObj.setAccountName("");
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);

        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("UNASSIGNED");
        assertNotNull(stateMachine);

    }

    @Test
    public void testDenyCaseFound(){
        caseRequestObj.setAccountName("Mph");
        caseRequestObj.setLegalCompanyName("Mphasis");
        caseRequestObj.setCaseNumber(1);
        caseRequestObj.setRole("CASE WORKER");
        reqObjMap.put(1,caseRequestObj);
        caseResponseObj=caseStoreService.getTheCase(caseRequestObj);
        assertNotNull(caseResponseObj);
        assertEquals("CASE WORKER",caseRequestObj.getRole());
        StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("ASSIGNED");
        assertNotNull(stateMachine);
    }

    private StateMachine<CaseStates, CaseEvents> buildStateMachine(String id) {
        StateMachine<CaseStates, CaseEvents> stateMachine = stateMachineFactory.getStateMachine(id);

        for (int i = 0; i < 10; i++) {
            if (stateMachine.getState() != null) {
                break;
            } else {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    stateMachine = null;
                    break;
                }
            }
        }
        return stateMachine;

    }
}
