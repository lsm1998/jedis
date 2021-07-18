package com.lsm.game;

import com.lsm.game.net.GameNetServer;
import com.lsm.game.net.GameNetServerBuild;

import java.io.IOException;

public class GameApplication
{
    public static void main(String[] args) throws IOException
    {
        GameNetServer gameNetServer = new GameNetServerBuild().build();

        gameNetServer.start();
    }
}
