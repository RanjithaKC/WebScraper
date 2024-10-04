package demo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JsonUtils {
    public static void writeHockeyTeamDataToJason( List<HockeyTeam> hockeyTeamList){
        ObjectMapper objectmapper = new ObjectMapper();
        List<Map<String, Object>> jsonOutputList = new ArrayList<>();
        
        for (HockeyTeam team : hockeyTeamList) {
            // Use the inputDataInOrderMap method created in hockey class to get the ordered data
            jsonOutputList.add(team.inputDataInOrderMap()); 
        }
        try{
            objectmapper.writeValue(new File("hockey-team-data.json"), jsonOutputList);
            System.out.println("Data has been written to hockey-team-data.json");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void writeOscarDataToJson(List<OscarFilm> oscarList){
        ObjectMapper objectmapper = new ObjectMapper();
        List<Map<String, Object>> jsonOutputList = new ArrayList<>();
        for(OscarFilm film : oscarList){
            jsonOutputList.add(film.oscarInDesiredOrder());
        }
        try{
        objectmapper.writeValue(new File("oscar-winner-data.json"), jsonOutputList);
        System.out.println("Data has been written to oscar-winner-data.json");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
