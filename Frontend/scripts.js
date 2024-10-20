const apiBaseUrl = "http://localhost:8080/rules";

function createRule() {
  const ruleString = document.getElementById("ruleString").value;

  const input = { ruleString };

  document.getElementById("createRuleInput").innerText = JSON.stringify(
    input,
    null,
    2
  );

  fetch(`${apiBaseUrl}/create_rule`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(input),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((err) => {
          throw new Error(JSON.stringify(err));
        });
      }
      return response.json();
    })
    .then((data) => {
      document.getElementById("createRuleOutput").innerText = JSON.stringify(
        data,
        null,
        2
      );
    })
    .catch((error) => {
      console.error("Error creating rule:", error);
      document.getElementById(
        "createRuleOutput"
      ).innerText = `Error: ${error.message}`;
    });
}

function combineRules() {
  const ruleIdsStr = document.getElementById("combineRuleIds").value;
  const ruleIds = ruleIdsStr.split(",").map((id) => id.trim());

  const input = { ruleIds };

  document.getElementById("combineRuleInput").innerText = JSON.stringify(
    input,
    null,
    2
  );

  fetch(`${apiBaseUrl}/combine_rules`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(input),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((err) => {
          throw new Error(JSON.stringify(err));
        });
      }
      return response.json();
    })
    .then((data) => {
      document.getElementById("combineRuleOutput").innerText = JSON.stringify(
        data,
        null,
        2
      );
    })
    .catch((error) => {
      console.error("Error combining rules:", error);
      document.getElementById(
        "combineRuleOutput"
      ).innerText = `Error: ${error.message}`;
    });
}

function evaluateRule() {
  const ruleId = document.getElementById("ruleId").value;
  const ruleData = document.getElementById("ruleData").value;

  let input;
  try {
    input = JSON.parse(ruleData);
  } catch (err) {
    document.getElementById("evaluateRuleOutput").innerText =
      "Error: Invalid JSON format for rule data";
    return;
  }

  document.getElementById("evaluateRuleInput").innerText = JSON.stringify(
    input,
    null,
    2
  );

  fetch(`${apiBaseUrl}/${ruleId}/evaluate_rule`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(input),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((err) => {
          throw new Error(JSON.stringify(err));
        });
      }
      return response.json();
    })
    .then((data) => {
      document.getElementById("evaluateRuleOutput").innerText = JSON.stringify(
        data,
        null,
        2
      );
    })
    .catch((error) => {
      console.error("Error evaluating rule:", error);
      document.getElementById(
        "evaluateRuleOutput"
      ).innerText = `Error: ${error.message}`;
    });
}
