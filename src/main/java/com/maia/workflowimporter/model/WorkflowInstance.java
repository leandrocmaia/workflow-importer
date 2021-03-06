package com.maia.workflowimporter.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class WorkflowInstance {
  Long id;
  Workflow workflow;
  Assignee assignee;
  Step step;
  Status status;

  public enum Status {
    RUNNING,
    NEW,
    DONE,
    PAUSED;
  }

  @AllArgsConstructor
  public enum Step {
    APPROVED("approved"),
    READY_FOR_ARCHIVING("ready for archiving"),
    SUPERVISOR_APPROVAL("supervisor approval"),
    WAITING_FOR_FEEDBACK("waiting for feedback"),
    INITIAL("initial"),
    LEGAL_APPROVAL("legal approval"),
    UNDEFINED("undefined");

    @Getter final String name;

    public static Step lookup(String name) {
      return Arrays.stream(Step.values())
          .filter(a -> a.getName().equals(name))
          .findAny()
          .orElse(Step.UNDEFINED);
    }
  }
}
