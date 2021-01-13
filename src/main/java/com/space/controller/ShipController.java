package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/rest")
public class ShipController {
    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping(value = "/ships")
    public ResponseEntity<List<Ship>> getAllShips(@RequestParam(name = "name", required = false) String name,
                                                  @RequestParam(name = "planet", required = false) String planet,
                                                  @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                  @RequestParam(name = "after", required = false) Long after,
                                                  @RequestParam(name = "before", required = false) Long before,
                                                  @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                  @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                  @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                  @RequestParam(name = "minCrewSize", required = false) Integer minSize,
                                                  @RequestParam(name = "maxCrewSize", required = false) Integer maxSize,
                                                  @RequestParam(name = "minRating", required = false) Double minRating,
                                                  @RequestParam(name = "maxRating", required = false) Double maxRating,
                                                  @RequestParam(name = "order", required = false) ShipOrder order,
                                                  @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(name = "pageSize", required = false) Integer pageSize) {

        List<Ship> ships = shipService.getAll(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minSize,
                maxSize, minRating, maxRating);

        ships = shipService.sort(ships, order, pageNumber, pageSize);

        return !ships.isEmpty() ? new ResponseEntity<>(ships, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> getShipById(@PathVariable("id") Long shipId) {
        if (shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = shipService.getById(shipId);
        return ship != null ? new ResponseEntity<>(ship, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> updateShipById(@PathVariable("id") Long shipId, @RequestBody Ship ship) {
        if (shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Ship updateShip = shipService.getById(shipId);
        if (updateShip == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updateShip = shipService.update(shipId, ship);
        return updateShip != null ? new ResponseEntity<>(updateShip, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/ships/{id}")
    public ResponseEntity<Ship> deleteShipById(@PathVariable("id") Long shipId) {
        if (shipId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship ship = shipService.getById(shipId);

        if (ship == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        shipService.delete(shipId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/ships/count")
    public ResponseEntity<Integer> getCountShips(@RequestParam(name = "name", required = false) String name,
                                                 @RequestParam(name = "planet", required = false) String planet,
                                                 @RequestParam(name = "shipType", required = false) ShipType shipType,
                                                 @RequestParam(name = "after", required = false) Long after,
                                                 @RequestParam(name = "before", required = false) Long before,
                                                 @RequestParam(name = "isUsed", required = false) Boolean isUsed,
                                                 @RequestParam(name = "minSpeed", required = false) Double minSpeed,
                                                 @RequestParam(name = "maxSpeed", required = false) Double maxSpeed,
                                                 @RequestParam(name = "minCrewSize", required = false) Integer minSize,
                                                 @RequestParam(name = "maxCrewSize", required = false) Integer maxSize,
                                                 @RequestParam(name = "minRating", required = false) Double minRating,
                                                 @RequestParam(name = "maxRating", required = false) Double maxRating) {

        List<Ship> ships = shipService.getAll(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minSize,
                maxSize, minRating, maxRating);

        return !ships.isEmpty() && ships != null ? new ResponseEntity<>(ships.size(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/ships")
    public ResponseEntity<Ship> createNewShip(@RequestBody Ship ship) {


        Ship newShip = shipService.create(ship);
        return newShip != null ? new ResponseEntity<>(newShip, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
