package com.joshi.stmac.casemgmt.conf;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.joshi.stmac.casemgmt.controller.SSMAccountCaseController;


@Configuration
public class StateMachineConfig {
	
	private static final Logger logger = LogManager.getLogger(StateMachineConfig.class);
	
	@Bean
	public StateMachineLogListener stateMachineLogListener() {
		return new StateMachineLogListener();
	}
	
	@Configuration
	@EnableStateMachineFactory 
	public static class Config extends EnumStateMachineConfigurerAdapter<CaseStates, CaseEvents> {
		
	@Autowired
	private StateMachineLogListener stateMachineLogListener;
		
	@Override
    public void configure(StateMachineConfigurationConfigurer<CaseStates, CaseEvents> config)
            throws Exception {
		logger.info("Entering configure config :: StateMachineConfig");
        config
            .withConfiguration()
            	.machineId("mymachine")
                .autoStartup(true)
                .listener(stateMachineLogListener);
    }
	
    @Override
	public void configure(StateMachineStateConfigurer<CaseStates, CaseEvents> states)
            throws Exception {
    	logger.info("Entering configure states :: StateMachineConfig");
        states
            .withStates()
                .initial(CaseStates.UNASSIGNED)
                .state(CaseStates.ASSIGNED)
                .end(CaseStates.ACCEPTED)
                .end(CaseStates.DENIED);
    }
	
	@Override
    public void configure(StateMachineTransitionConfigurer<CaseStates, CaseEvents> transitions)
            throws Exception {
		logger.info("Entering configure transitions :: StateMachineConfig");
        transitions
            .withExternal()
                .source(CaseStates.UNASSIGNED)
                .target(CaseStates.ASSIGNED)
                .event(CaseEvents.ASSIGN)
                .and()
            .withExternal()
                .source(CaseStates.ASSIGNED)
                .target(CaseStates.ASSIGNED)
                .event(CaseEvents.REASSIGN)
                .and()
            .withExternal()
                .source(CaseStates.ASSIGNED)
                .target(CaseStates.ACCEPTED)
                .event(CaseEvents.ACCEPT)
                .and()
            .withExternal()
            	.source(CaseStates.ASSIGNED)
            	.target(CaseStates.DENIED)
            	.event(CaseEvents.DENY);
    	}
    
	}

}

