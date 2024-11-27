package com.dineth.debateTracker.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WinLossStatDTO {
    private Integer wins;
    private Integer losses;
    private Integer winPercentage;

    public WinLossStatDTO(Integer wins, Integer losses) {
        this.wins = wins;
        this.losses = losses;
    }

    public Integer getWinPercentage() {
        if (wins == 0 && losses == 0) {
            return 0;
        }
        return (int) Math.round((double) wins / (wins + losses) * 100);
    }
}
