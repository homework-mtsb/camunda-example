package ru.mts.homework;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceivedListener implements ExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        logger.info("received message in event, logging from listener");
    }
}
