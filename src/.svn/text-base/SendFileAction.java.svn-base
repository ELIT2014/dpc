import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


public class SendFileAction implements Action {

	private void copyMetadata(String columnTo, String columnFrom, Hashtable<String, Object> table, ArrayList<String> messages) {
		if (table.get(columnTo) != null && !table.get(columnTo).equals("")) {
			messages.add("Column [" + columnTo + "] not empty!");
		}
		
		table.put(columnTo, table.get(columnFrom));
	}
	
	public String doAction(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest parameters,
			String message) throws IOException {
		File file = parameters.getFile("file");

		final String referenceTemplate = 
				"<p style=\"margin-left: 18.45pt; text-indent: -18.45pt;\" class=\"JnepReferences\"><span lang=\"EN-US\"><span>%s.<span style=\"font: 7pt \"> </span></span></span><span lang=\"EN-US\">%s</span></p>";
		int referenceIndex = 0;
		final String affiliationTemplate = "<p><sup>%s</sup> %s</p>";
		final String affiliationTemplate2 = "<p>%s</p>";
		
		ArrayList<String> messages = new ArrayList<String>();
		
//		int articleId = DataBase.genNextId("journal_articles", "id");
		String art_volume = "", art_number = "", art_id = "", art_year = "", art_pages = "";
		
		Hashtable<String, String> keywords = new Hashtable<String, String>();
		keywords.put("en", "Keywords:");
		keywords.put("ru", "Ключевые слова:");
		keywords.put("uk", "Ключові слова:");

		Hashtable<String, String> langPrefix = new Hashtable<String, String>();
		langPrefix.put("uk", "");
		langPrefix.put("ru", "_ru");
		langPrefix.put("en", "_en");

		StringBuilder sbRef = new StringBuilder();
		StringBuilder sbEmail = new StringBuilder();
		
		Hashtable<String, String> citation = new Hashtable<String, String>();
		citation.put("etalen", "et al., ");
		citation.put("etalru", "и др., ");
		citation.put("etaluk", "та ін., ");
		citation.put("en", "J. Nano-Electron. Phys. %s No %s, %s (%s)");
		citation.put("ru", "Ж. нано-электрон. физ. %s № %s, %s (%s)");
		citation.put("uk", "Ж. нано-електрон. фіз. %s № %s, %s (%s)");

		String dates_uk = "Одержано %s, у відредагованій формі - %s, опубліковано онлайн - %s";
		String dates_ru = "Получено %s, в отредактированной форме – %s, опубликовано онлайн – %s";
		String dates_en = "Received %s, in final form %s, published online %s";
		
		ArrayList<String[]> text = SOfficeConnection.getFormatedText(file);
		
		Hashtable<String, Object> query = new Hashtable<String, Object>();

		TreeSet<String> styles = new TreeSet<String>();
		styles.add("Jnep_Title");
		styles.add("Jnep_References");
		styles.add("Jnep_Abstract");
		styles.add("Jnep_Header");
		styles.add("Jnep_Autors");
		styles.add("Jnep_Affiliations");
		styles.add("Jnep_Email");
		styles.add("Jnep_DOI");
		styles.add("Jnep_PACS");
		styles.add("Jnep_Footer");
		
		query.put("id", 0);
		query.put("title", "");
		query.put("author", "");
		query.put("link", "");
		query.put("links", "");
		query.put("summary", "");
		query.put("pages_start", 0);
		query.put("number_id", 0);
		query.put("pages_end", 0);
		query.put("key_words", "");
		query.put("pacs_numbers", "");
		query.put("title_en", "");
		query.put("author_en", "");
		query.put("summary_en", "");
		query.put("key_words_en", "");
		query.put("file_link_en", "");
		query.put("title_ru", "");
		query.put("author_ru", "");
		query.put("summary_ru", "");
		query.put("key_words_ru", "");
		query.put("dates", "");
		query.put("doi", "");
		query.put("email", "");
		query.put("en_aviable", 0);
		query.put("ru_aviable", 0);
		query.put("affiliations", "");
		query.put("affiliations_en", "");
		query.put("affiliations_ru", "");
		query.put("links_ua", "");
		query.put("links_ru", "");
		query.put("links_en", "");
		query.put("dates_ru", "");
		query.put("dates_en", "");
		query.put("links__en", "");
		query.put("links__ru", "");
		query.put("pages_start_en", 0);
		query.put("pages_end_en", 0);
		query.put("addBy", 0);
		query.put("first_reviewer", 0);
		query.put("second_reviewer", 0);
		query.put("tech_expert", 0);
		query.put("count_views", 0);
		query.put("count_downloads", 0);
		
		
		query.put("link", "");
		query.put("links__en", "");
		query.put("links__ru", "");
		query.put("file_link_en", "");
		query.put("en_aviable", 0);
		query.put("ru_aviable", 0);
		
		query.put("pages_start_en", 0);
		query.put("pages_end_en", 0);
		query.put("addBy", 0);
		
		query.put("first_reviewer", 0);
		query.put("second_reviewer", 0);
		query.put("tech_expert", 0);
		query.put("count_views", 0);
		query.put("count_downloads", 0);
		
		if (text.size() == 0)
			return "forward-error&errorCode=5";
		
		//query.put("id", articleId);

		for (int i = 0; i < text.size(); i++) {
			String[] row = text.get(i);

			try {
				if (row[0].equals("Jnep_Title")) {
					query.put("title" + langPrefix.get(row[1]), row[2]);
				}
				
				if (row[0].equals("Jnep_References")) {
					if (row[3] != null) {
						row[2] = row[2].replace(row[4], "<a href=\"" + row[3] + "\">" + row[4] + "</a>");
					}

					sbRef.append(String.format(referenceTemplate, ++referenceIndex, row[2]));
					sbRef.append("\n");
				}
	
				if (row[0].equals("Jnep_Abstract")) {
					if (row[2].startsWith(keywords.get(row[1]))) {
						StringTokenizer tokens = new StringTokenizer(row[2], ":,.");
						StringBuilder sb = new StringBuilder();
						
						tokens.nextToken();
						
						while(tokens.hasMoreTokens()) {
							sb.append(tokens.nextToken().trim() + ", ");
						}
						
						sb.setLength(sb.length() - 2);
						
						query.put("key_words" + langPrefix.get(row[1]), sb.toString());
					} else if (row[2].matches("^\\(.*\\)$")) {
						Pattern p = Pattern.compile("(([0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4})|([0-9]{1,2} [^0-9]{1,}[0-9]{0,4}))");
						Matcher m = p.matcher(row[2]);
	
						String dateReceive = "";
						String dateEdit = "";
						String datePrint = "";
						
						if (m.find()) dateReceive = m.group(1);
						if (m.find()) dateEdit = m.group(1);
						if (m.find()) datePrint = m.group(1);
						
						query.put("dates", String.format(dates_uk, dateReceive, dateEdit, datePrint));
						query.put("dates_ru", String.format(dates_ru, dateReceive, dateEdit, datePrint));
						query.put("dates_en", String.format(dates_en, dateReceive, dateEdit, datePrint));
						
						query.put("dates", row[2].substring(1, row[2].length() - 1));
						query.put("dates_ru", row[2].substring(1, row[2].length() - 1));
						query.put("dates_en", row[2].substring(1, row[2].length() - 1));
					} else {
						query.put("summary" + langPrefix.get(row[1]), row[2]);
					}
				}
	
	
				if (row[0].equals("Jnep_Header")) {
					Pattern p = Pattern.compile("Vol\\. ([0-9]{1,}) No ([0-9]{1,})\\, ([0-9]{1,})\\(([0-9]{1,})pp\\) \\(([0-9]{4})\\)");
					Matcher m = p.matcher(row[2]);
					// (vol)-(number)-(id)-(pages)-(year)
					
					if (m.find()) {
						art_volume = m.group(1);
						art_number = m.group(2);
						art_id = m.group(3);
						art_pages = m.group(4);
						art_year = m.group(5);
	
						query.put("number_id", DataBase.getNumberID(art_volume, art_number));
						messages.add("number id - " + DataBase.getNumberID(art_volume, art_number));
						
	//					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
	//							"(" + DataBase.genNextId("data_values", "id") + ", " + 
	//							articleId + ", " + types.get("volume") + ", '" + m.group(1) + "', -1, '" + row[1] + "'); ");
	//					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
	//							"(" + DataBase.genNextId("data_values", "id") + ", " + 
	//							articleId + ", " + types.get("number") + ", '" + m.group(2) + "', -1, '" + row[1] + "'); ");
	//					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
	//							"(" + DataBase.genNextId("data_values", "id") + ", " + 
	//							articleId + ", " + types.get("year") + ", '" + m.group(5) + "', -1, '" + row[1] + "'); ");
					}
				}
	
				
				if (row[0].equals("Jnep_Autors")) {
					Pattern p = Pattern.compile("([^0-9,\\[\\]]+)([0-9,]*)(\\[([0-9]+)\\])?");
					Matcher m = p.matcher(row[2]);
					StringTokenizer tokens;
	//				int id = 0;
					ArrayList<String> authors = new ArrayList<String>();
					StringBuilder sb = new StringBuilder();
					StringBuilder sb_works = new StringBuilder();
					
					while (m.find()) {
	//					++id;
						
						sb.append(m.group(1).trim());
						
						authors.add(m.group(1).trim());
						
						tokens = new StringTokenizer(m.group(2), ",");
						
						while (tokens.hasMoreTokens()) {
							sb_works.append(tokens.nextToken() + ";");
						}
						
						if (sb_works.length() > 0) {
							sb_works.setLength(sb_works.length() - 1);
							
							sb.append("" + sb_works + "");
							sb_works.setLength(0);
						}
						
						sb.append(", ");
						
//						if (m.group(4) != null) ;
	//						DataBase.executeUpdate("INSERT INTO data_values VALUES " +
	//							"(" + DataBase.genNextId("data_values", "id") + ", " + 
	//							articleId + ", " + types.get("email_relation") + ", '" + id + "', " + m.group(4) + ", '" + row[1] + "'); ");
					}
					
					sb.setLength(sb.length() - 2);
					
					query.put("author" + langPrefix.get(row[1]), sb.toString());
					
					StringBuilder link = new StringBuilder();
					
					if (authors.size() == 4) {
						for (int j = 0; j < 4; j++)
							link.append(authors.get(j) + ", ");
					} else if (authors.size() > 4) {
						for (int j = 0; j < 3; j++)
							link.append(authors.get(j) + ", ");
						link.append(citation.get("etal" + row[1]));
					} else {
						for (int j = 0; j < authors.size(); j++)
							link.append(authors.get(j) + ", ");
					}
	
					String currLangPrefix = langPrefix.get(row[1]);
					
					if (currLangPrefix.equals(""))
						currLangPrefix = "_ua";
					
					query.put("links" + currLangPrefix, link + String.format(citation.get(row[1]), art_volume, art_number, art_id, art_year));
				}
	
				
				if (row[0].equals("Jnep_Affiliations")) {
					StringTokenizer tokens = new StringTokenizer(row[2], "\t");
					String id = tokens.nextToken();
					
					String workList = (String) query.get("affiliations" + langPrefix.get(row[1]));
					
					if (tokens.hasMoreTokens()) {
						workList += "\n" + String.format(affiliationTemplate, id, tokens.nextToken());							
					} else {
						workList += "\n" + String.format(affiliationTemplate2, id);
					}
					
					query.put("affiliations" + langPrefix.get(row[1]), workList);
				}
	
				
				if (row[0].equals("Jnep_Email")) {
					sbEmail.append(row[2] + ", ");
				}
	
	
				if (row[0].equals("Jnep_DOI")) {
					row[2] = row[2].substring(4).trim();
					
					query.put("doi", row[2]);
				}
	
	
				if (row[0].equals("Jnep_PACS")) {
					row[2] = row[2].substring(15).trim();
	
					query.put("pacs_numbers", row[2]);
				}
	
	
				if (row[0].equals("Jnep_Footer")) {
					int start_page = Integer.parseInt(row[2]);
					if (start_page == 0) start_page = 1;
						
					query.put("pages_start", art_id + "-" + start_page);
					query.put("pages_end", art_id + "-" + (start_page - 1 + Integer.parseInt(art_pages)));
				}
			} catch (Exception e) {
				messages.add("Style " + row[0] + " exception: " + e);
			}

			styles.remove(row[0]);
			
//			System.out.println(row[0] + " - " + row[1] + " - " + row[2]);
		}

		for (String s : styles) {
			messages.add("Style not found - " + s);
		}
		
		query.put("links", sbRef.toString());
		query.put("links__en", sbRef.toString());
		query.put("links__ru", sbRef.toString());
		
		if (sbEmail.length() > 0)
			sbEmail.setLength(sbEmail.length() - 2);
		
		query.put("email", sbEmail.toString());
		
		// English article
		if (query.get("title").equals("") || query.get("title_ru").equals("")) {
			copyMetadata("title", "title_en", query, messages);
			copyMetadata("title_ru", "title_en", query, messages);
			
			copyMetadata("author", "author_en", query, messages);
			copyMetadata("author_ru", "author_en", query, messages);
			
			copyMetadata("links_ua", "links_en", query, messages);
			copyMetadata("links_ru", "links_en", query, messages);
			
			copyMetadata("summary", "summary_en", query, messages);
			copyMetadata("summary_ru", "summary_en", query, messages);
			
			copyMetadata("affiliations", "affiliations_en", query, messages);
			copyMetadata("affiliations_ru", "affiliations_en", query, messages);
			
			copyMetadata("key_words", "key_words_en", query, messages);
			copyMetadata("key_words_ru", "key_words_en", query, messages);
		}

		query.put("pages_start_en", query.get("pages_start"));
		query.put("pages_end_en", query.get("pages_end"));
		
		StringBuilder sb_columns = new StringBuilder();
		StringBuilder sb_values = new StringBuilder();
		
		for (String column : query.keySet()) {
			Object value = query.get(column);
			
			sb_columns.append(column + ", ");
			
			if (value instanceof String) {
				sb_values.append("'" + value + "', ");
			} else if (value instanceof Integer) {
				sb_values.append(value + ", ");
			}
		}
		
		sb_columns.setLength(sb_columns.length() - 2);
		sb_values.setLength(sb_values.length() - 2);

		String queryStr = "INSERT INTO journal_articles (" + sb_columns + ") VALUES (" + sb_values + "); ";
		
		
		if ((!query.get("number_id").equals(0)) && DataBase.executeUpdate(queryStr) > 0) {
			messages.add("Database ok");
		} else {
			messages.add("Database error");
		}
		
//		System.out.println(queryStr);

		PrintWriter out = response.getWriter();
			
		out.println("\t\t<div>");
		out.println("\t\t<table width=\"500px\" align=\"center\" cellspacing=\"10px\" style=\"margin-top: 150px; border: 1px solid gray; background: silver;\">");
		out.println("\t\t\t<tr><td  style=\"margin-bottom: 10px; padding-bottom: 10px; border-bottom: 1px solid gray\"><strong>Results</strong></td></tr>");

		for (String s : messages) {
			out.println("\t\t\t<tr><td>" + s + "</td></tr>");
		}
				
		out.println("\t\t\t<tr><td align=\"right\"><a href=\"" + request.getContextPath() + "/\">go to main page</a></td></tr>");
		out.println("\t\t</table>");
		out.println("\t\t</div>");

		return null;
		
//		if (parameters.getParameter("redirect") != null) {
//			response.sendRedirect(parameters.getParameter("redirect"));
//			return null;
//		} else {
//			return "forward-main";
//		}
	}

/*

	public String doAction(HttpServletRequest request,
			HttpServletResponse response, MultipartRequest parameters,
			String message) throws IOException {
		File file = parameters.getFile("file");

		int articleId = DataBase.genNextId("data_values", "article_id");
		String art_volume = "", art_number = "", art_id = "", art_year = "", art_pages = "";
		boolean art_lang = false;
		
		Hashtable<String, String> keywords = new Hashtable<String, String>();
		keywords.put("en", "Keywords:");
		keywords.put("ru", "Ключевые слова:");
		keywords.put("uk", "Ключові слова:");

		Hashtable<String, String> citation = new Hashtable<String, String>();
		citation.put("etalen", "et al. ");
		citation.put("etalru", "и др. ");
		citation.put("etaluk", "та ін. ");
		citation.put("en", "J. Nano-Electron. Phys. %s No %s, %s (%s)");
		citation.put("ru", "Ж. нано-электрон. физ. %s № %s, %s (%s)");
		citation.put("uk", "Ж. нано-електрон. фіз. %s № %s, %s (%s)");
		
		Hashtable<String, ArrayList<String>> values = new Hashtable<String, ArrayList<String>>();
		Hashtable<String, String> types = DataBase.getTypes();
		
		for (String type : types.values())
			values.put(type, new ArrayList<String>());
		
		ArrayList<String[]> text = SOfficeConnection.getFormatedText(file);

		if (text.size() == 0)
			return "forward-error&errorCode=5";
		int parent;
		
		DataBase.executeUpdate("INSERT INTO data_values VALUES " +
				"(" + DataBase.genNextId("data_values", "id") + ", " + 
				articleId + ", " + types.get("time") + ", '" + new Date() + "', -1, 'en'); ");
		
		for (int i = 0; i < text.size(); i++) {
			String[] row = text.get(i);

			if (row[0].equals("Jnep_Title")) {
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("title") + ", '" + row[2] + "', -1, '" + row[1] + "'); ");
				
				if (!art_lang) {
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("language") + ", '" + row[1] + "', -1, '" + row[1] + "'); ");
				
					art_lang = true;
				}
			}
			
			if (row[0].equals("Jnep_References")) {
				int id = DataBase.genNextId("data_values", "id");
				
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + id + ", " + 
						articleId + ", " + types.get("reference") + ", '" + row[2] + "', -1, '" + row[1] + "'); ");
				
				if (row[3] != null) {
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("reference_link") + ", '" + row[3] + "', " + id + ", '" + row[1] + "'); ");

					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("reference_link_name") + ", '" + row[4] + "', " + id + ", '" + row[1] + "'); ");
}
			}

			if (row[0].equals("Jnep_Abstract")) {
				if (row[2].startsWith(keywords.get(row[1]))) {
					StringTokenizer tokens = new StringTokenizer(row[2], ":,.");
					
					tokens.nextToken();
					parent = -1;
					
					while(tokens.hasMoreTokens()) {
						if (parent > 0) {
							DataBase.executeUpdate("INSERT INTO data_values VALUES " +
									"(" + DataBase.genNextId("data_values", "id") + ", " + 
									articleId + ", " + types.get("keyword") + ", '" + tokens.nextToken().trim() + "', " + parent + ", '" + row[1] + "'); ");
						} else {
							parent = DataBase.genNextId("data_values", "id");
							
							DataBase.executeUpdate("INSERT INTO data_values VALUES " +
									"(" + parent + ", " + 
									articleId + ", " + types.get("keyword") + ", '" + tokens.nextToken().trim() + "', -1, '" + row[1] + "'); ");
						}
					}
				} else if (row[2].matches("^\\(.*\\)$")) {
					Pattern p = Pattern.compile("([0-9]{1,2}\\.[0-9]{1,2}\\.[0-9]{4})");
					Matcher m = p.matcher(row[2]);
					
					if (m.find()) DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("date_receive") + ", '" + m.group(1) + "', -1, '" + row[1] + "'); ");

					if (m.find()) DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("date_edit") + ", '" + m.group(1) + "', -1, '" + row[1] + "'); ");

					if (m.find()) DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("date_print") + ", '" + m.group(1) + "', -1, '" + row[1] + "'); ");
				} else {
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("abstract") + ", '" + row[2] + "', -1, '" + row[1] + "'); ");
				}
			}
			
			if (row[0].equals("Jnep_Header")) {
				Pattern p = Pattern.compile("Vol\\. ([0-9]{1,}) No ([0-9]{1,})\\, ([0-9]{1,})\\(([0-9]{1,})pp\\) \\(([0-9]{4})\\)");
				Matcher m = p.matcher(row[2]);
				// (vol)-(number)-(id)-(pages)-(year)
				
				if (m.find()) {
					art_volume = m.group(1);
					art_number = m.group(2);
					art_id = m.group(3);
					art_pages = m.group(4);
					art_year = m.group(5);
					
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("volume") + ", '" + m.group(1) + "', -1, '" + row[1] + "'); ");
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("number") + ", '" + m.group(2) + "', -1, '" + row[1] + "'); ");
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("year") + ", '" + m.group(5) + "', -1, '" + row[1] + "'); ");
				}
			}
			
			if (row[0].equals("Jnep_Autors")) {
				Pattern p = Pattern.compile("([^0-9,]+)([0-9,]*)(\\[([0-9]+)\\])?");
				Matcher m = p.matcher(row[2]);
				StringTokenizer tokens;
				int id = 0;
				ArrayList<String> authors = new ArrayList<String>();

				while (m.find()) {
					++id;
					
					DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("author") + ", '" + m.group(1).trim() + "', " + id + ", '" + row[1] + "'); ");

					authors.add(m.group(1).trim());
					
					tokens = new StringTokenizer(m.group(2), ",");
					
					while (tokens.hasMoreTokens()) {
						DataBase.executeUpdate("INSERT INTO data_values VALUES " +
								"(" + DataBase.genNextId("data_values", "id") + ", " + 
								articleId + ", " + types.get("work_relation") + ", '" + id + "', " + tokens.nextToken() + ", '" + row[1] + "'); ");
					}
					
					if (m.group(4) != null)
						DataBase.executeUpdate("INSERT INTO data_values VALUES " +
							"(" + DataBase.genNextId("data_values", "id") + ", " + 
							articleId + ", " + types.get("email_relation") + ", '" + id + "', " + m.group(4) + ", '" + row[1] + "'); ");
				}
				
				StringBuilder link = new StringBuilder();
				
				if (authors.size() == 4) {
					for (int j = 0; j < 4; j++)
						link.append(authors.get(j) + ", ");
				} else if (authors.size() > 4) {
					for (int j = 0; j < 3; j++)
						link.append(authors.get(j) + ", ");
					link.append(citation.get("etal" + row[1]));
				} else {
					for (int j = 0; j < authors.size(); j++)
						link.append(authors.get(j) + ", ");
				}

				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("citation") + ", '" + link + String.format(citation.get(row[1]), art_volume, art_number, art_id, art_year) + "', -1, '" + row[1] + "'); ");
			}
			
			if (row[0].equals("Jnep_Affiliations")) {
				StringTokenizer tokens = new StringTokenizer(row[2], "\t");
				String id = tokens.nextToken();
				
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("work") + ", '" + tokens.nextToken() + "', " + id + ", '" + row[1] + "'); ");
			}
			
			if (row[0].equals("Jnep_Email")) {
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("email") + ", '" + row[2] + "', " + row[3] + ", '" + row[1] + "'); ");
			}

			if (row[0].equals("Jnep_DOI")) {
				row[2] = row[2].substring(4).trim();
				
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("doi") + ", '" + row[2] + "', -1, '" + row[1] + "'); ");
			}

			if (row[0].equals("Jnep_PACS")) {
				row[2] = row[2].substring(15).trim();

				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("pacs") + ", '" + row[2] + "', -1, '" + row[1] + "'); ");
			}
			
			if (row[0].equals("Jnep_Footer")) {
				int start_page = Integer.parseInt(row[2]);
				if (start_page == 0) start_page = 1;
					
				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("start_page") + ", '" + start_page + "', -1, '" + row[1] + "'); ");

				DataBase.executeUpdate("INSERT INTO data_values VALUES " +
						"(" + DataBase.genNextId("data_values", "id") + ", " + 
						articleId + ", " + types.get("end_page") + ", '" + (start_page - 1 + Integer.parseInt(art_pages)) + "', -1, '" + row[1] + "'); ");
			}
			
//			System.out.println(row[0] + " - " + row[1] + " - " + row[2]);
		}
		
		return "forward-main";
	}

 	
*/

}
