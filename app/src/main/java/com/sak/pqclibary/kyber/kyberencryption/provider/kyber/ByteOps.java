package com.sak.pqclibary.kyber.kyberencryption.provider.kyber;

import java.util.Arrays;


public final class ByteOps {

    public static long convertByteTo24BitUnsignedInt(byte[] x) {
        long r = (long) (x[0] & 0xFF);
        r = r | (long) ((long) (x[1] & 0xFF) << 8);
        r = r | (long) ((long) (x[2] & 0xFF) << 16);
        return r;
    }
    public static short barrettReduce(short a) {
        short t;
        long shift = (((long) 1) << 26);
        short v = (short) ((shift + (KyberParams.paramsQ / 2)) / KyberParams.paramsQ);
        t = (short) ((v * a) >> 26);
        t = (short) (t * KyberParams.paramsQ);
        return (short) (a - t);
    }
    public static short[] generateCBDPoly(byte[] buf, int paramsK) {
        long t, d;
        int a, b;
        short[] r = new short[KyberParams.paramsPolyBytes];
        switch (paramsK) {
            case 2:
                for (int i = 0; i < KyberParams.paramsN / 4; i++) {
                    t = ByteOps.convertByteTo24BitUnsignedInt(Arrays.copyOfRange(buf, (3 * i), buf.length));
                    d = t & 0x00249249;
                    d = d + ((t >> 1) & 0x00249249);
                    d = d + ((t >> 2) & 0x00249249);
                    for (int j = 0; j < 4; j++) {
                        a = (short) ((d >> (6 * j + 0)) & 0x7);
                        b = (short) ((d >> (6 * j + KyberParams.paramsETAK512)) & 0x7);
                        r[4 * i + j] = (short) (a - b);
                    }
                }
                break;
            default:
                for (int i = 0; i < KyberParams.paramsN / 8; i++) {
                    t = ByteOps.convertByteTo32BitUnsignedInt(Arrays.copyOfRange(buf, (4 * i), buf.length));
                    d = t & 0x55555555;
                    d = d + ((t >> 1) & 0x55555555);
                    for (int j = 0; j < 8; j++) {
                        a = (short) ((d >> (4 * j + 0)) & 0x3);
                        b = (short) ((d >> (4 * j + KyberParams.paramsETAK768K1024)) & 0x3);
                        r[8 * i + j] = (short) (a - b);
                    }
                }
        }
        return r;
    }
    public static short conditionalSubQ(short a) {
        a = (short) (a - KyberParams.paramsQ);
        a = (short) (a + ((int) ((int) a >> 15) & KyberParams.paramsQ));
        return a;
    }
    public static short montgomeryReduce(long a) {
        short u = (short) (a * KyberParams.paramsQinv);
        int t = (int) (u * KyberParams.paramsQ);
        t = (int) (a - t);
        t >>= 16;
        return (short) t;
    }
    public static long convertByteTo32BitUnsignedInt(byte[] x) {
        long r = (long) (x[0] & 0xFF);
        r = r | (long) ((long) (x[1] & 0xFF) << 8);
        r = r | (long) ((long) (x[2] & 0xFF) << 16);
        r = r | (long) ((long) (x[3] & 0xFF) << 24);
        return r;
    }

}
