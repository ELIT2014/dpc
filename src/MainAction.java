import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public class MainAction implements Action {

	public String doAction(HttpServletRequest request, HttpServletResponse response, MultipartRequest parameters, String message) throws IOException {
		PrintWriter out = response.getWriter();
		
//		ArrayList<String[]> data = DataBase.getData();
			
//		int artId = -1;
//		String[] colors = {"ffffff", "f5f5ff"};
		
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("<table width=\"100%\" style=\"margin-top: 20px; border-collapse: collapse; background: #E5E5E5\">");
//		sb.append("<tr><th style=\"padding: 5px; border: 1px solid #C0C0C0;\">id</th>" +
//				"<th style=\"padding: 5px; border: 1px solid #C0C0C0;\">article_id</th>" +
//				"<th style=\"padding: 5px; border: 1px solid #C0C0C0;\">data_type</th>" +
//				"<th style=\"padding: 5px; border: 1px solid #C0C0C0;\">value</th>" +
//				"<th style=\"padding: 5px; border: 1px solid #C0C0C0;\">parent_id</th>" +
//				"<th style=\"padding: 5px; border: 1px solid #C0C0C0;\">lang</th></tr>");
			
//		for (String[] row : data) {
//			artId = Integer.parseInt(row[1]);
//			
//			sb.append("<tr>\n");
//			for (int i = 0; i < row.length; i++)
//				sb.append("<td style=\"background: #" + colors[(artId + 1) % 2] + "; padding: 5px; border: 1px solid #C0C0C0;\">" + row[i] + "</td>\n");
//			sb.append("</tr>\n");
//		}

			
//		sb.append("</table>\n");
			
		out.println("<div>");
			
		out.println("<div>");
		out.println("\t<form style=\"display: inline;\" action=\"" + request.getContextPath() + "/\" enctype=\"multipart/form-data\" method=\"post\">");
		out.println("\tSubmit article ");
		out.println("\t<input type=\"file\" name=\"file\" size=\"40\" />");
		out.println("\t<input type=\"submit\" value=\"Submit\" style=\"margin-top: 10px; width: 100px;\"/>");
		out.println("\t<input type=\"hidden\" name=\"action\" value=\"sendfile\" />");
		out.println("\t</form>");
//		out.println("\t<form style=\"display: inline;\" action=\"" + request.getContextPath() + "/\" enctype=\"multipart/form-data\" method=\"post\">");
//		out.println("\t<input type=\"submit\" value=\"Clear\" style=\"margin-top: 10px; width: 100px;\"/>");
//		out.println("\t<input type=\"hidden\" name=\"action\" value=\"clear\" />");
//		out.println("\t</form>");
		out.println("</div>");

//		out.println("<div>");
//		out.println(sb);
//		out.println("</div>");

		out.println("</div>");
			
		return null;
	}

}
