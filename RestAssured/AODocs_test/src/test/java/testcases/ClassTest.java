package testcases;

import org.testng.annotations.Test;
import reporting.BaseReport;
import webservices.DocsApiRequestHelper;
import webservices.DocsApiResponse;

public class ClassTest extends BaseReport {

	public static String typefile = "application/vnd.google-apps.folder";
	public static String url = "https://www.googleapis.com/upload/drive/v3/files";
	public static String username = "test.aodocs3@gmail.com";

	public String CreateFolder() {

		DocsApiResponse folderresponse = DocsApiRequestHelper.CreateFolder(typefile, url);
		folderresponse.verifyResponseStatusCode(200);
		String idfolder = folderresponse.getJsonPath().getString("id");
		System.out.println(idfolder);

		return idfolder;

	}

	public String AddPermission(String folderid) {

		test = extent.createTest("Add Permission Test API");
		String url = "https://www.googleapis.com/drive/v3/files/" + folderid + "/permissions";
		DocsApiResponse permissionresponse = DocsApiRequestHelper.AddPermission(url, username);
		permissionresponse.verifyResponseStatusCode(200);
		String permissionid = permissionresponse.getJsonPath().getString("id");
		System.out.println(permissionid);

		return permissionid;

	}

	public String CreateFile(String folderid) {

		test = extent.createTest("Create File Test API");
		String url = "https://www.googleapis.com/upload/drive/v3/files";
		DocsApiResponse createfileresponse = DocsApiRequestHelper.CreateFile(url, folderid);
		createfileresponse.verifyResponseStatusCode(200);
		String fileid = createfileresponse.getJsonPath().getString("id");

		return fileid;

	}

	public void GetPermission(String folderid, String permissionid) {

		test = extent.createTest("Get Permission Test API ");
		String url = "https://www.googleapis.com/drive/v3/files/" + folderid + "permissions" + permissionid;
		DocsApiRequestHelper.GetPermission(url);

	}

	public void SharedFile(String fileid) {

		test = extent.createTest("Shared File Test API");
		String url = "https://www.googleapis.com/drive/v3/files/" + fileid + "permissions";
		DocsApiResponse getsharedfileresponse = DocsApiRequestHelper.SharedFile(url);
		getsharedfileresponse.verifyResponseStatusCode(400);
	}

	@Test
	public void ScenarioComplet() {

		test = extent.createTest("Scenario Test API");
		String folderid = CreateFolder();
		String permissionid = AddPermission(folderid);
		String fileid = CreateFile(folderid);
		GetPermission(folderid, permissionid);
		SharedFile(fileid);
	}

}
