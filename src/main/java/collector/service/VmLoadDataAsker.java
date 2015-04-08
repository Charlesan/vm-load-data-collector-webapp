package collector.service;

import org.springframework.stereotype.Service;

import collector.dto.VmLoadData;
import collector.util.RestfulClient;

import com.google.gson.Gson;

@Service
public class VmLoadDataAsker {

	public VmLoadData requestVmLoadData(String vmIp) {
		StringBuffer url = new StringBuffer();
		url.append("http://" + vmIp + ":" + 8080);
		url.append("/cloud-ocs-monitor-data-gatherer/gatherer/sum/vmLoadData");
		
		String json = RestfulClient.sendGetRequest(url.toString());
		Gson gson = new Gson();
		VmLoadData result = (VmLoadData) gson.fromJson(json, VmLoadData.class);
		if (result == null) {
			result = new VmLoadData();
		}
		
		return result;
	}
	
	public double getVmReponseTime(String vmIp) {
		String ret = collector.util.ExecUtil.execCmd("ping -c 3 " + vmIp);
		String s[] = ret.substring(ret.lastIndexOf("=")).split("/");
		
		return Double.parseDouble(s[1]);
	}
}
