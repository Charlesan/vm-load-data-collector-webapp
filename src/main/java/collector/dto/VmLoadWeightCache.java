package collector.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class VmLoadWeightCache {
	
	private final static String[] vmNames = {"vm0", "vm1", "vm2"};

	private Map<String, Integer> vmLoadWeight;
	
	public void updateVmLoadWeight(Map<String, Integer> newVmLoadWeight) {
		vmLoadWeight = newVmLoadWeight;
	}

	public Map<String, Integer> getVmLoadWeight() {
		if (vmLoadWeight == null) {
			vmLoadWeight = new HashMap<String, Integer>();
			for (String vmName : vmNames) {
				vmLoadWeight.put(vmName, 100);
			}
		}
		
		return vmLoadWeight;
	}

	public void setVmLoadWeight(Map<String, Integer> vmLoadWeight) {
		this.vmLoadWeight = vmLoadWeight;
	}
	
}
