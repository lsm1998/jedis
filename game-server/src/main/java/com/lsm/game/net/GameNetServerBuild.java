/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm.game.net;

public class GameNetServerBuild
{
    public GameNetServer build()
    {
        return new GameNetServerImpl();
    }
}
