package com.zeotap.rule_engine.RuleEngine.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeotap.rule_engine.RuleEngine.entities.Rule;
import com.zeotap.rule_engine.RuleEngine.models.ASTNode;
import com.zeotap.rule_engine.RuleEngine.repositories.RuleRepository;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

  @Autowired
  private RuleRepository ruleRepository;

  @Autowired
  private ObjectMapper objectMapper;

  public boolean evaluateRule(UUID ruleId, Map<String, Object> data) {
    try {
      Rule rule = ruleRepository
        .findById(ruleId)
        .orElseThrow(() -> new IllegalArgumentException("Rule not found"));

      ASTNode astNode = objectMapper.readValue(
        rule.getAstJson(),
        ASTNode.class
      );
      return astNode.evaluate(data);
    } catch (Exception e) {
      throw new RuntimeException("Failed to evaluate rule", e);
    }
  }
}
