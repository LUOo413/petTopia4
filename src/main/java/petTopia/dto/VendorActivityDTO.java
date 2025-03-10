package petTopia.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import petTopia.model.vendor.VendorActivity;

@Getter
@Setter
public class VendorActivityDTO {
	private Integer id;
	private Integer vendorId; // 只保留 vendor 的 id
	private String name;
	private String description;
	private Date startTime;
	private Date endTime;
	private boolean isRegistrationRequired;
	private Integer activityTypeId; // 只保留 activityType 的 id
	private Date registrationDate;
	private int numberVisitor;
	private String address;
	private List<Integer> imageIds; // 只保留 images 的 id
	private Integer activityPeopleNumberId; // 只保留 activityPeopleNumber 的 id

	public VendorActivityDTO(VendorActivity vendorActivity) {
		this.id = vendorActivity.getId();
		this.vendorId = vendorActivity.getVendor() != null ? vendorActivity.getVendor().getId() : null;
		this.name = vendorActivity.getName();
		this.description = vendorActivity.getDescription();
		this.startTime = vendorActivity.getStartTime();
		this.endTime = vendorActivity.getEndTime();
		this.isRegistrationRequired = vendorActivity.isRegistrationRequired();
		this.activityTypeId = vendorActivity.getActivityType() != null ? vendorActivity.getActivityType().getId()
				: null;
		this.registrationDate = vendorActivity.getRegistrationDate();
		this.numberVisitor = vendorActivity.getNumberVisitor();
		this.address = vendorActivity.getAddress();
		this.imageIds = vendorActivity.getImages() != null
				? vendorActivity.getImages().stream().map(image -> image.getId()).collect(Collectors.toList())
				: null;
		this.activityPeopleNumberId = vendorActivity.getActivityPeopleNumber() != null
				? vendorActivity.getActivityPeopleNumber().getId()
				: null;
	}
}
