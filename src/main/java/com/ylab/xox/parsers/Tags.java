package com.ylab.xox.parsers;

/**
 *  Енам содержит значения тегов для сохранения в XML и JSON
 */

public enum Tags {
    TAG_GAMEPLAY ("Gameplay"),
    TAG_GAME ("Game"),
    TAG_STEP ("Step"),
    TAG_GAMERESULT ("GameResult"),
    TAG_PLAYER ("Player");

    private String value;
    Tags(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
