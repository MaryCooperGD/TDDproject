package com.springmiddleware.errors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Custom controller for HTTP errors.
 * @author MLondei
 *
 */
@Controller
public class CustomErrorController implements ErrorController {

		  /**
		   * Handles the different types of http error.
		 * @param request the request done by the user
		 * @return the string description of the error
		 */
		@RequestMapping("/error")
		  @ResponseBody
		  public String handleError(HttpServletRequest request) {
		      Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		      Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		      if(statusCode.equals(404)) {
		    	  return String.format("<html><body><h2>Pagina non trovata</h2><div>Status code: <b>%s</b></div>"
		                  + "<div>Exception Message: <b>La pagina cercata non esiste.</b></div><body></html>",
		          statusCode, exception==null? "N/A": exception.getMessage());
		      } else if (statusCode.equals(505)) {
		    	  return String.format("<html><body><h2>Giocatore non trovato</h2><div>Status code: <b>%s</b></div>"
		                  + "<div>Exception Message: <b>L'entità non è presente nel database.</b></div><body></html>",
		          statusCode, exception==null? "N/A": exception.getMessage());
		      }  else if (statusCode.equals(405)) {
		    	  return String.format("<html><body><h2>Operazione non permessa</h2><div>Status code: <b>%s</b></div>"
		                  + "<div>Exception Message: <b>L'operazione non è permessa</b></div><body></html>",
		          statusCode, exception==null? "N/A": exception.getMessage());
		      }else {
		    	  return String.format("<html><body><h2>Errore</h2><div>Status code: <b>%s</b></div>"
		                  + "<div>Exception Message: <b>%s</b></div><body></html>",
		          statusCode, exception==null? "N/A": exception.getMessage());
		      }
		      
		  }

		  /* (non-Javadoc)
		 * @see org.springframework.boot.web.servlet.error.ErrorController#getErrorPath()
		 */
		@Override
		  public String getErrorPath() {
		      return "/error";
		  }

}
