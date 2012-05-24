package org.codeautomate.webfs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codeautomate.webfs.util.ADAuthenticator;
import org.json.JSONObject;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		Map map = new HashMap();

		ADAuthenticator ad = new ADAuthenticator();

		Map authResp = ad.authenticate(request.getParameter("userID"),
				request.getParameter("password"));

		if (authResp != null) {
			map.put("success", true);
			map.put("msg", "Login success");
			request.getSession().setAttribute("logged_user",
					request.getParameter("userID"));
		} else {
			map.put("success", false);
			map.put("msg", "Login Failed");
		}

		JSONObject obj = new JSONObject(map);
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
	}

}
