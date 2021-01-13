package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepository;

    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }


    @Override
    public List<Ship> getAll(String name, String planet, ShipType type, Long after, Long before,
                             Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minSize,
                             Integer maxSize, Double minRating, Double maxRating) {

        List<Ship> ships = shipRepository.findAll();

        List<Ship> filteredShips = ships.stream()
                .filter(n -> name == null || n.getName().contains(name))
                .filter(n -> planet == null || n.getPlanet().contains(planet))
                .filter(n -> type == null || n.getShipType().equals(type))
                .filter(n -> after == null || n.getProdDate().getTime() >= after)
                .filter(n -> before == null || n.getProdDate().getTime() < before)
                .filter(n -> isUsed == null || n.getUsed() == isUsed)
                .filter(n -> minSpeed == null || n.getSpeed() >= minSpeed)
                .filter(n -> maxSpeed == null || n.getSpeed() < maxSpeed)
                .filter(n -> minSize == null || n.getCrewSize() >= minSize)
                .filter(n -> maxSize == null || n.getCrewSize() < maxSize)
                .filter(n -> minRating == null || n.getRating() >= minRating)
                .filter(n -> maxRating == null || n.getRating() < maxRating)
                .collect(Collectors.toList());

        return filteredShips;
    }


    @Override
    public List<Ship> sort(List<Ship> ships, ShipOrder order, Integer pageNumber, Integer pageSize) {

        class ShipComparator implements Comparator<Ship> {

            public int compare(Ship a, Ship b) {

                if (order == null) {
                    return a.getId().compareTo(b.getId());
                } else {
                    switch (order) {
                        case DATE:
                            return a.getProdDate().compareTo(b.getProdDate());
                        case SPEED:
                            return a.getSpeed().compareTo(b.getSpeed());
                        case RATING:
                            return a.getRating().compareTo(b.getRating());
                        default:
                            return a.getId().compareTo(b.getId());
                    }
                }
            }
        }

        int page = pageNumber == null  ? 0 : pageNumber;
        int size = pageSize == null ? 3 : pageSize > ships.size() ? ships.size() : pageSize;

        List<Ship> sortedShips = ships.stream()
                .sorted(new ShipComparator())
                .skip(page * size)
                .limit(size)
                .collect(Collectors.toList());

        return sortedShips;
    }

    @Override
    public Ship getById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        if (shipRepository.existsById(id))
            shipRepository.deleteById(id);
    }

    @Override
    public Ship create(Ship ship) {

        if (CheckNewShip.checkIsNotNullData(ship) && CheckNewShip.checkData(ship)) {
            if (ship.getUsed() == null) {
                ship.setUsed(false);
            }

            UpdatingShip.updateRating(ship);
            return shipRepository.save(ship);
        } else {
            return null;
        }
    }

    @Override
    public Ship update(Long id, Ship ship) {
        if (!CheckNewShip.checkData(ship)) {
            return null;
        }

        Ship currentShip = getById(id);
        Ship updatedShip = UpdatingShip.updateData(currentShip, ship);
        return shipRepository.save(updatedShip);
    }
}
