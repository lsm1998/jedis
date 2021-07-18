package com.lsm.game.service;

import com.lsm.game.model.Ranks;
import com.lsm.game.model.Room;
import com.lsm.game.model.User;

import java.util.LinkedHashSet;

public class RoomService
{
    public Room createRoom()
    {
        Room room = new Room();
        room.setBlueRanks(new LinkedHashSet<>());
        room.setReadRanks(new LinkedHashSet<>());
        return room;
    }

    public boolean joinRoom(Room room, User user, Ranks ranks)
    {
        LinkedHashSet<User> ranksSet = room.getBlueRanks();
        if (ranks == Ranks.RED)
        {
            ranksSet = room.getReadRanks();
        }
        if (ranksSet.size() < Room.ROOM_SIZE)
        {
            ranksSet.add(user);
            return true;
        }
        return false;
    }

    public boolean joinRoom(Room room, User user)
    {
        return this.joinRoom(room, user, Ranks.BLUE) || this.joinRoom(room, user, Ranks.RED);
    }

    public boolean leaveRoom(Room room, User user, Ranks ranks)
    {
        LinkedHashSet<User> ranksSet = room.getBlueRanks();
        if (ranks == Ranks.RED)
        {
            ranksSet = room.getReadRanks();
        }
        return ranksSet.remove(user);
    }
}
