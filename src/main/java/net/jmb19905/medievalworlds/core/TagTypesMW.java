package net.jmb19905.medievalworlds.core;

import net.minecraft.nbt.*;

public class TagTypesMW {
    public static final byte LONG_TAG_ID = LongTag.valueOf(0).getId();
    public static final byte INT_TAG_ID = IntTag.valueOf(0).getId();
    public static final byte SHORT_TAG_ID = ShortTag.valueOf((short)0).getId();
    public static final byte BYTE_TAG_ID = ByteTag.valueOf((byte)0).getId();
    public static final byte FLOAT_TAG_ID = FloatTag.valueOf(0).getId();
    public static final byte DOUBLE_TAG_ID = DoubleTag.valueOf(0).getId();
    public static final byte STRING_TAG_ID = StringTag.valueOf("").getId();

    private static final byte [] dummyByteArray = {0};
    private static final int [] dummyIntArray = {0};
    private static final long [] dummyLongArray = {0};
    public static final byte BYTE_ARRAY_TAG_ID = new ByteArrayTag(dummyByteArray).getId();
    public static final byte INT_ARRAY_TAG_ID = new IntArrayTag(dummyIntArray).getId();
    public static final byte LONG_ARRAY_TAG_ID = new LongArrayTag(dummyLongArray).getId();
    public static final byte LIST_TAG_ID = new ListTag().getId();
    public static final byte COMPOUND_TAG_ID = new CompoundTag().getId();
}
