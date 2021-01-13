package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import java.util.Date;

public final class CheckNewShip {

    public static boolean checkData(Ship ship) {
        String name = ship.getName();
        String planet = ship.getPlanet();
        Date prodDate = ship.getProdDate();
        ShipType type = ship.getShipType();
        Integer crewSize = ship.getCrewSize();
        Double speed = ship.getSpeed();

        speed = UpdatingShip.roundSpeed(speed);
        ship.setSpeed(speed);

        return getCheckName(name) && getCheckPlanet(planet) && getCheckProdDate(prodDate) && getCheckSpeed(speed) && getCheckSize(crewSize);
    }

    public static boolean getCheckName(String name) {
        if (name != null) {
            if (name.length() > 50 || name.matches("\\s+") || name.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static boolean getCheckPlanet(String planet) {
        if (planet != null) {
            if(planet.length() > 50 || planet.matches("\\s+")) {
                return false;
            }
        }
        return true;
    }

    public static boolean getCheckProdDate(Date date) {
        if (date != null) {
            int numberOfYear = date.getYear() + 1900;
            if (numberOfYear < 2800 || numberOfYear > 3019) {
                return false;
            }
        }
        return true;
    }

    public static boolean getCheckSpeed(Double speed) {
        if (speed != null) {
            if(speed < 0.01 || speed > 0.99) {
                return false;
            }
        }
        return true;
    }

    public static boolean getCheckSize(Integer size) {
        if (size != null) {
            if (size < 1 || size > 9999) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIsNotNullData(Ship ship) {
        String name = ship.getName();
        String planet = ship.getPlanet();
        Date prodDate = ship.getProdDate();
        ShipType type = ship.getShipType();
        Integer crewSize = ship.getCrewSize();
        Double speed = ship.getSpeed();

        speed = UpdatingShip.roundSpeed(speed);
        ship.setSpeed(speed);

        if (name == null || name.isEmpty() || planet == null || prodDate.getTime() < 0 || type == null || crewSize == null || speed == null) {
            return  false;
        }
        return true;
    }
}
