package Handler;

import com.google.gson.Gson;

public class ObjectEncoderDecoder {
    private Gson gson = new Gson();

    public Object encode(String reqData, Class c) {
        return gson.fromJson(reqData, c);
    }

    public String decode(Object result) {
        return gson.toJson(result);
    }
}
