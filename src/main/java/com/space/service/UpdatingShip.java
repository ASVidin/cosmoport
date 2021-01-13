package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public final class UpdatingShip {

    public static Ship updateData(Ship oldShip, Ship updateShip) {
        String name = updateShip.getName();
        String planet = updateShip.getPlanet();
        Date prodDate = updateShip.getProdDate();
        ShipType type = updateShip.getShipType();
        Integer crewSize = updateShip.getCrewSize();

        Double speed = updateShip.getSpeed();
        speed = UpdatingShip.roundSpeed(speed);
        updateShip.setSpeed(speed);

        if (name != null && !name.isEmpty()) {
            oldShip.setName(name);
        }
        if (planet != null) {
            oldShip.setPlanet(planet);
        }
        if (prodDate != null) {
            oldShip.setProdDate(prodDate);
        }
        if (type != null) {
            oldShip.setShipType(type);
        }
        if (crewSize != null) {
            oldShip.setCrewSize(crewSize);
        }
        if (speed != null) {
            oldShip.setSpeed(speed);
        }

        updateRating(oldShip);

        return oldShip;
    }

    public static void updateRating(Ship ship) {

        double k = ship.getUsed() == false ? 1 : 0.5;
        BigDecimal rating = new BigDecimal((80 * ship.getSpeed() * k) / (3019 - (ship.getProdDate().getYear() + 1900) + 1));
        rating = rating.setScale(2, RoundingMode.HALF_EVEN);
        ship.setRating(rating.doubleValue());

    }

    public static Double roundSpeed(Double speed) {
        if (speed == null) {
            return speed;
        }
        BigDecimal roundSpeed = new BigDecimal(speed);
        roundSpeed = roundSpeed.setScale(2, RoundingMode.HALF_EVEN);
        return roundSpeed.doubleValue();
    }
}
