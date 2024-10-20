package com.zeotap.rule_engine.RuleEngine.repositories;

import com.zeotap.rule_engine.RuleEngine.entities.Rule;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {}
