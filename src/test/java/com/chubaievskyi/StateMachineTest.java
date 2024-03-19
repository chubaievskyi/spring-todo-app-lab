package com.chubaievskyi;

import com.chubaievskyi.configuration.StateMachineConfig;
import com.chubaievskyi.entity.Event;
import com.chubaievskyi.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { StateMachineConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class StateMachineTests {

//    @Autowired
//    private StateMachineFactory<Status, Event> stateMachineFactory;

    @Autowired
    private StateMachine<Status, Event> stateMachine;

//    @BeforeEach
//    public void setup() throws Exception {
//        stateMachine = stateMachineFactory.getStateMachine();
//        // plan don't know how to wait if machine is started
//        // automatically so wait here.
//        for (int i = 0; i < 10; i++) {
//            if (stateMachine.getState() != null) {
//                break;
//            } else {
//                Thread.sleep(200);
//            }
//        }
//    }

    @Test
    void testInitial() throws Exception {
        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(Status.NEW)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    void testNewStatusValidOptions() throws Exception {
        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(Status.NEW)
                        .and()
                        .step()
                        .sendEvent(Event.WORK_IN_PROGRESS)
                        .expectStates(Status.WORK_IN_PROGRESS)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Event.POSTPONED)
                        .expectState(Status.POSTPONED)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Event.CANCELLED)
                        .expectState(Status.CANCELLED)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    void testNewStatusInvalidOptions() throws Exception {
        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(Status.NEW)
                        .and()
                        .step()
                        .sendEvent(Event.NOTIFIED)
                        .expectStates(Status.NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(Event.SIGNED)
                        .expectState(Status.NEW)
                        .expectStateChanged(0)
                        .and()
                        .step()
                        .sendEvent(Event.DONE)
                        .expectState(Status.NEW)
                        .expectStateChanged(0)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    void testWorkInProgressStatusValidOptions() throws Exception {

        resetStateMachine(Status.WORK_IN_PROGRESS);

        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(Status.WORK_IN_PROGRESS)
                        .and()
                        .step()
                        .sendEvent(Event.POSTPONED)
                        .expectState(Status.POSTPONED)
                        .expectStateChanged(1)
                        .and()
//                        .step()
//                        .sendEvent(Event.NOTIFIED)
//                        .expectState(Status.NOTIFIED)
//                        .expectStateChanged(1)
//                        .and()
//                        .step()
//                        .sendEvent(Event.SIGNED)
//                        .expectStates(Status.SIGNED)
//                        .expectStateChanged(1)
//                        .and()
                        .step()
                        .sendEvent(Event.CANCELLED)
                        .expectStates(Status.CANCELLED)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    void testWorkInProgressStatusInvalidOptions() throws Exception {

        resetStateMachine(Status.WORK_IN_PROGRESS);

        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(Status.WORK_IN_PROGRESS)
                        .and()
                        .step()
                        .sendEvent(Event.DONE)
                        .expectStates(Status.WORK_IN_PROGRESS)
                        .expectStateChanged(0)
                        .and()
                        .build();
        plan.test();
    }

    void resetStateMachine(Status status) {
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(accessor -> {
                    accessor.resetStateMachine(new DefaultStateMachineContext<>(status,
                            null, null, null));
                });
    }

}