package com.lsm.game.model;

import lombok.Data;

import java.util.Date;
import java.util.LinkedHashSet;

@Data
public class Room
{
    public static final int ROOM_SIZE = 2;

    private LinkedHashSet<User> blueRanks;
    private LinkedHashSet<User> readRanks;
    private Date startDate;
    private Date endDate;
}
