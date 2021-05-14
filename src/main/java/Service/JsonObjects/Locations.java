package Service.JsonObjects;

import Request.RegisterRequest;

import java.io.FileNotFoundException;

public class Locations {
    Location[] data;

    public Location[] getAllLocations() throws FileNotFoundException {
        return data;
    }
}
