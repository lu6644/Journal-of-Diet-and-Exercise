package Model.Profile;

public class Main {
	
	public static void main(String args[]) {
		UserProfile profile = new UserProfile("test", "test", "test", "test", 30, "male", 175.00, 70, null, false);
		System.out.println(profile.getUsername());
		System.out.println(profile.getPassword());
		System.out.println(profile.getFirstName());
		System.out.println(profile.getLastName());
		System.out.println(profile.getGender());
		System.out.println(profile.getHeight());
		System.out.println(profile.getAge());
		System.out.println(profile.getWeight());
		System.out.println(profile.getSpecialPeriod());
		System.out.println(profile.isHasWeightScale());
		
		profile = new UserProfile();
		profile.setAge(10);
		profile.setFirstName("test2");
		profile.setGender("female");
		profile.setHeight(180);
		profile.setHasWeightScale(true);
		profile.setLastName("test2");
		profile.setPassword("test2");
		profile.setSpecialPeriod("pregnant");
		profile.setUsername("test2");
		profile.setWeight(50);
		
		
		System.out.println("--------------------------------------");
		System.out.println(profile.getUsername());
		System.out.println(profile.getPassword());
		System.out.println(profile.getFirstName());
		System.out.println(profile.getLastName());
		System.out.println(profile.getGender());
		System.out.println(profile.getHeight());
		System.out.println(profile.getAge());
		System.out.println(profile.getWeight());
		System.out.println(profile.getSpecialPeriod());
		System.out.println(profile.isHasWeightScale());
	}
	
	
	
}
