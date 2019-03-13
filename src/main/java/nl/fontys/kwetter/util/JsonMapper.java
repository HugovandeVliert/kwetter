package nl.fontys.kwetter.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonMapper {
    private Gson gson;

    public JsonMapper() {
        gson =  new GsonBuilder().serializeNulls().create();
    }

    public <T> T fromJSON(String data, Class<T> type) {
        return gson.fromJson(data, type);
    }

    public String toJSON(Object object) {
        return gson.toJson(object, object.getClass());
    }
}
