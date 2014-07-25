import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public class ErrorAction implements Action {

	public String doAction(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest parameters,
			String message) throws IOException {
		PrintWriter out = response.getWriter();

		int errorCode = Integer.parseInt(request.getParameter("errorCode") == null ? "-1" : request.getParameter("errorCode"));
		String[] errorMessages = {"Unknown error", "Authentification failed", "Error hashing password",
				"File size is too large", "Invalid request", "Such file type not supported", "Data base error", "Access denied", "Can't export data"};
		
		out.println("\t\t<div>");
		out.println("\t\t<table width=\"300px\" align=\"center\" cellspacing=\"10px\" style=\"margin-top: 150px; border: 1px solid #ff5e5e; background: #ffb7b7;\">");
		out.println("\t\t\t<tr><td  style=\"margin-bottom: 10px; padding-bottom: 10px; border-bottom: 1px solid #ff5e5e\"><strong>Error!</strong></td></tr>");

		if (errorCode > 0 && errorCode < errorMessages.length)
			out.println("\t\t\t<tr><td>" + errorMessages[errorCode] + "</td></tr>");
		else
			out.println("\t\t\t<tr><td>" + errorMessages[0] + "</td></tr>");
			
		out.println("\t\t\t<tr><td align=\"right\"><a href=\"" + request.getContextPath() + "/\">go to main page</a></td></tr>");
		out.println("\t\t</table>");
		out.println("\t\t</div>");
		
		return null;
	}

}
