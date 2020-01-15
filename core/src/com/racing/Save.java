package com.racing;

import java.io.*;

public class Save implements Serializable
{
    private static final long ID = 1L;
    private long record;

    public long getRecord() {
        return record;
    }

    public void setRecord(long record) {
        this.record = record;
    }
}
