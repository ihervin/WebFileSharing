package org.codeautomate.webfs.util;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileListUtil {
	public List getDirectoryList(String dirPath, String filter) {
		File dir = new File(dirPath);
		CustomFileNameFilter csFilter = new CustomFileNameFilter(filter);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dt = new Date();
		File f;
		FileInfo temp;
		String[] children = dir.list(csFilter);
		List list = new ArrayList();
		Arrays.sort(children, String.CASE_INSENSITIVE_ORDER);
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				f = new File(dirPath + children[i]);
				if (f.isFile()) {
					temp = new FileInfo();
					temp.setFileName(children[i]);
					temp.setFileTS(sdf.format(new Date(f.lastModified())));
					list.add(temp);
				}
			}
		}

		return list;
	}

	public class CustomFileNameFilter implements FilenameFilter {
		private String filter;

		public CustomFileNameFilter(String filter) {
			this.filter = filter;
		}

		public boolean accept(File dir, String name) {
			return name.matches(filter);
		}

	}

}
