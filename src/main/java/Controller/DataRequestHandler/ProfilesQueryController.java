package Controller.DataRequestHandler;

import Model.DatabaseInteraction.ProfileDAO;
import Model.Profile.UserProfile;

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

    public UserProfile getProfile(int id){
        return ProfileDAO.getInstance().getProfile(id);
    }

    public UserProfile getProfileByUsername(String username, String password){
        return ProfileDAO.getInstance().getProfileByUsername(username, password);
    }

    public int verifyPassword(String username, String password){return ProfileDAO.getInstance().verifyPassword(username, password);}
}
