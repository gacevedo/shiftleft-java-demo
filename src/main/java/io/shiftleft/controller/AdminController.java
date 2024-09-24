package io.shiftleft.controller;

import io.shiftleft.model.AuthToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Admin checks login
 */
@Controller
public class AdminController {
  private String fail = "redirect:/";

  // helper
  private boolean isAdmin(String auth)
  {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(Base64.getDecoder().decode(auth));
      ObjectInputStream objectInputStream = new ObjectInputStream(bis);
      Object authToken = objectInputStream.readObject();
      return ((AuthToken) authToken).isAdmin();
    } catch (Exception ex) {
      System.out.println(" cookie cannot be deserialized: "+ex.getMessage());
      return false;
    }
  }

  //
  @RequestMapping(value = "/admin/printSecrets", method = RequestMethod.POST)
  public String doPostPrintSecrets(HttpServletResponse response, HttpServletRequest request) {
    return fail;
  }


  @RequestMapping(value = "/admin/printSecrets", method = RequestMethod.GET)
  public String doGetPrintSecrets(@CookieValue(value = "auth", defaultValue = "notset") String auth, HttpServletResponse response, HttpServletRequest request) throws Exception {

    if (request.getSession().getAttribute("auth") == null) {
      return fail;
    }

    String authToken = request.getSession().getAttribute("auth").toString();
    if(!isAdmin(authToken)) {
      return fail;
    }

    ClassPathResource cpr = new ClassPathResource("static/calculations.csv");
    try {
      byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
      response.getOutputStream().println(new String(bdata, StandardCharsets.UTF_8));
      return null;
    } catch (IOException ex) {
      ex.printStackTrace();
      // redirect to /
      return fail;
    }
  }

  /**
   * Handle login attempt
   * @param auth cookie value base64 encoded
   * @param password hardcoded value
   * @param response -
   * @param request -
   * @return redirect to company numbers
   * @throws Exception
   */
@RequestMapping(value = "/admin/login", method = RequestMethod.POST)
public String doPostLogin(@CookieValue(value = "auth", defaultValue = "notset") String auth, @RequestBody String password, HttpServletResponse response, HttpServletRequest request) throws IOException {
    String succ = "redirect:/admin/printSecrets";
    String fail = "redirect:/admin/fail";
    ObjectMapper mapper = new ObjectMapper();

    try {
        if (!auth.equals("notset")) {
            AuthToken authToken = mapper.readValue(Base64.getDecoder().decode(auth), AuthToken.class);
            if (authToken.isAdmin()) {
                request.getSession().setAttribute("auth", auth);
                return succ;
            }
        }

        String[] pass = password.split("=");
        if (pass.length != 2) {
            return fail;
        }

        if (pass[1] != null && pass[1].length() > 0 && pass[1].equals("shiftleftsecret")) {
            AuthToken authToken = new AuthToken(AuthToken.ADMIN);
            String cookieValue = Base64.getEncoder().encodeToString(mapper.writeValueAsBytes(authToken));
            response.addCookie(new Cookie("auth", cookieValue));
            request.getSession().setAttribute("auth", cookieValue);
            return succ;
        }
        return fail;
    } catch (Exception ex) {
        ex.printStackTrace();
        return fail;
    }
}

  }

  /**
   * Same as POST but just a redirect
   * @param response
   * @param request
   * @return redirect
   */
  @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
  public String doGetLogin(HttpServletResponse response, HttpServletRequest request) {
    return "redirect:/";
  }
}

