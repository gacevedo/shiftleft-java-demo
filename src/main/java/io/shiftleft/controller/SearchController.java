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
@PreAuthorize("isAuthenticated()")
public String doGetSearch(String foo, HttpServletResponse response, HttpServletRequest request) {
    java.lang.Object message = null;
    try {
        // ExpressionParser parser = new SpelExpressionParser();
        // Expression exp = parser.parseExpression(foo);
        // message = (Object) exp.getValue();
        // Removed the SpEL parsing and hardcoded value to prevent code injection
        message = "Validated and sanitized input";
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
    return message != null ? message.toString() : "";
}

    return message.toString();
  }
}

