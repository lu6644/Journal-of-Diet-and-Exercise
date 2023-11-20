package Controller.DataRequestHandler;

import Model.DatabaseInteraction.ProfileDAO;
import Model.Profile.UserProfile;
import View.ProfileUI.ProfileUIData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProfilesQueryController {
    private static ProfilesQueryController instance = null;

    private ProfilesQueryController() {

    }

    public static ProfilesQueryController getInstance() {
        if (instance == null) {
            instance = new ProfilesQueryController();
        }
        return instance;
    }

    public List<Map<String,String>> getProfiles(){
        List<UserProfile> profiles = ProfileDAO.getInstance().getProfileList();
        return profileListUIMapper(profiles);
    }

    private List<Map<String,String>> profileListUIMapper(List<UserProfile> profiles){
        return profiles.stream().map(profile -> Map.of(
                "id", String.valueOf(profile.getId()),
                "name", profile.getFirstName() + ' ' + profile.getLastName()
        )).collect(Collectors.toList());
    }

    public ProfileUIData getProfile(int id){
        UserProfile profile = ProfileDAO.getInstance().getProfile(id);
        ProfileUIData profileUIData = new ProfileUIData(profile.getUsername(), profile.getPassword(),
                profile.getFirstName(), profile.getLastName(), profile.getAge(), profile.getGender(),
                profile.getHeight(), profile.getHeightUnit(), profile.getWeight(), profile.getWeightUnit(),
                profile.getSpecialPeriod(), profile.isHasWeightScale());
        profileUIData.setId(id);
        return profileUIData;
    }

    public ProfileUIData getProfileByUsername(String username, String password){
        UserProfile profile = ProfileDAO.getInstance().getProfileByUsername(username, password);
        ProfileUIData profileUIData = new ProfileUIData(username,password,profile.getFirstName(),
                profile.getLastName(), profile.getAge(), profile.getGender(), profile.getHeight(),
                profile.getHeightUnit(), profile.getWeight(), profile.getWeightUnit(),profile.getSpecialPeriod(),
                profile.isHasWeightScale());
        profileUIData.setId(profile.getId());
        return profileUIData;
    }

    public int verifyPassword(String username, String password){return ProfileDAO.getInstance().verifyPassword(username, password);}
}
