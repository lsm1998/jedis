package com.lsm.game.connect;

public class ConnectFactoryImpl implements ConnectFactory
{
    protected ConnectFactoryImpl()
    {
    }

    @Override
    public ClientConnect getConnect()
    {
        return new ClientConnect();
    }
}
