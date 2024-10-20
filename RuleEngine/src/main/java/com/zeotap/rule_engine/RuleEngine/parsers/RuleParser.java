package com.zeotap.rule_engine.RuleEngine.parsers;

import com.zeotap.rule_engine.RuleEngine.exceptions.RuleParsingException;
import com.zeotap.rule_engine.RuleEngine.models.ASTNode;
import com.zeotap.rule_engine.RuleEngine.models.OperandNode;
import com.zeotap.rule_engine.RuleEngine.models.OperatorNode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleParser {

  private List<Token> tokens;
  private int currentTokenIndex;

  public ASTNode parse(String ruleString) throws RuleParsingException {
    this.tokens = tokenize(ruleString);
    this.currentTokenIndex = 0;
    return parseExpression();
  }

  private List<Token> tokenize(String ruleString) throws RuleParsingException {
    List<Token> tokens = new ArrayList<>();
    String regex = "\\s*(\\(|\\)|AND|OR|[><=!]=?|\\w+|'[^']*'|\"[^\"]*\")\\s*";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(ruleString);

    while (matcher.find()) {
      String tokenValue = matcher.group(1).trim();
      Token token = createToken(tokenValue);
      tokens.add(token);
    }

    return tokens;
  }

  private Token createToken(String tokenValue) throws RuleParsingException {
    if (
      tokenValue.equalsIgnoreCase("AND") || tokenValue.equalsIgnoreCase("OR")
    ) {
      return new Token(Token.TokenType.OPERATOR, tokenValue.toUpperCase());
    } else if (tokenValue.equals("(") || tokenValue.equals(")")) {
      return new Token(Token.TokenType.PARENTHESIS, tokenValue);
    } else if (tokenValue.matches("[><=!]=?")) {
      return new Token(Token.TokenType.COMPARATOR, tokenValue);
    } else if (
      tokenValue.matches("'[^']*'") || tokenValue.matches("\"[^\"]*\"")
    ) {
      return new Token(
        Token.TokenType.VALUE,
        tokenValue.substring(1, tokenValue.length() - 1)
      );
    } else if (tokenValue.matches("\\w+")) {
      return new Token(Token.TokenType.ATTRIBUTE, tokenValue);
    } else {
      throw new RuleParsingException("Unknown token: " + tokenValue);
    }
  }

  private ASTNode parseExpression() throws RuleParsingException {
    ASTNode node = parseTerm();

    while (
      currentTokenIndex < tokens.size() &&
      tokens.get(currentTokenIndex).getType() == Token.TokenType.OPERATOR &&
      tokens.get(currentTokenIndex).getValue().equals("OR")
    ) {
      Token operatorToken = tokens.get(currentTokenIndex++);
      ASTNode rightNode = parseTerm();
      node = new OperatorNode(operatorToken.getValue(), node, rightNode);
    }

    return node;
  }

  private ASTNode parseTerm() throws RuleParsingException {
    ASTNode node = parseFactor();

    while (
      currentTokenIndex < tokens.size() &&
      tokens.get(currentTokenIndex).getType() == Token.TokenType.OPERATOR &&
      tokens.get(currentTokenIndex).getValue().equals("AND")
    ) {
      Token operatorToken = tokens.get(currentTokenIndex++);
      ASTNode rightNode = parseFactor();
      node = new OperatorNode(operatorToken.getValue(), node, rightNode);
    }

    return node;
  }

  private ASTNode parseFactor() throws RuleParsingException {
    Token token = tokens.get(currentTokenIndex);

    if (
      token.getType() == Token.TokenType.PARENTHESIS &&
      token.getValue().equals("(")
    ) {
      currentTokenIndex++;
      ASTNode node = parseExpression();
      if (
        currentTokenIndex >= tokens.size() ||
        !tokens.get(currentTokenIndex).getValue().equals(")")
      ) {
        throw new RuleParsingException("Missing closing parenthesis");
      }
      currentTokenIndex++;
      return node;
    } else if (token.getType() == Token.TokenType.ATTRIBUTE) {
      return parseCondition();
    } else {
      throw new RuleParsingException("Unexpected token: " + token.getValue());
    }
  }

  private ASTNode parseCondition() throws RuleParsingException {
    Token attributeToken = tokens.get(currentTokenIndex++);
    if (currentTokenIndex >= tokens.size()) {
      throw new RuleParsingException("Expected comparator after attribute");
    }

    Token comparatorToken = tokens.get(currentTokenIndex++);
    if (comparatorToken.getType() != Token.TokenType.COMPARATOR) {
      throw new RuleParsingException(
        "Expected comparator, found: " + comparatorToken.getValue()
      );
    }

    if (currentTokenIndex >= tokens.size()) {
      throw new RuleParsingException("Expected value after comparator");
    }

    Token valueToken = tokens.get(currentTokenIndex++);
    if (
      valueToken.getType() != Token.TokenType.VALUE &&
      valueToken.getType() != Token.TokenType.ATTRIBUTE
    ) {
      throw new RuleParsingException(
        "Expected value, found: " + valueToken.getValue()
      );
    }

    String attribute = attributeToken.getValue();
    String comparator = comparatorToken.getValue();
    String value = valueToken.getValue();

    Object valueObject;
    try {
      valueObject = Double.parseDouble(value);
    } catch (NumberFormatException e) {
      valueObject = value;
    }

    return new OperandNode(attribute, comparator, valueObject);
  }
}
