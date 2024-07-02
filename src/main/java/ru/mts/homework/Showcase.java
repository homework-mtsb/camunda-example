/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.mts.homework;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Showcase {

  private final Logger logger = getLogger(this.getClass());

  @Autowired
  private RuntimeService runtimeService;

  @Autowired
  private RepositoryService repositoryService;

  private String processInstanceId;

  @EventListener
  public void notify(final PostDeployEvent unused) {
    processInstanceId = runtimeService.startProcessInstanceByKey("Sample").getProcessInstanceId();
    String waitProcessInstanceId = runtimeService.startProcessInstanceByKey("wait-process").getProcessInstanceId();


    String extId = runtimeService.startProcessInstanceByKey("wait-process", Map.of("extId", "1111")).getProcessInstanceId();
    String extId1 = runtimeService.startProcessInstanceByKey("wait-process", Map.of("extId", "2111")).getProcessInstanceId();

    logger.info("started instance: {}", processInstanceId);

    logger.info("started wait instance: {}", waitProcessInstanceId);
    List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
            .processInstanceId(waitProcessInstanceId).active().list();

    logger.info("Found " + list.size() + " instances with uuid " + waitProcessInstanceId);
    runtimeService.deleteProcessInstance(waitProcessInstanceId, "just");
    list = runtimeService.createProcessInstanceQuery()
            .processInstanceId(waitProcessInstanceId).active().list();

    logger.info("Found " + list.size() + " instances with uuid " + waitProcessInstanceId);


    List<ProcessInstance> list1 = runtimeService.createProcessInstanceQuery()
            .or()
            .variableValueEquals("extId", "1111")
            .variableValueEquals("extId", "2111")
            .endOr()
            .active().list();

    logger.info("found {} instances with or clause", list1.size());


    List<ProcessInstance> list2 = runtimeService.createProcessInstanceQuery()
            .or()
            .processInstanceId(extId)
            .processInstanceId(extId1)
            .endOr()
            .active().list();

    logger.info("found {} due to overriding", list2.size());
    logger.info("it has processInstId {}", list2.get(0).getProcessInstanceId());
    logger.info("extId1 {}", extId.equals(list2.get(0).getProcessInstanceId()));

    List<ProcessInstance> list3 = runtimeService.createProcessInstanceQuery().processInstanceIds(Set.of(extId1, extId)).list();
    logger.info("found {} instances with multimethod", list3.size());


    List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().active().listPage(0, 10);


    logger.info("processDefinitions {}", processDefinitions.size());


  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }
}
