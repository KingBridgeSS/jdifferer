package org.bridge.core.decompile;

public interface Decompiler {
    String decompile(String fileName) throws Exception;
    String decompile(byte[] classBytes) throws Exception;

}
