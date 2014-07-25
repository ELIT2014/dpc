import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


@SuppressWarnings("serial")
public class Test extends HttpServlet {

	public void init(ServletConfig config) {
		String dbhost = config.getInitParameter("dbhost");
		String dbport = config.getInitParameter("dbport");
		String dbname = config.getInitParameter("dbname");
		String dbuser = config.getInitParameter("dbuser");
		String dbpass = config.getInitParameter("dbpass");
		
		DataBase.init("jdbc:mysql://" + dbhost + ":" + dbport + "/" + dbname, dbuser, dbpass);
//		DataBase.init("jdbc:mysql://127.0.0.1:3306/dpc", "dpc", "EuNLSTbByRGrdjYf");

		SOfficeConnection.init("127.0.0.1", 7525);
		
		dir = config.getInitParameter("dir");
		if (dir == null)
			dir = ".";
		
		actions.put("main", new MainAction());
		actions.put("error", new ErrorAction());
		actions.put("forward", new ForwardAction());
		actions.put("sendfile", new SendFileAction());
		actions.put("clear", new ClearAction());
//		actions.put("export", new ExportAction());
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doRequest(request, response, false);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doRequest(request, response, true);
	}
	
	public void destroy() {
		DataBase.closeConnection();
		
		SOfficeConnection.close();
	}

	private synchronized void doRequest(HttpServletRequest request, HttpServletResponse response, boolean post) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		request.setCharacterEncoding("utf-8");
		
		String action = null, message = "", result = "";
		StringTokenizer tokens = null;
		MultipartRequest parameters = null;
		
		action = request.getParameter("action");
		
		if (post && action == null) {
			try {
				parameters = new MultipartRequest(request, dir, 1024*1024*30, "utf-8");
				action = parameters.getParameter("action");
			} catch (Exception e) {
				e.printStackTrace();
				
				action = "forward";
				message = "error&errorCode=3";
			}
		}

		header(response.getWriter(), (String) request.getSession().getAttribute("name"), request.getContextPath());

		if (action == null)
			action = "main";
			
		while (action != null) {
			if (actions.get(action) != null) {
				result = actions.get(action).doAction(request, response, parameters, message);
				if (result != null) {
					tokens = new StringTokenizer(result, "-");
				
					action = tokens.nextToken();
					message = tokens.hasMoreTokens() ? tokens.nextToken() : "";
				} else {
					action = null;
					message = "";
				}
			} else {
				System.out.println(action + " - " + message);
				
				action = "forward";
				message = "error&errorCode=4";
			}
		}
		
		footer(response.getWriter());
	}

	private void header(PrintWriter out, String username, String url) {
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" >");
		out.println("\t<head>");
		out.println("\t\t<title>APQ - Mod 1</title>");
		out.println("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		out.println("\t</head>");
		out.println("\t<body style=\"font-family: arial; font-size: 14px;\">");
	}
	
	private void footer(PrintWriter out) {
		out.println("\t</body>");
		out.println("</html>");
	}


	private Hashtable<String, Action> actions = new Hashtable<String, Action>();
	private String dir = ".";
}
