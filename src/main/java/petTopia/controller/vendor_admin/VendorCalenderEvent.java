package petTopia.controller.vendor_admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VendorCalenderEvent {

	@GetMapping("path")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}
	
	
}
