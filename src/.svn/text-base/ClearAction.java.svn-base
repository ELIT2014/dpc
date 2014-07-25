import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public class ClearAction implements Action {

	public String doAction(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest parameters,
			String message) throws IOException {
		
		DataBase.executeUpdate("DELETE FROM data_values;");
		
		return "forward-main";
	}

}
