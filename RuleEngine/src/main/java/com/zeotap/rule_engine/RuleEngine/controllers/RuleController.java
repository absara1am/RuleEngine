package com.zeotap.rule_engine.RuleEngine.controllers;

import com.zeotap.rule_engine.RuleEngine.entities.Rule;
import com.zeotap.rule_engine.RuleEngine.services.EvaluationService;
import com.zeotap.rule_engine.RuleEngine.services.RuleService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rules")
public class RuleController {

  @Autowired
  private RuleService ruleService;

  @Autowired
  private EvaluationService evaluationService;

  @PostMapping("/create_rule")
  public ResponseEntity<Rule> createRule(
    @RequestBody Map<String, String> request
  ) {
    String ruleString = request.get("ruleString");
    Rule rule = ruleService.createRule(ruleString);
    return ResponseEntity.ok(rule);
  }

  @PostMapping("/combine_rules")
  public ResponseEntity<Rule> combineRules(
    @RequestBody Map<String, Object> request
  ) {
    @SuppressWarnings("unchecked")
    List<String> ruleIdsStr = (List<String>) request.get("ruleIds");
    List<UUID> ruleIds = ruleIdsStr.stream().map(UUID::fromString).toList();
    Rule combinedRule = ruleService.combineRules(ruleIds);
    return ResponseEntity.ok(combinedRule);
  }

  @PostMapping("/{id}/evaluate_rule")
  public ResponseEntity<Map<String, Boolean>> evaluateRule(
    @PathVariable UUID id,
    @RequestBody Map<String, Object> data
  ) {
    boolean result = evaluationService.evaluateRule(id, data);
    return ResponseEntity.ok(Map.of("result", result));
  }
}
