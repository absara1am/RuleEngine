package com.zeotap.rule_engine.RuleEngine.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Map;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type"
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = OperandNode.class, name = "operand"),
    @JsonSubTypes.Type(value = OperatorNode.class, name = "operator"),
  }
)
public interface ASTNode {
  boolean evaluate(Map<String, Object> data);
}
