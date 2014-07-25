import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public interface Action {

	public String doAction(HttpServletRequest request, HttpServletResponse response, MultipartRequest parameters, String message)  throws IOException;
	
}
