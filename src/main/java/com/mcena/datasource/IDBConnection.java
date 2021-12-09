package com.mcena.datasource;

public interface IDBConnection {
    <T> void initQuery(T genericObject);
}
