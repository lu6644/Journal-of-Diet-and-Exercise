package Model.Profile;

public class UserProfile {

	// Attributes of the user profile
	private int id = -1;
	private String username = "";
	private String password = "";
	private String firstName;
	private String lastName;
	private int age;
	private String gender;
	private double height;

	private String heightUnit;
	private double weight;

	private String weightUnit;
	private String specialPeriod;
	private boolean hasWeightScale;

	// Default constructor
	public UserProfile() {
		
	}

	// Parameterized constructor to initialize the profile data
	public UserProfile(String username, String password, String firstName, String lastName, int age, String gender,
			double height,String heightUnit, double weight,String weightUnit, String specialPeriod, boolean hasWeightScale) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.heightUnit = heightUnit;
		this.weightUnit = weightUnit;
		this.weight = weight;
		this.specialPeriod = specialPeriod;
		this.hasWeightScale = hasWeightScale;
	}

	// Another parameterized constructor for a simplified profile data
	public UserProfile(String firstName, String lastName, int age, String gender, double height,
					   double weight, String specialPeriod, boolean hasWeightScale){
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.specialPeriod = specialPeriod;
		this.hasWeightScale = hasWeightScale;
	}

	// Getter and setter methods for each attribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getHeightUnit(){return heightUnit;}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setHeightUnit(String heightUnit){
		this.heightUnit = heightUnit;
	}

	public double getWeight() {
		return weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setWeightUnit(String weightUnit){
		this.weightUnit = weightUnit;
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

	// Method to calculate Basal Metabolic Rate (BMR)
	public double calculateBMR() {
		double h, w;
		if(heightUnit.equals("inches")){
			h = height * 2.54;
		}
		else{
			h = height;
		}
		if(weightUnit.equals("lbs")){
			w = weight * 0.453592;
		}
		else{
			w = weight;
		}
		return gender.equals("male") ? (10 * w + 6.25 * h - 5 * age + 5)
				: (10 * w + 6.25 * h - 5 * age - 161);
		// The equation of Mifflin-St.Jeor
	}

	// Override toString() method for debugging or logging purposes
	@Override
	public String toString() {
		return "UserProfile{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", age=" + age +
				", gender='" + gender + '\'' +
				", height=" + height +
				", weight=" + weight +
				", specialPeriod='" + specialPeriod + '\'' +
				", hasWeightScale=" + hasWeightScale +
				'}';
	}
}
