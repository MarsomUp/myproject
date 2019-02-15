package com.weina.redis;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.util.SafeEncoder;

import java.io.*;

/**
 * @Description: 对象转字节，字节转对象，redis存储时使用
 * @Author: mayc
 * @Date: 2019/02/14 09:57
 */
public class FstSerializer implements ISerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FstSerializer.class);

    public static final ISerializer me = new FstSerializer();
    @Override
    public byte[] keyToBytes(String key) {
        return SafeEncoder.encode(key);
    }

    @Override
    public String keyFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    @Override
    public byte[] fieldToBytes(Object field) {
        return valueToBytes(field);
    }

    @Override
    public Object fieldFromBytes(byte[] bytes) {
        return valueFromBytes(bytes);
    }

    @Override
    public byte[] valueToBytes(Object value) {
        FSTObjectOutput fstOut = null;
        try {
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            fstOut = new FSTObjectOutput(bytesOut);
            fstOut.writeObject(value);
            fstOut.flush();
            byte[] bout = bytesOut.toByteArray();
            return bout;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(fstOut != null)
                try {fstOut.close();} catch (IOException e) {LOGGER.error(e.getMessage(), e);}
        }
    }

    @Override
    public Object valueFromBytes(byte[] bytes) {
        if(bytes == null || bytes.length == 0)
            return null;

        FSTObjectInput fstInput = null;
        try {
            fstInput = new FSTObjectInput(new ByteArrayInputStream(bytes));
            return fstInput.readObject();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if(fstInput != null)
                try {fstInput.close();} catch (IOException e) {LOGGER.error(e.getMessage(), e);}
        }
    }
}
