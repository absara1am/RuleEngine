package com.zeotap.rule_engine.RuleEngine.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;

@Data
@Entity
@Table(name = "rules")
public class Rule {

  @Id
  @GeneratedValue
  private UUID id;

  private String name;

  @Column(name = "rule_string", columnDefinition = "TEXT")
  private String ruleString;

  @Column(name = "ast_json", columnDefinition = "TEXT")
  private String astJson;

  @Column(name = "created_at")
  private java.time.LocalDateTime createdAt;

  @Column(name = "updated_at")
  private java.time.LocalDateTime updatedAt;
}
