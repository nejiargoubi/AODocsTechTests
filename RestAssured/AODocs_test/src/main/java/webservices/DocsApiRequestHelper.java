package webservices;

import org.json.JSONObject;
import webservices.DocsApiResponse.RequestMethod;

public class DocsApiRequestHelper {

	public static DocsApiResponse CreateFolder(String type, String url) {

		JSONObject CreateFolderParams = new JSONObject();
		CreateFolderParams.put("mimeType", type);

		return new DocsApiResponse(RequestMethod.POST, url, CreateFolderParams);
	}

	public static DocsApiResponse AddPermission(String url, String username) {

		JSONObject AddPermissionParams = new JSONObject();
		AddPermissionParams.put("role", "writer");
		AddPermissionParams.put("type", "user");
		AddPermissionParams.put("emailAddress", username);

		return new DocsApiResponse(RequestMethod.POST, url, AddPermissionParams);
	}

	public static DocsApiResponse CreateFile(String url, String folderid) {

		JSONObject CreateFileParams = new JSONObject();
		JSONObject parentsObject = new JSONObject();
		parentsObject.put("id", folderid);
		CreateFileParams.put("parents", parentsObject);
		System.out.println(CreateFileParams);

		return new DocsApiResponse(RequestMethod.POST, url, CreateFileParams);
	}
	
	
	public static DocsApiResponse GetPermission(String url) {
		JSONObject GetPermissionParams = new JSONObject();
		
		return new DocsApiResponse(RequestMethod.GET, url,GetPermissionParams);
	}
	
	
	public static DocsApiResponse SharedFile(String url) {
				
		JSONObject SharedFileParams = new JSONObject();
		SharedFileParams.put("role", "writer");
		SharedFileParams.put("type", "user");
		SharedFileParams.put("emailAddress", "techtest-qa@test.aodocs.com");
		
		return new DocsApiResponse(RequestMethod.POST, url,SharedFileParams);
	}
	
	
}