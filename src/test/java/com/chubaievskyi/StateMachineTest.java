package com.chubaievskyi;

import com.chubaievskyi.configuration.StateMachineConfig;
import com.chubaievskyi.enums.Event;
import com.chubaievskyi.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { StateMachineConfig.class})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class StateMachineTest {

    @Autowired
    private StateMachine<Status, Event> stateMachine;


    void resetStateMachine(Status status) {
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(accessor -> accessor.resetStateMachine(new DefaultStateMachineContext<>(status,
                        null, null, null)));
    }

    void statusChangeTest(Status currentStatus, Event event, Status expectedStatus, int count) throws Exception {

        resetStateMachine(currentStatus);

        StateMachineTestPlan<Status, Event> plan =
                StateMachineTestPlanBuilder.<Status, Event>builder()
                        .stateMachine(stateMachine)
                        .step()
                        .expectState(currentStatus)
                        .and()
                        .step()
                        .sendEvent(event)
                        .expectStates(expectedStatus)
                        .expectStateChanged(count)
                        .and()
                        .build();
        plan.test();
    }

    @Test
    void testValidStatusOption() throws Exception {

        statusChangeTest(Status.NEW, Event.WORK_IN_PROGRESS, Status.WORK_IN_PROGRESS, 1);
        statusChangeTest(Status.NEW, Event.POSTPONED, Status.POSTPONED, 1);
        statusChangeTest(Status.NEW, Event.CANCELLED, Status.CANCELLED, 1);

        statusChangeTest(Status.WORK_IN_PROGRESS, Event.POSTPONED, Status.POSTPONED, 1);
        statusChangeTest(Status.WORK_IN_PROGRESS, Event.NOTIFIED, Status.NOTIFIED, 1);
        statusChangeTest(Status.WORK_IN_PROGRESS, Event.SIGNED, Status.SIGNED, 1);
        statusChangeTest(Status.WORK_IN_PROGRESS, Event.CANCELLED, Status.CANCELLED, 1);

        statusChangeTest(Status.POSTPONED, Event.WORK_IN_PROGRESS, Status.WORK_IN_PROGRESS, 1);
        statusChangeTest(Status.POSTPONED, Event.CANCELLED, Status.CANCELLED, 1);

        statusChangeTest(Status.NOTIFIED, Event.SIGNED, Status.SIGNED, 1);
        statusChangeTest(Status.NOTIFIED, Event.DONE, Status.DONE, 1);
        statusChangeTest(Status.NOTIFIED, Event.CANCELLED, Status.CANCELLED, 1);

        statusChangeTest(Status.SIGNED, Event.DONE, Status.DONE, 1);
        statusChangeTest(Status.SIGNED, Event.CANCELLED, Status.CANCELLED, 1);

    }

    @Test
    void testInvalidStatusOption() throws Exception {

        statusChangeTest(Status.NEW, Event.NOTIFIED, Status.NEW, 0);
        statusChangeTest(Status.NEW, Event.SIGNED, Status.NEW, 0);
        statusChangeTest(Status.NEW, Event.DONE, Status.NEW, 0);

        statusChangeTest(Status.WORK_IN_PROGRESS, Event.DONE, Status.WORK_IN_PROGRESS, 0);

        statusChangeTest(Status.POSTPONED, Event.NOTIFIED, Status.POSTPONED, 0);
        statusChangeTest(Status.POSTPONED, Event.SIGNED, Status.POSTPONED, 0);
        statusChangeTest(Status.POSTPONED, Event.DONE, Status.POSTPONED, 0);

        statusChangeTest(Status.NOTIFIED, Event.WORK_IN_PROGRESS, Status.NOTIFIED, 0);
        statusChangeTest(Status.NOTIFIED, Event.POSTPONED, Status.NOTIFIED, 0);

        statusChangeTest(Status.SIGNED, Event.WORK_IN_PROGRESS, Status.SIGNED, 0);
        statusChangeTest(Status.SIGNED, Event.POSTPONED, Status.SIGNED, 0);
        statusChangeTest(Status.SIGNED, Event.NOTIFIED, Status.SIGNED, 0);

        statusChangeTest(Status.DONE, Event.WORK_IN_PROGRESS, Status.DONE, 0);
        statusChangeTest(Status.DONE, Event.POSTPONED, Status.DONE, 0);
        statusChangeTest(Status.DONE, Event.NOTIFIED, Status.DONE, 0);
        statusChangeTest(Status.DONE, Event.SIGNED, Status.DONE, 0);
        statusChangeTest(Status.DONE, Event.CANCELLED, Status.DONE, 0);

        statusChangeTest(Status.CANCELLED, Event.WORK_IN_PROGRESS, Status.CANCELLED, 0);
        statusChangeTest(Status.CANCELLED, Event.POSTPONED, Status.CANCELLED, 0);
        statusChangeTest(Status.CANCELLED, Event.NOTIFIED, Status.CANCELLED, 0);
        statusChangeTest(Status.CANCELLED, Event.SIGNED, Status.CANCELLED, 0);
        statusChangeTest(Status.CANCELLED, Event.DONE, Status.CANCELLED, 0);


    }

    @Test
    void happyPassChangeStatusTest() throws Exception {
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
                        .sendEvent(Event.WORK_IN_PROGRESS)
                        .expectState(Status.WORK_IN_PROGRESS)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Event.NOTIFIED)
                        .expectState(Status.NOTIFIED)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Event.SIGNED)
                        .expectState(Status.SIGNED)
                        .expectStateChanged(1)
                        .and()
                        .step()
                        .sendEvent(Event.DONE)
                        .expectState(Status.DONE)
                        .expectStateChanged(1)
                        .and()
                        .build();
        plan.test();
    }

}