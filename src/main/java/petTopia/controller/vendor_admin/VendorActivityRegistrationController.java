package petTopia.controller.vendor_admin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import petTopia.model.vendor.ActivityRegistration;
import petTopia.repository.vendor_admin.ActivityRegistrationRepository;

@Controller
public class VendorActivityRegistrationController {

	@Autowired
	private ActivityRegistrationRepository activityRegistrationRepository;

	@GetMapping("/api/vendor_admin/activity/registration")
	public ResponseEntity<?> getRegistrationByActivityId(@RequestParam Integer activityId) {
		List<ActivityRegistration> registration = activityRegistrationRepository.findByVendorActivityId(activityId);
		if (registration.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList()); // ✅ 返回空数组 []
		}
		return new ResponseEntity<>(registration, HttpStatus.OK);

	}

	// 批量更新状态为 "confirmed" 并发送通知
	@PutMapping("/api/vendor_admin/activity/confirmAll")
	public ResponseEntity<?> confirmAllRegistrations(@RequestParam Integer activityId) {
		List<ActivityRegistration> registrations = activityRegistrationRepository.findByVendorActivityId(activityId);

		if (registrations.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No registrations found.");
		}

		// 批量更新状态为 "confirmed"
		registrations.forEach(registration -> {
			registration.setStatus("confirmed");
			activityRegistrationRepository.save(registration);

			// 发送通知（假设你有一个发送通知的服务）
//			sendNotification(registration.getMember(),
//					"Your registration for activity " + activityId + " has been confirmed.");
		});

		return ResponseEntity.ok("All registrations have been confirmed and notifications sent.");
	}

	@ResponseBody
	@PutMapping("/api/vendor_admin/registration/update/{id}")
	public ResponseEntity<?> updateRegistrationStatus(@PathVariable Integer id,
			@RequestBody String status) {
		Optional<ActivityRegistration> registrationopt = activityRegistrationRepository.findById(id);

		if (registrationopt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No registrations found.");
			
		}
	    ActivityRegistration registration = registrationopt.get();

		registration.setStatus(status);
		activityRegistrationRepository.save(registration);
		return ResponseEntity.ok(registration);
	}

	// 单个报名 ID 更新状态为 "confirmed" 并发送通知
	@PutMapping("/api/vendor_admin/activity/confirmById/{registrationId}")
	public ResponseEntity<?> confirmRegistrationById(@PathVariable Integer registrationId) {
		ActivityRegistration registration = activityRegistrationRepository.findById(registrationId).orElse(null);

		if (registration == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found.");
		}

		// 更新状态为 "confirmed"
		registration.setStatus("confirmed");
		activityRegistrationRepository.save(registration);

		// 发送通知
//		sendNotification(registration.getMember(), "Your registration has been confirmed.");

		return ResponseEntity.ok("Registration confirmed and notification sent.");
	}

	// 单个报名 ID 更新状态为 "canceled" 并发送通知
	@PutMapping("/api/vendor_admin/activity/cancelById")
	public ResponseEntity<?> cancelRegistrationById(@RequestParam Integer registrationId) {
		ActivityRegistration registration = activityRegistrationRepository.findById(registrationId).orElse(null);

		if (registration == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found.");
		}

		// 更新状态为 "canceled"
		registration.setStatus("canceled");
		activityRegistrationRepository.save(registration);

		// 发送通知
//		sendNotification(registration.getMember(), "Your registration has been canceled.");

		return ResponseEntity.ok("Registration canceled and notification sent.");
	}

	// 发送通知的示例方法
//	private void sendNotification(String member, String message) {
//		// 这里你需要实现发送通知的逻辑，可以是邮件、消息队列或其他方式
//		// 假设使用某个 NotificationService 来发送通知
//		System.out.println("Sending notification to " + member + ": " + message);
//	}

}
