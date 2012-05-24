var _dirList;
var _selectedDir;

function initDirList() {
	$.ajax({
		type : 'POST',
		url : 'ListDir',
		data : {},
		success : renderDirList,
		dataType : 'json'
	});
}

function renderDirList(dirList) {
	_dirList = dirList;
	var navHeader = $('#nav-left');
	var navTitle = $("<li class=\"nav-header\">Shared Folder</li>");

	var menuItem = $("<li class=\"dir-item\"></li>");
	var link = $("<a href=\"#\"><i class=\"dir-item-icon icon-folder-close\"></i> </a>")
	var cLink, cMenuItem;
	for ( var i = 0; i < dirList.data.length; i++) {
		cMenuItem = menuItem.clone();
		cLink = link.clone();
		cLink.attr("onclick", "loadDir(this, '" + dirList.data[i] + "')")
		cLink.append(dirList.data[i]);
		cMenuItem.append(cLink);

		navHeader.prepend(cMenuItem);
	}
	navHeader.prepend(navTitle);
}

function loadDir(s, dir) {
	$(".dir-item-icon").attr('class', 'dir-item-icon icon-folder-close');
	$(".dir-item").attr('class', 'dir-item');
	$(s).parent().attr('class', 'dir-item active');
	$(s).children().attr('class', 'dir-item-icon icon-folder-open icon-white');
	getFileList(dir);

	_selectedDir = dir;
}

function getFileList(dirName) {
	$(".data-row").remove();
	$.ajax({
		type : 'POST',
		url : 'ListFile',
		data : {
			'dir_name' : dirName
		},
		success : renderFileList,
		dataType : 'json'
	});
}

function renderFileList(retval) {
	var tBody = $("#t-data-body");
	var row = $("<tr class=\"data-row\"></tr>");
	var link = $("<a href=\"#\"></a>");
	var col = $("<td></td>");

	var cRow, cNo, cName, cTs, cLink;
	if (retval.success) {
		for ( var i = 0; i < retval.data.length; i++) {
			cRow = row.clone();

			cNo = col.clone();
			cNo.append(i + 1);
			cRow.append(cNo);

			cLink = link.clone();
			cLink.attr("onclick", "downloadFile('" + retval.data[i].fileName
					+ "','" + _selectedDir + "')");
			cLink.append(retval.data[i].fileName);

			cName = col.clone();
			cName.append(cLink);
			cRow.append(cName);

			cTs = col.clone();
			cTs.append(retval.data[i].fileTS);
			cRow.append(cTs);

			tBody.append(cRow);
		}
	} else {
		window.location = "index.jsp?r=timeout"
	}
}

function downloadFile(fileName, selectedDir) {
	$("#dir_name").val(selectedDir);
	$("#file_name").val(fileName);
	$("#frm_download").submit();
}

function logoutUser() {
	$.ajax({
		type : 'POST',
		url : 'Logout',
		success : logoutSuccess,
		dataType : 'json'
	});
}

function logoutSuccess(data) {
	window.location = "index.jsp";
}