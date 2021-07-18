/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm.game.net;

import java.io.IOException;

public interface GameNetServer
{
    void start() throws IOException;

    void stop() throws IOException;
}
