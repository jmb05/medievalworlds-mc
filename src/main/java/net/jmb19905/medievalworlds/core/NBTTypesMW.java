package net.jmb19905.medievalworlds.core;

import net.minecraft.nbt.*;

public class NBTTypesMW {
    public static final byte LONG_NBT_ID = LongNBT.valueOf(0).getId();
    public static final byte INT_NBT_ID = IntNBT.valueOf(0).getId();
    public static final byte SHORT_NBT_ID = ShortNBT.valueOf((short)0).getId();
    public static final byte BYTE_NBT_ID = ByteNBT.valueOf((byte)0).getId();
    public static final byte FLOAT_NBT_ID = FloatNBT.valueOf(0).getId();
    public static final byte DOUBLE_NBT_ID = DoubleNBT.valueOf(0).getId();
    public static final byte STRING_NBT_ID = StringNBT.valueOf("").getId();

    private static final byte [] dummyByteArray = {0};
    private static final int [] dummyIntArray = {0};
    private static final long [] dummyLongArray = {0};
    public static final byte BYTE_ARRAY_NBT_ID = new ByteArrayNBT(dummyByteArray).getId();
    public static final byte INT_ARRAY_NBT_ID = new IntArrayNBT(dummyIntArray).getId();
    public static final byte LONG_ARRAY_NBT_ID = new LongArrayNBT(dummyLongArray).getId();
    public static final byte LIST_NBT_ID = new ListNBT().getId();
    public static final byte COMPOUND_NBT_ID = new CompoundNBT().getId();
}
