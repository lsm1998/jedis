/**
 * 作者：刘时明
 * 时间：2021/2/3
 */
package com.lsm1998.jedis.storage;

import com.lsm1998.jedis.common.db.RedisDB;

public interface RedisStorage
{
    RedisDB[] load();

    void save(RedisDB[] redisDBArrays);
}
