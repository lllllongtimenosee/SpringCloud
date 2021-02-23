package com.taryn._08executionFramework;

public class TaskResult<R> {

    private final TaskResultType taskResultType;

    private final R r;

    private final String reason;

    public TaskResult(TaskResultType taskResultType, R r, String reason) {
        this.taskResultType = taskResultType;
        this.r = r;
        this.reason = reason;
    }

    public TaskResult(TaskResultType taskResultType, R r) {
        this.taskResultType = taskResultType;
        this.r = r;
        this.reason = "Success";
    }

    public TaskResultType getTaskResultType() {
        return taskResultType;
    }

    public R getR() {
        return r;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskResultType=" + taskResultType +
                ", r=" + r +
                ", reason='" + reason + '\'' +
                '}';
    }
}
