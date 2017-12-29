package com.joshi.stmac.casemgmt.conf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.joshi.stmac.casemgmt.conf.CaseEvents;
import com.joshi.stmac.casemgmt.conf.CaseStates;
import com.joshi.stmac.casemgmt.conf.StateMachineLogListener;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateMachineConfigTest {
	
	@Autowired
	private StateMachineFactory<CaseStates, CaseEvents> stateMachineFactory;
	
	@Autowired
	private StateMachineLogListener listener;


//	private StateMachine<CaseStates, CaseEvents> stateMachine;

	@Test
	public void testInitial() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Initial");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		System.out.printf("testInitial::Current State: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.println("");
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
	}
	
	@Test
	public void testAssign() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Assign");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		
		stateMachine.sendEvent(CaseEvents.ASSIGN);
		System.out.printf("testAssign::State After Assign Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
	}
	
	@Test
	public void testInvalidAccept() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Invalid-Accept");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.ACCEPT);
		System.out.printf("testInvalidAccept::State After Accept Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertTrue(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
	}
	
	@Test
	public void testValidAccept() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Valid-Accept");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		
		stateMachine.sendEvent(CaseEvents.ASSIGN);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.ACCEPT);
		System.out.printf("testValidAccept::State After Accept Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertFalse(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ACCEPTED);
	}
	
	@Test
	public void testInvalidDeny() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Invalid-Deny");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.DENY);
		System.out.printf("testInvalidDeny::State After Deny Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertTrue(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.UNASSIGNED);
	}
	
	@Test
	public void testValidDeny() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Valid-Deny");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		
		stateMachine.sendEvent(CaseEvents.ASSIGN);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.DENY);
		System.out.printf("testValidDeny::State After Deny Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertFalse(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.DENIED);
	}
	
	@Test
	public void testInvalidDenyToAccept() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Invalid-Deny-To-Accept");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		
		stateMachine.sendEvent(CaseEvents.ASSIGN);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.DENY);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.DENIED);
		
		stateMachine.sendEvent(CaseEvents.ACCEPT);
		System.out.printf("testInvalidDenyToAccept::State After Accept Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertTrue(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.DENIED);
	}
	
	@Test
	public void testInvalidAcceptToDeny() {
		StateMachine<CaseStates, CaseEvents> stateMachine = buildStateMachine("CSM-Invalid-Accept-To-Deny");
		assertNotNull(stateMachine);
		assertTrue(stateMachine.getState() != null);
		
		stateMachine.sendEvent(CaseEvents.ASSIGN);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ASSIGNED);
		
		stateMachine.sendEvent(CaseEvents.ACCEPT);
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ACCEPTED);
		
		stateMachine.sendEvent(CaseEvents.DENY);
		System.out.printf("testInvalidAcceptToDeny::State After Deny Event: %s and the SM is %s", stateMachine.getState().getId(), (stateMachine.isComplete()?"completed" : "not completed"));
		System.out.printf("::Has Error: %s", stateMachine.hasStateMachineError());
		System.out.println("");
		assertTrue(stateMachine.hasStateMachineError());
		assertThat(stateMachine.getState().getId()).isEqualTo(CaseStates.ACCEPTED);
	}


	
	@Before
	public void setup() throws Exception {
		assertNotNull(stateMachineFactory);
		assertNotNull(listener);
		listener.resetMessages();
	}
	
	private StateMachine<CaseStates, CaseEvents> buildStateMachine(String id) {
		StateMachine<CaseStates, CaseEvents> stateMachine = stateMachineFactory.getStateMachine(id);
		// plan don't know how to wait if machine is started
		// automatically so wait here.
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

	@After
	public void teardown() throws Exception {
		if (listener != null) {
			System.out.println("*****");
			System.out.println("State Machine Listener Messages:");
			for(String msg: listener.getMessages()) {
				System.out.println(msg);
			}
			System.out.println("*****");
		}
	}


}
