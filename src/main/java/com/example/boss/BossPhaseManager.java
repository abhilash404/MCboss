package com.example.boss;

public class BossPhaseManager {

    public enum Phase {
        PHASE_1, // 100-60% health - arrogant, testing player
        PHASE_2, // 60-30% health - focused, adapting
        PHASE_3  // 30-0% health  - enraged, desperate
    }

    private Phase currentPhase = Phase.PHASE_1;

    // Call this every cycle to update phase based on health
    public void update(float currentHealth, float maxHealth) {
        float healthPercent = (currentHealth / maxHealth) * 100;

        if (healthPercent > 60) {
            currentPhase = Phase.PHASE_1;
        } else if (healthPercent > 30) {
            currentPhase = Phase.PHASE_2;
        } else {
            currentPhase = Phase.PHASE_3;
        }
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    // Returns the personality prompt for the current phase
    public String getPersonalityPrompt() {
        return switch (currentPhase) {
            case PHASE_1 -> """
                You are the Ancient Warlord. You are powerful and barely threatened.
                You are arrogant and toying with the player. You test their abilities
                before committing to serious attacks.
                """;
            case PHASE_2 -> """
                You are the Ancient Warlord. You are wounded but focused.
                You have stopped underestimating the player. You fight with
                calculated aggression and adapt to their patterns.
                """;
            case PHASE_3 -> """
                You are the Ancient Warlord. You are cornered and desperate.
                You fight with everything you have. No more holding back.
                You are enraged and will use any move available to survive.
                """;
        };
    }

    // Returns which moves are unlocked in current phase
    public String getAvailableMoves() {
        return switch (currentPhase) {
            case PHASE_1 -> "melee_strike, ranged_barrage, taunt, summon_minions";
            case PHASE_2 -> "melee_strike, ranged_barrage, taunt, summon_minions, shield_wall, gap_closer";
            case PHASE_3 -> "melee_strike, ranged_barrage, taunt, summon_minions, shield_wall, gap_closer, whirlwind, enrage";
        };
    }

    public boolean isEnraged() {
        return currentPhase == Phase.PHASE_3;
    }
}