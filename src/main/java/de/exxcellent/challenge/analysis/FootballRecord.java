package de.exxcellent.challenge.analysis;

import java.util.Map;

/**
 * Domain model for a football record.
 * @author Uwe Clement <uwe.clement@gmail.com>
 */
public class FootballRecord {
    private static final String KEY_TEAM = "team";
    private static final String KEY_GOALS = "goals";
    private static final String KEY_GOALS_ALLOWED = "goals allowed";
    private final String team;
    private final long goals;
    private final long goalsAllowed;

    public FootballRecord(String team, long goals, long goalsAllowed) {
        this.team = team;
        this.goals = goals;
        this.goalsAllowed = goalsAllowed;
    }

    public static FootballRecord fromMap(Map<String, String> record) {
        String team = record.get(KEY_TEAM);
        if (team == null) {
            throw new IllegalArgumentException("Missing required column: " + KEY_TEAM);
        }
        String goalsStr = record.get(KEY_GOALS);
        if (goalsStr == null) {
            throw new IllegalArgumentException("Missing required column: " + KEY_GOALS);
        }
        String allowedStr = record.get(KEY_GOALS_ALLOWED);
        if (allowedStr == null) {
            throw new IllegalArgumentException("Missing required column: " + KEY_GOALS_ALLOWED);
        }
        long goals = Long.parseLong(goalsStr);
        long allowed = Long.parseLong(allowedStr);
        return new FootballRecord(team, goals, allowed);
    }

    public long getSpread() {
        return Math.abs(goals - goalsAllowed);
    }

    public String getTeam() {
        return team;
    }
}
