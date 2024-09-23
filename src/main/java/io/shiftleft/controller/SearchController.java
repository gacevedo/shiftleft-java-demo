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
public String doGetSearch(@RequestParam(required = false, defaultValue = "") String foo, HttpServletResponse response, HttpServletRequest request) {
    String message = null;
    try {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(foo);
        message = exp.getValue(String.class);
    } catch (Exception ex) {
        // Log the exception message without exposing sensitive information
        System.out.println(ex.getMessage());
    }
    // Use OWASP Encoder to escape HTML content
    return message != null ? Encoder.encodeForHTML(message) : "";
}

        // Handle specific exceptions like SpelEvaluationException or EvaluationException
        System.out.println(ex.getMessage());
    }
    // Always check if message is not null before toString()
    return message != null ? message.toString() : "";
}

      System.out.println(ex.getMessage());
    }
    return message.toString();
  }
}


