package petTopia.controller.vendor_admin;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import petTopia.model.vendor.ActivityType;
import petTopia.model.vendor.CalendarEvent;
import petTopia.model.vendor.Vendor;
import petTopia.model.vendor.VendorReview;
import petTopia.repository.vendor.CalendarEventRepository;
import petTopia.repository.vendor.VendorRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class VendorCalenderEventController {

	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private CalendarEventRepository calendarEventRepository;
	
	@ResponseBody
	@GetMapping("/api/vendor_admin/calender/{vendorId}")
	public ResponseEntity<?> getCalenderEventsByVendorId(@PathVariable Integer vendorId) {
		Optional<Vendor> vendor = vendorRepository.findById(vendorId);
		if(vendor.isPresent()) {
			List<CalendarEvent> calender = calendarEventRepository.findByVendorId(vendorId);
			return ResponseEntity.ok(calender);
		
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	@ResponseBody
	@PostMapping("/api/vendor_admin/calender/add")
	public ResponseEntity<?> addCalendarEvent(
		    @RequestParam Integer vendorId,
		    @RequestParam String eventTitle,
		    @RequestParam("start_time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startTime,
		    @RequestParam("end_time") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endTime,@RequestParam String color) {
		    
		    try {
		        Vendor vendor = vendorRepository.findById(vendorId)
		                                       .orElseThrow(() -> new Exception("Vendor not found"));
		        
		        CalendarEvent calendarEvent = new CalendarEvent();
		        calendarEvent.setEventTitle(eventTitle);
		        calendarEvent.setStartTime(startTime);
		        calendarEvent.setEndTime(endTime);
		        calendarEvent.setVendor(vendor);  // 这里关联了 vendor_id
		        calendarEvent.setCreatedAt(new Date());
		        calendarEvent.setUpdatedAt(new Date());
		        calendarEvent.setColor(color);
		        calendarEventRepository.save(calendarEvent);

		        return new ResponseEntity<>(calendarEvent,HttpStatus.CREATED);  // 返回成功响应
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // 返回失败响应
		    }
		}

	
	
}
