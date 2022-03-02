package net.abyssdev.abysslib.nbt;

import net.abyssdev.abysslib.utils.Version;
import net.abyssdev.nbtutils.*;

public final class NBTUtils {

    private static final INBT NBT;

    static {
        switch (Version.getCurrentVersion()) {
            case v1_8_R3: {
                NBT = new v1_8_R3();
                break;
            }

            case v1_9_R1: {
                NBT = new v1_9_R1();
                break;
            }

            case v1_9_R2: {
                NBT = new v1_9_R2();
                break;
            }

            case v1_10_R1: {
                NBT = new v1_10_R1();
                break;
            }

            case v1_11_R1: {
                NBT = new v1_11_R1();
                break;
            }

            case v1_12_R1: {
                NBT = new v1_12_R1();
                break;
            }

            case v1_13_R2: {
                NBT = new v1_13_R2();
                break;
            }

            case v1_14_R1: {
                NBT = new v1_14_R1();
                break;
            }

            case v1_15_R1: {
                NBT = new v1_15_R1();
                break;
            }

            case v1_16_R1: {
                NBT = new v1_16_R1();
                break;
            }

            case v1_16_R2: {
                NBT = new v1_16_R2();
                break;
            }

            case v1_16_R3: {
                NBT = new v1_16_R3();
                break;
            }

            case v1_17_R1: {
                NBT = new v1_17_R1();
                break;
            }

            case v1_18_R1: {
                NBT = new v1_18_R1();
                break;
            }

            default: {
                NBT = null;
            }
        }
    }

    public static INBT get() {
        return NBTUtils.NBT;
    }

}
