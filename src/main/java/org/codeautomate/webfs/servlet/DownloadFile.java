package org.codeautomate.webfs.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codeautomate.webfs.util.FileInfo;
import org.codeautomate.webfs.util.FileListUtil;
import org.codeautomate.webfs.util.SharingConfiguration;
import org.codeautomate.webfs.util.SharingConfiguration.Share;

/**
 * Servlet implementation class DownloadFile
 */
public class DownloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public DownloadFile() {

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
			String fileName = request.getParameter("file_name");

			SharingConfiguration config = new SharingConfiguration();
			List list = config.getShareByUser(request.getSession()
					.getAttribute("logged_user").toString());
			String dirPath = request.getParameter("dir_name");

			Share share = null;
			boolean found = false;

			Iterator it = list.iterator();
			while (it.hasNext() && !found) {
				share = (Share) it.next();
				found = dirPath.equals(share.getName());
			}

			if (found) {
				FileListUtil f = new FileListUtil();
				list = f.getDirectoryList(share.getDirectory()[0].getName(),
						share.getDirectory()[0].getFilter());
				it = list.iterator();
				FileInfo fInfo = null;

				found = false;
				while (it.hasNext() && !found) {
					fInfo = (FileInfo) it.next();
					found = fInfo.getFileName().equals(fileName);
				}

				if (found) {
					String fullPathFileName = share.getDirectory()[0].getName()
							+ "/" + fInfo.getFileName();
					File file = new File(fullPathFileName);
					int length = 0;
					ServletOutputStream op = response.getOutputStream();
					ServletContext context = getServletConfig()
							.getServletContext();
					String mimetype = context.getMimeType(fullPathFileName);

					response.setContentType((mimetype != null) ? mimetype
							: "application/octet-stream");
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + fInfo.getFileName()
									+ "\"");

					byte[] bbuf = new byte[1024];
					DataInputStream in = new DataInputStream(
							new FileInputStream(file));

					while ((in != null) && ((length = in.read(bbuf)) != -1)) {
						op.write(bbuf, 0, length);
					}

					in.close();
					op.flush();
					op.close();
				} else {
					response.sendRedirect("index.jsp");
				}
			} else {
				response.sendRedirect("index.jsp");
			}
		} else {
			response.sendRedirect("index.jsp");
		}
	}

}
