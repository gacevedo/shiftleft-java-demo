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
    java.lang.Object message = null;
    try {
        // Check if the current user is authenticated
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new SecurityException("Unauthorized access");
        }

        // Evaluate the SpEL expression
        SecurityExpressionHandler<FilterInvocation> expressionHandler = new DefaultWebSecurityExpressionHandler();
        boolean access = expressionHandler.getExpression("hasRole('ROLE_USER')").getValue(new ServletWebRequest(request), FilterInvocation.class);

        if (access) {
            // If the user has the 'ROLE_USER' role, evaluate the expression
            message = new SpelExpressionParser().parseExpression(foo).getValue();
        } else {
            // If the user does not have the 'ROLE_USER' role, throw an exception
            throw new SecurityException("Access denied");
        }
    } catch (Exception ex) {
        // Log the exception and return a safe message
        logger.error("An error occurred while processing the request", ex);
        message = "An error occurred while processing your request. Please try again later.";
    }
    return message != null ? message.toString() : "";
}

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


