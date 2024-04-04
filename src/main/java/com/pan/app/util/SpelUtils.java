package com.pan.app.util;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @description:
 * @author: Mr.Pan
 * @create: 2024-03-31 16:30
 **/
public class SpelUtils {
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    public static String parse(String expressionStr, String[] paramNames, Object[] paramValues) {
        EvaluationContext evaluationContext = buildContext(paramNames, paramValues);

        Expression expression = PARSER.parseExpression(expressionStr);
        return (String) expression.getValue(evaluationContext);
    }

    private static EvaluationContext buildContext(String[] paramNames, Object[] paramValues) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();

        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];

            evaluationContext.setVariable(paramName, paramValue);
        }

        return evaluationContext;
    }
}
