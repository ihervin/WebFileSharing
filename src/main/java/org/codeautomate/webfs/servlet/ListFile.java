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

import org.codeautomate.webfs.util.FileListUtil;
import org.codeautomate.webfs.util.SharingConfiguration;
import org.codeautomate.webfs.util.SharingConfiguration.Share;
import org.json.JSONObject;

/**
 * Servlet implementation class ListFile
 */
public class ListFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListFile() {
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
		if (request.getSession().getAttribute("logged_user") != null) {
			SharingConfiguration config = new SharingConfiguration();
			List list = config.getShareByUser(request.getSession()
					.getAttribute("logged_user").toString());
			String dirPath = request.getParameter("dir_name");
			FileListUtil fUtil;

			Share share = null;
			boolean found = false;

			Iterator it = list.iterator();
			while (it.hasNext() && !found) {
				share = (Share) it.next();
				found = dirPath.equals(share.getName());
			}

			String retVal = "";
			JSONObject obj = new JSONObject();
			Map map = new HashMap();
			if (found) {
				fUtil = new FileListUtil();
				map.put("success", true);
				// map.put("data",
				// getDirectoryList(share.getDirectory()[0].getName()));
				map.put("data",
						fUtil.getDirectoryList(
								share.getDirectory()[0].getName(),
								share.getDirectory()[0].getFilter()));
			} else {
				map.put("success", false);
			}

			PrintWriter out = response.getWriter();
			out.println((new JSONObject(map)).toString());
		} else {
			Map map = new HashMap();
			map.put("success", false);
			map.put("istimeout", true);
			JSONObject obj = new JSONObject(map);

			PrintWriter out = response.getWriter();
			out.println(obj.toString());
		}
	}

}
