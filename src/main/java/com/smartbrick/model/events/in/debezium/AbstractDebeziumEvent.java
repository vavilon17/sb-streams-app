package com.smartbrick.model.events.in.debezium;

import com.fasterxml.jackson.annotation.*;
import com.smartbrick.model.events.in.Project;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class)
})
@JsonIgnoreProperties({ "source", "ts_ms", "transaction" })
public abstract class AbstractDebeziumEvent<T> {

    @JsonProperty("before")
    private T before;
    @JsonProperty("after")
    private T after;
    @JsonProperty("op")
    private String op;

    public T getBefore() {
        return before;
    }

    public void setBefore(T before) {
        this.before = before;
    }

    public T getAfter() {
        return after;
    }

    public void setAfter(T after) {
        this.after = after;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
