package sk.nuit.blanche.model;

import com.google.android.gms.maps.model.LatLng;


public class Artist {
	
	private int id;
	private String name;
	private String work;
	private String image;
	private String place;
	private String country;
	private String type;
	private String desc;
	private boolean forKids;
	private Double latitude;
	private Double longitude;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isForKids() {
		return forKids;
	}
	public void setForKids(boolean forKids) {
		this.forKids = forKids;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public LatLng getGps(){
		return new LatLng(getLatitude(), getLongitude());
	}

}
