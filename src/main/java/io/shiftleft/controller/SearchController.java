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
public String doGetSearch(@RequestParam(required = false) String foo, HttpServletResponse response, HttpServletRequest request) {
    Object message = null;
    try {
        if (foo != null && !foo.isEmpty()) {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(foo);
            message = exp.getValue();
        }
    } catch (Exception ex) {
        System.out.println(ex.getMessage());
    }
    return message != null ? message.toString() : "";
}

      System.out.println(ex.getMessage());
    }
    return message.toString();
  }
}

