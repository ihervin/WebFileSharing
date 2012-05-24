package org.codeautomate.webfs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codeautomate.webfs.util.SharingConfiguration;
import org.codeautomate.webfs.util.SharingConfiguration.Share;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class ListDir
 */
public class ListDir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListDir() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SharingConfiguration config = new SharingConfiguration();
		PrintWriter out = response.getWriter();
		JSONObject obj;
		Map map = new HashMap();

		if (request.getSession().getAttribute("logged_user") != null) {
			List list = config.getShareByUser(request.getSession()
					.getAttribute("logged_user").toString());
			JSONArray arr = new JSONArray();
			Share share;

			Iterator it = list.iterator();
			while (it.hasNext()) {
				share = (Share) it.next();
				arr.put(share.getName());
			}
			map.put("success", true);
			map.put("data", arr);

			obj = new JSONObject(map);
			out.println(obj.toString());
		} else {
			map.put("success", false);
			obj = new JSONObject(map);
			out.println(obj.toString());
		}
	}

}
