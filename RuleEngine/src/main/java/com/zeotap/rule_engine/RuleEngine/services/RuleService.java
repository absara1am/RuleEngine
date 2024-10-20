package com.zeotap.rule_engine.RuleEngine.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeotap.rule_engine.RuleEngine.entities.Rule;
import com.zeotap.rule_engine.RuleEngine.exceptions.RuleParsingException;
import com.zeotap.rule_engine.RuleEngine.models.ASTNode;
import com.zeotap.rule_engine.RuleEngine.models.OperatorNode;
import com.zeotap.rule_engine.RuleEngine.parsers.RuleParser;
import com.zeotap.rule_engine.RuleEngine.repositories.RuleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

  @Autowired
  private RuleRepository ruleRepository;

  @Autowired
  private ObjectMapper objectMapper;

  public Rule createRule(String ruleString) {
    try {
      RuleParser parser = new RuleParser();
      ASTNode ast = parser.parse(ruleString);
      String astJson = objectMapper.writeValueAsString(ast);

      Rule rule = new Rule();
      rule.setName("Rule_" + UUID.randomUUID());
      rule.setRuleString(ruleString);
      rule.setAstJson(astJson);
      rule.setCreatedAt(LocalDateTime.now());
      rule.setUpdatedAt(LocalDateTime.now());

      return ruleRepository.save(rule);
    } catch (RuleParsingException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Failed to create rule", e);
    }
  }

  public Rule combineRules(List<UUID> ruleIds) {
    try {
      List<Rule> rules = ruleRepository.findAllById(ruleIds);
      if (rules.isEmpty()) {
        throw new IllegalArgumentException("No rules found for provided IDs");
      }

      List<ASTNode> astNodes = new ArrayList<>();
      for (Rule rule : rules) {
        ASTNode astNode = objectMapper.readValue(
          rule.getAstJson(),
          ASTNode.class
        );
        astNodes.add(astNode);
      }

      ASTNode combinedAst = combineAstNodes(astNodes);

      String astJson = objectMapper.writeValueAsString(combinedAst);

      Rule combinedRule = new Rule();
      combinedRule.setName("CombinedRule_" + UUID.randomUUID());
      combinedRule.setRuleString("Combined Rule");
      combinedRule.setAstJson(astJson);
      combinedRule.setCreatedAt(LocalDateTime.now());
      combinedRule.setUpdatedAt(LocalDateTime.now());

      return ruleRepository.save(combinedRule);
    } catch (Exception e) {
      throw new RuntimeException("Failed to combine rules", e);
    }
  }

  private ASTNode combineAstNodes(List<ASTNode> astNodes) {
    ASTNode combined = astNodes.get(0);
    for (int i = 1; i < astNodes.size(); i++) {
      combined = new OperatorNode("AND", combined, astNodes.get(i));
    }
    return combined;
  }
}
