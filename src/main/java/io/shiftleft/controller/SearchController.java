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
@PreAuthorize("hasRole('ROLE_USER')")
public String doGetSearch(@RequestParam String foo, HttpServletResponse response, HttpServletRequest request) {
    java.lang.Object message = null;
    try {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Access Denied");
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("#foo");
        message = (Object) exp.getValue();
    } catch (Exception ex) {
        Logger logger = LoggerFactory.getLogger(SearchController.class);
        logger.error(ex.getMessage());
    }
    return message != null ? message.toString() : "";
}

    return message.toString();
  }
}

