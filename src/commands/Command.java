package commands;

import util.Response;

import java.io.Serializable;
import java.util.List;

public abstract class Command implements Serializable {
    public abstract Response execute(List<Object> params);



}
