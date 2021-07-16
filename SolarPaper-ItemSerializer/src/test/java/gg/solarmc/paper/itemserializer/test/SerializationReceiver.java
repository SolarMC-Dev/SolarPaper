package gg.solarmc.paper.itemserializer.test;

import java.io.OutputStream;

public interface SerializationReceiver {

    void serializeAsBytes(OutputStream output);
}
