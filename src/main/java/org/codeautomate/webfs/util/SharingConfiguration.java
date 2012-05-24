package org.codeautomate.webfs.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class SharingConfiguration {
	private XMLConfiguration config;

	public static int LDAP_DOMAIN = 0;
	public static int LDAP_HOST = 1;
	public static int LDAP_SEARCH_BASE = 2;

	public SharingConfiguration() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL configURL = loader.getResource("sharing-config.xml");
		// URL configURL = getClass().getResource("/sharing-config.xml");

		this.config = new XMLConfiguration();
		config.setDelimiterParsingDisabled(true);
		try {
			config.load(configURL);
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLDAPConfig(int configID) {
		String[] keyConfig = { "domain", "ldap-host", "search-base" };
		return config.getProperty("ad-auth." + keyConfig[configID]).toString();
	}

	public List getShareByUser(String userID) {
		List retVal = new ArrayList();
		Object rootSection = config.getProperty("share.name");
		Share share;

		if (rootSection instanceof Collection) {
			int size = ((Collection) rootSection).size();
			for (int i = 0; i < size; i++) {
				if (userHasAccess(i, userID)) {
					share = new Share();
					share.setName(config.getProperty("share(" + i + ").name")
							.toString());
					share.setDirectory(getDirectoryByShareIdx(i));
					retVal.add(share);
				}
			}
		} else if (rootSection != null) {
			if (userHasAccess(-1, userID)) {
				share = new Share();
				share.setName(config.getProperty("share.name").toString());
				share.setDirectory(getDirectoryByShareIdx(-1));
				retVal.add(share);
			}
		}

		return retVal;
	}

	public boolean userHasAccess(int shareIdx, String userID) {
		String strShareIdx = "";
		Object userSection;
		boolean found = false;

		if (shareIdx >= 0) {
			strShareIdx = "(" + shareIdx + ")";
		}

		userSection = config.getProperty("share" + strShareIdx + ".users.user");
		if (userSection instanceof Collection) {
			int uSize = ((Collection) userSection).size();
			while (uSize > 0 && !found) {
				found = userID.toLowerCase().equals(
						config.getProperty(
								"share" + strShareIdx + ".users.user("
										+ --uSize + ")").toString()
								.toLowerCase());
			}
			return found;
		} else {
			return userID.toLowerCase().equals(
					userSection.toString().toLowerCase());
		}
	}

	public Directory[] getDirectoryByShareIdx(int shareIdx) {
		Directory[] retVal;
		Object pathSection, filterSection;
		String strShareIdx = "";
		Directory dir;

		if (shareIdx >= 0) {
			strShareIdx = "(" + shareIdx + ")";
		}

		pathSection = config.getProperty("share" + strShareIdx
				+ ".directories.directory.path");
		filterSection = config.getProperty("share" + strShareIdx
				+ ".directories.directory.filter");

		if (pathSection instanceof Collection) {
			retVal = new Directory[((Collection) pathSection).size()];
			for (int i = 0; i < retVal.length; i++) {
				dir = new Directory();
				dir.setFilter(config.getProperty(
						"share" + strShareIdx + ".directories.directory(" + i
								+ ").filter").toString());
				dir.setName(config.getProperty(
						"share" + strShareIdx + ".directories.directory(" + i
								+ ").path").toString());
				retVal[i] = dir;
			}
		} else {
			retVal = new Directory[1];
			dir = new Directory();
			dir.setFilter(filterSection.toString());
			dir.setName(pathSection.toString());
			retVal[0] = dir;
		}

		return retVal;
	}

	public class Share {
		private String name;
		private Directory[] directory;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setDirectory(Directory[] directory) {
			this.directory = directory;
		}

		public Directory[] getDirectory() {
			return directory;
		}
	}

	public class Directory {
		private String name;
		private String filter;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setFilter(String filter) {
			this.filter = filter;
		}

		public String getFilter() {
			return filter;
		}
	}

	public static void main(String[] args) {
		SharingConfiguration config = new SharingConfiguration();
		System.out.println(config.getShareByUser("hervin_i"));

	}
}
