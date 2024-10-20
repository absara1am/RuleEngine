package com.zeotap.rule_engine.RuleEngine;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.zeotap.rule_engine.RuleEngine.exceptions.RuleParsingException;
import com.zeotap.rule_engine.RuleEngine.models.ASTNode;
import com.zeotap.rule_engine.RuleEngine.parsers.RuleParser;
import org.junit.jupiter.api.Test;

public class RuleParserTest {

  @Test
  public void testValidRuleParsing() {
    String ruleString = "(age > 30 AND department = 'Sales')";
    RuleParser parser = new RuleParser();
    try {
      ASTNode ast = parser.parse(ruleString);
      assertNotNull(ast);
      // Additional assertions to verify the AST structure
    } catch (RuleParsingException e) {
      fail("Parsing failed with exception: " + e.getMessage());
    }
  }

  @Test
  public void testInvalidRuleParsing() {
    String ruleString = "(age > 30 AND)";
    RuleParser parser = new RuleParser();
    assertThrows(
      RuleParsingException.class,
      () -> {
        parser.parse(ruleString);
      }
    );
  }
}
