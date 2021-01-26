/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.config;

import lombok.Data;

@Data
public class ClientConfig
{
    private String host = "127.0.0.1";

    private int port = 6379;
}
