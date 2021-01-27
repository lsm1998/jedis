package com.lsm1998.jedis.connect;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.utils.ArraysUtil;
import com.lsm1998.jedis.server.RedisDBServer;
import lombok.Getter;
import lombok.Setter;

import java.nio.channels.SocketChannel;

public class RedisClientConnect
{
    // 当前选择的数据库下标
    private int dbIndex;

    // 数据库
    @Setter
    private RedisDBServer redisDBServer;

    protected SocketChannel channel;

    // 当前操作的命令
    @Getter
    private String cmd;
    // 当前操作的参数列表
    @Getter
    private String[] args;

    public int selectDB(int index)
    {
        if (index == this.dbIndex)
        {
            return 0;
        }
        this.dbIndex = index;
        return 1;
    }

    public RedisDB getRedisDB()
    {
        return this.redisDBServer.getRedisDB(this.dbIndex);
    }

    public boolean parse(String command)
    {
        if (command == null) return false;
        String[] array = command.split(" ");
        if (array.length < 2)
        {
            return false;
        }
        this.cmd = array[0];
        this.args = ArraysUtil.remove(0, array, String.class);
        return true;
    }
}
