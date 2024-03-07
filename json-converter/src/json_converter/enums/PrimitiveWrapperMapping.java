package json_converter.enums;

import javax.management.RuntimeErrorException;

public enum PrimitiveWrapperMapping {
	BOOLEAN(boolean.class, Boolean.class),
    BYTE(byte.class, Byte.class),
    SHORT(short.class, Short.class),
    INTEGER(int.class, Integer.class),
    LONG(long.class, Long.class),
    FLOAT(float.class, Float.class),
    DOUBLE(double.class, Double.class),
    CHARACTER(char.class, Character.class);

    private final Class<?> primitive;
    private final Class<?> wrapper;

    PrimitiveWrapperMapping(Class<?> primitive, Class<?> wrapper) {
        this.primitive = primitive;
        this.wrapper = wrapper;
    }
    
    public Class<?> getPrimitive() {
        return primitive;
    }

    public Class<?> getWrapper() {
        return wrapper;
    }

    public static Class<?> getWrapperByPrimitive(Class<?> primitiveClass) {
        for (PrimitiveWrapperMapping mapping : values()) {
            if (mapping.primitive == primitiveClass) {
                return mapping.wrapper;
            }
        }
        throw new RuntimeException("Invalid primitive type");
    }

    public static Class<?> getPrimitiveByWrapper(Class<?> wrapperClass) {
        for (PrimitiveWrapperMapping mapping : values()) {
            if (mapping.wrapper == wrapperClass) {
                return mapping.primitive;
            }
        }
        throw new RuntimeException("Invalid wrapper class");
    }
}
