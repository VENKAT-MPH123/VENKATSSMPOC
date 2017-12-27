package com.joshi.stmac.casemgmt.conf;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateContext.Stage;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

public class StateMachineLogListener extends StateMachineListenerAdapter<CaseStates, CaseEvents> {
	
	private static final Logger logger = LogManager.getLogger(StateMachineLogListener.class);
	
	private final LinkedList<String> messages = new LinkedList<String>();

	public List<String> getMessages() {
		return messages;
	}

	public void resetMessages() {
		messages.clear();
	}

	@Override
	public void stateContext(StateContext<CaseStates, CaseEvents> stateContext) {
		logger.info("Entering stateContext :: StateMachineLogListener");
		if (stateContext.getStage() == Stage.STATE_ENTRY) {
			messages.addFirst(stateContext.getStateMachine().getId() + " enter " + stateContext.getTarget().getId());
		} else if (stateContext.getStage() == Stage.STATE_EXIT) {
			messages.addFirst(stateContext.getStateMachine().getId() + " exit " + stateContext.getSource().getId());
		} else if (stateContext.getStage() == Stage.EVENT_NOT_ACCEPTED) {
			messages.addFirst(stateContext.getStateMachine().getId() + " event " + stateContext.getEvent().name() + " is not accepted");
			stateContext.getStateMachine().setStateMachineError(new Exception("Event not accepted."));
		} else if (stateContext.getStage() == Stage.STATE_CHANGED) {
			messages.addFirst(stateContext.getStateMachine().getId() 
					+ " changed "); 
		}
	}

}
