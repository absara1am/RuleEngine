package com.zeotap.rule_engine.RuleEngine.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonTypeName("operand")
public class OperandNode implements ASTNode {

  private String attribute;
  private String operator;
  private Object value;

  @Override
  public boolean evaluate(Map<String, Object> data) {
    Object attributeValue = data.get(attribute);

    if (attributeValue == null) {
      return false;
    }

    try {
      if (attributeValue instanceof Number && value instanceof Number) {
        return evaluateNumbers((Number) attributeValue, (Number) value);
      } else if (attributeValue instanceof String && value instanceof String) {
        return evaluateStrings((String) attributeValue, (String) value);
      } else {
        throw new RuntimeException(
          "Incompatible types for comparison: " +
          attributeValue.getClass() +
          " and " +
          value.getClass()
        );
      }
    } catch (ClassCastException e) {
      throw new RuntimeException("Attribute and value must be comparable", e);
    }
  }

  private boolean evaluateNumbers(Number attrValue, Number ruleValue) {
    double attrDouble = attrValue.doubleValue();
    double ruleDouble = ruleValue.doubleValue();

    switch (operator) {
      case ">":
        return attrDouble > ruleDouble;
      case "<":
        return attrDouble < ruleDouble;
      case "=":
      case "==":
        return attrDouble == ruleDouble;
      case ">=":
        return attrDouble >= ruleDouble;
      case "<=":
        return attrDouble <= ruleDouble;
      case "!=":
        return attrDouble != ruleDouble;
      default:
        throw new UnsupportedOperationException(
          "Unknown operator: " + operator
        );
    }
  }

  private boolean evaluateStrings(String attrValue, String ruleValue) {
    switch (operator) {
      case "=":
      case "==":
        return attrValue.equals(ruleValue);
      case "!=":
        return !attrValue.equals(ruleValue);
      default:
        throw new UnsupportedOperationException(
          "Unknown operator for String comparison: " + operator
        );
    }
  }
}
