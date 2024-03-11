package com.chubaievskyi.configuration;

import com.chubaievskyi.entity.Event;
import com.chubaievskyi.entity.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@Configuration
@EnableStateMachine
//@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<Status, Event> {

    @Override
    public void configure(StateMachineConfigurationConfigurer<Status, Event> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(listener());
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
                    .source(Status.NEW).target(Status.WORK_IN_PROGRESS).event(Event.START_WORK)
                    .and()
                .withExternal()
                    .source(Status.NEW).target(Status.POSTPONED).event(Event.POSTPONE)
                    .and()
                .withExternal()
                    .source(Status.NEW).target(Status.CANCELLED).event(Event.CANCEL)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.POSTPONED).event(Event.POSTPONE)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.NOTIFIED).event(Event.NOTIFY)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.SIGNED).event(Event.SIGN)
                    .and()
                .withExternal()
                    .source(Status.WORK_IN_PROGRESS).target(Status.CANCELLED).event(Event.CANCEL)
                    .and()
                .withExternal()
                    .source(Status.POSTPONED).target(Status.WORK_IN_PROGRESS).event(Event.RESTART)
                    .and()
                .withExternal()
                    .source(Status.POSTPONED).target(Status.CANCELLED).event(Event.CANCEL)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.SIGNED).event(Event.SIGN)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.DONE).event(Event.COMPLETE)
                    .and()
                .withExternal()
                    .source(Status.NOTIFIED).target(Status.CANCELLED).event(Event.CANCEL)
                    .and()
                .withExternal()
                    .source(Status.SIGNED).target(Status.DONE).event(Event.COMPLETE)
                    .and()
                .withExternal()
                    .source(Status.SIGNED).target(Status.CANCELLED).event(Event.CANCEL)
                    .and();
    }

    @Bean
    public StateMachineListener<Status, Event> listener() {
        return new StateMachineListenerAdapter<Status, Event>() {
            @Override
            public void stateChanged(State<Status, Event> from, State<Status, Event> to) {
//                System.out.println("State change to " + to.getId());
                log.info("Status change to {}", to.getId());
            }
        };
    }
}