package com.opendatatuscany.api.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.opendatatuscany.api.RestaurantsRequest.RistorantiBottegheXAll;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RestaurantController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/importOpenData")
    public List<Restaurant> postImportOpenData(@Valid @RequestBody RistorantiBottegheXAll ristorantiebottegheXall) {
        List<Restaurant> restaurantList = ristorantiebottegheXall.getRistorantiebottegheXall();
        List<Restaurant> validRestaurants = restaurantList.stream()
                .filter(restaurant -> restaurant != null)
                .collect(Collectors.toList());
        return restaurantRepository.saveAll(validRestaurants);
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable(value = "id") Long restaurantId)
            throws ResponseStatusException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Restaurant not found for id: " + restaurantId));
        return ResponseEntity.ok().body(restaurant);
    }

    @GetMapping(path = "/restaurants", params = { "provincia" })
    public List<Restaurant> getRestaurantsByProvince(@RequestParam String provincia) {
        return restaurantRepository.findByProvinciaIgnoreCase(provincia);
    }

    @GetMapping(path = "/restaurants", params = { "provincia", "userLat", "userLon" })
    public List<Restaurant> getRestaurantsByProvinceAndOrderByDistance(@RequestParam String provincia,
            @RequestParam double userLat, @RequestParam double userLon) {
        List<Restaurant> filteredRestaurants = restaurantRepository.findByProvinciaIgnoreCase(provincia);

        filteredRestaurants.forEach(restaurant -> restaurant.calculateDistance(userLat, userLon));
        return filteredRestaurants.stream()
                .sorted((r1, r2) -> Double.compare(r1.getDistance(), r2.getDistance()))
                .collect(Collectors.toList());
    }

    @PostMapping("/restaurants")
    public Restaurant postRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> putRestaurant(@PathVariable(value = "id") Long restaurantId,
            @Valid @RequestBody Restaurant newRestaurant) throws ResponseStatusException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Restaurant not found for id: " + restaurantId));
        restaurant.setAllColums(newRestaurant);
        final Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return ResponseEntity.ok().body(updatedRestaurant);
    }

    @DeleteMapping("/restaurants/{id}")
    public void deleteRestaurant(@PathVariable(value = "id") Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
