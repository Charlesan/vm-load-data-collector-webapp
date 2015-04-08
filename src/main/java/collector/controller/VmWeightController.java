package collector.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import collector.dto.VmLoadWeightCache;

@Controller
@RequestMapping(value="/vm/weight")
public class VmWeightController {
	
	@Autowired
	private VmLoadWeightCache vmLoadWeightCache;

	@RequestMapping(value="/getVmWeight", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> getVmWeight() {
		return vmLoadWeightCache.getVmLoadWeight();
	}
}
