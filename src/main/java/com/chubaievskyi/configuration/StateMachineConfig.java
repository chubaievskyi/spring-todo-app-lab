package com.chubaievskyi.configuration;

import com.chubaievskyi.enums.Event;
import com.chubaievskyi.enums.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<Status, Event> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<Status, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<Status, Event> states) throws Exception {
        states
                .withStates()
                .initial(Status.NEW)
                .states(EnumSet.allOf(Status.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Status, Event> transitions) throws Exception {
        transitions
                .withExternal()
                    .source(Status.NEW).target(Status.WORK_IN_PROGRESS).event(Event.WORK_IN_PROGRESS)
                    .and()
                .withExternal()
                    .source(Status.NEW).target(Status.POSTPONED).event(Event.POSTPONED)
                    .and()
                .withExternal()
                    .source(Status.NEW).target(Status.CANCELLED).event(Event.CANCELLED)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.POSTPONED).event(Event.POSTPONED)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.NOTIFIED).event(Event.NOTIFIED)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.SIGNED).event(Event.SIGNED)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.CANCELLED).event(Event.CANCELLED)
                    .and()
                .withExternal()
                    .source(Status.POSTPONED).target(Status.WORK_IN_PROGRESS).event(Event.WORK_IN_PROGRESS)
                    .and()
                .withExternal()
                    .source(Status.POSTPONED).target(Status.CANCELLED).event(Event.CANCELLED)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.SIGNED).event(Event.SIGNED)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.DONE).event(Event.DONE)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.CANCELLED).event(Event.CANCELLED)
                    .and()
                .withExternal()
                    .source(Status.SIGNED).target(Status.DONE).event(Event.DONE)
                    .and()
                .withExternal()
                    .source(Status.SIGNED).target(Status.CANCELLED).event(Event.CANCELLED)
                    .and();
    }
}