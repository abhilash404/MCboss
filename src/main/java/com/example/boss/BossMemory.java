package com.example.boss;

import java.util.ArrayList;
import java.util.List;

public class BossMemory {

    private static final int MAX_MEMORY_ENTRIES = 10;

    // Rolling scratchpad of fight events
    private final List<String> fightLog = new ArrayList<>();

    // Playstyle counters
    private int rangedAttacks = 0;
    private int meleeAttacks = 0;
    private int retreats = 0;
    private int shields = 0;
    private double avgDistance = 0;
    private int distanceSamples = 0;

    // Add an event to the fight log
    public void addEvent(String event) {
        fightLog.add(event);
        if (fightLog.size() > MAX_MEMORY_ENTRIES) {
            fightLog.remove(0); // drop oldest entry
        }
    }

    // Record player actions
    public void recordRangedAttack() { rangedAttacks++; }
    public void recordMeleeAttack() { meleeAttacks++; }
    public void recordRetreat() { retreats++; }
    public void recordShield() { shields++; }

    public void recordDistance(double distance) {
        distanceSamples++;
        avgDistance = ((avgDistance * (distanceSamples - 1)) + distance) / distanceSamples;
    }

    // Infer dominant playstyle as a string
    public String getPlaystyleProfile() {
        String dominant;
        if (rangedAttacks > meleeAttacks * 2) {
            dominant = "KITER (shoots from distance)";
        } else if (meleeAttacks > rangedAttacks * 2) {
            dominant = "BRAWLER (fights up close)";
        } else if (retreats > 5) {
            dominant = "PASSIVE (avoids engagement)";
        } else {
            dominant = "MIXED";
        }

        return String.format(
                "Dominant: %s | Ranged: %d | Melee: %d | Retreats: %d | Avg distance: %.1f blocks",
                dominant, rangedAttacks, meleeAttacks, retreats, avgDistance
        );
    }

    // Get fight log as a single string for the prompt
    public String getFightLog() {
        if (fightLog.isEmpty()) return "No events yet.";
        return String.join("\n", fightLog);
    }

    // Reset for a new fight
    public void reset() {
        fightLog.clear();
        rangedAttacks = 0;
        meleeAttacks = 0;
        retreats = 0;
        shields = 0;
        avgDistance = 0;
        distanceSamples = 0;
    }
}