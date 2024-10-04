package demo;
import java.util.LinkedHashMap;
import java.util.Map;

public class HockeyTeam {
   private String teamName;
   private String year;
   private double winPercentage;
   private long currentEpochTime;

    public HockeyTeam(long currentEpochTime, String teamName, String year, double winPercentage){
        this.currentEpochTime = currentEpochTime;
        this.teamName=teamName;
        this.year=year;
        this.winPercentage=winPercentage;
    }
    public long getEpochTime() {
        return currentEpochTime;
    }

    public String getTeamName(){
        return teamName;
    }
    public String getYear(){
        return year;
    }
    public double getWinPercentage(){
        return winPercentage;
    }
    //Add a method that returns a Map with fields in the desired order.

    public Map<String, Object> inputDataInOrderMap(){
        // Use LinkedHashMap to maintain insertion order
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("epochTime", currentEpochTime);
        map.put("teamName", teamName);
        map.put("year", year);
        map.put("winPercentage", winPercentage);
        return map;
    } 

}


