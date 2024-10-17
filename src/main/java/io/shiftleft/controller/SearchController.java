package io.shiftleft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Search login
 */
@Controller
public class SearchController {

@RequestMapping(value = "/search/user", method = RequestMethod.GET)
public String doGetSearch(@RequestParam String foo, HttpServletResponse response, HttpServletRequest request) {
    AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    Logger logger = Logger.getLogger(SearchController.class.getName());
    java.lang.Object message = null;
    try {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(foo);
        message = exp.getValue();
    } catch (Exception ex) {
        logger.severe(ex.getMessage());
    }
    if (message != null) {
        return message.toString();
    } else {
        return "No result found";
    }
}

    return message.toString();
  }
}

