package Model.Profile;

public class UserProfile {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private double height;
	private double weight;
	private String specialPeriod;
	private boolean hasWeightScale;

	public UserProfile(String username, String password, String firstName, String lastName, int age, String gender,
			double height, double weight, String specialPeriod, boolean hasWeightScale) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.specialPeriod = specialPeriod;
		this.hasWeightScale = hasWeightScale;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getSpecialPeriod() {
		return specialPeriod;
	}

	public void setSpecialPeriod(String specialPeriod) {
		this.specialPeriod = specialPeriod;
	}

	public boolean isHasWeightScale() {
		return hasWeightScale;
	}

	public void setHasWeightScale(boolean hasWeightScale) {
		this.hasWeightScale = hasWeightScale;
	}

	public double calculateBMR() {
		return gender.equals("male") ? (10 * weight + 6.25 * height - 5 * age + 5)
				: (10 * weight + 6.25 * height - 5 * age - 161);
		// The equation of Mifflin-St.Jeor
	}

}
