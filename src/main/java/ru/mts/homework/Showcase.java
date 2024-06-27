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

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class Showcase {

  private final Logger logger = getLogger(this.getClass());

  @Autowired
  private RuntimeService runtimeService;

  private String processInstanceId;

  @EventListener
  public void notify(final PostDeployEvent unused) {
    processInstanceId = runtimeService.startProcessInstanceByKey("Sample").getProcessInstanceId();
    logger.info("started instance: {}", processInstanceId);
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }
}