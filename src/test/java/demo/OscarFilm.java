package demo;
import java.util.LinkedHashMap;
import java.util.Map;

public class OscarFilm {

    private String title;
    private int nominations;
    private int awards;
    private boolean isWinner;
    private int year;
    private long epochTime;

    public OscarFilm(long epochTime, int year, String title, int nominations, int awards, boolean isWinner){
        this.epochTime = epochTime;
        this.year = year;
        this.title = title;
        this.nominations = nominations;
        this.awards = awards;
        this.isWinner = isWinner;
    }

    public long getEpochTime(){
        return epochTime;
    }

    public int getYear(){
        return year;
    }

    public String getTitle(){
        return title;
    }

    public int getNominations(){
        return nominations;
    }

    public int getAwards(){
        return awards;
    }

    public boolean getIsWinner(){
        return isWinner;
    }

        //Add a method that returns a Map with fields in the desired order.
        public Map<String, Object> oscarInDesiredOrder(){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("epochTime", epochTime);
        map.put("year", year);
        map.put("title", title);
        map.put("nominations", nominations);
        map.put("awards", awards);
        map.put("isWinner", isWinner);
        return map;
        }

}
