package util;

import java.io.Serializable;

public class Response implements Serializable {
    private final String title;
    private final String message;
    public Response(String title, String message){
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return title + " : " + message;
    }
}
