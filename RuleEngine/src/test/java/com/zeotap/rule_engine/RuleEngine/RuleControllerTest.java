package com.zeotap.rule_engine.RuleEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zeotap.rule_engine.RuleEngine.services.EvaluationService;
import com.zeotap.rule_engine.RuleEngine.services.RuleService;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuleControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private RuleService ruleService;

  @Autowired
  private EvaluationService evaluationService;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testCreateRule() {
    String url = "http://localhost:" + port + "/rules";
    Map<String, String> request = Map.of(
      "ruleString",
      "(age > 30 AND department = 'Sales')"
    );
    ResponseEntity<Map> response = restTemplate.postForEntity(
      url,
      request,
      Map.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().get("id"));
  }

  @Test
  public void testEvaluateRule() {
    // First, create a rule
    String ruleString = "(age > 30 AND department = 'Sales')";
    var rule = ruleService.createRule(ruleString);

    // Then, evaluate the rule
    String url =
      "http://localhost:" + port + "/rules/" + rule.getId() + "/evaluate";
    Map<String, Object> data = Map.of("age", 35, "department", "Sales");
    ResponseEntity<Map> response = restTemplate.postForEntity(
      url,
      data,
      Map.class
    );
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue((Boolean) response.getBody().get("result"));
  }
}
