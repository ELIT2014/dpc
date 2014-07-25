import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public class ForwardAction implements Action {

	public String doAction(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest parameters,
			String message) throws IOException {
		response.sendRedirect(request.getContextPath() + "/?action=" + message);
		
		return null;
	}

}
