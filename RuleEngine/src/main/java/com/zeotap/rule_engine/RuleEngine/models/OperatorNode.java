package com.zeotap.rule_engine.RuleEngine.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeName("operator")
public class OperatorNode implements ASTNode {

  private String operator;
  private ASTNode left;
  private ASTNode right;

  @Override
  public boolean evaluate(Map<String, Object> data) {
    boolean leftResult = left.evaluate(data);
    boolean rightResult = right.evaluate(data);

    switch (operator.toUpperCase()) {
      case "AND":
        return leftResult && rightResult;
      case "OR":
        return leftResult || rightResult;
      default:
        throw new UnsupportedOperationException(
          "Unknown operator: " + operator
        );
    }
  }
}
