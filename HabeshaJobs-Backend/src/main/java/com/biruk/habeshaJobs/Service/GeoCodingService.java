package com.biruk.habeshaJobs.Service;

import com.biruk.habeshaJobs.Model.Common.Address;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

/*
    * The purpose of this class is to convert the users address into latitude and longitude coordinates.
    * I order to do this, we need to use geocoding APIs such as
        1. OpenStreetMap Nominatim (Free, Limited for Heavy Use)
        2. Google Maps Geocoding API (Highly Scalable, Paid)
        3. PositionStack (Free Tier + Paid Plans)
        4. LocationIQ (Free Tier + Paid Plans)
        5. Mapbox Geocoding API (Free Tier + Paid Plans)

  * For the time being, I will use the OpenStreetMap Nominatim API. And later on I will use one of scalable APIs.
 */
@Service
public class GeoCodingService {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
// it is wrapped with Optional in case geocode fails or returns no results.
    public Optional <double []> geoCodeAddress (Address address) {

        //Creates a restTemplate instance to send HTTP requests. This is used to communicate with the Nominatim API.
        RestTemplate restTemplate = new RestTemplate();

        // Construct the address string from the Address object. It uses only non-null fields. so we have to check if the field is null or not.
        StringBuilder addressBuilder = new StringBuilder ();

        if (address.getStreetAddress() != null && !address.getStreetAddress().isBlank()) {
            addressBuilder.append(address.getStreetAddress()).append(", ");
        }
        if (address.getCity() != null && !address.getCity().isBlank()) {
            addressBuilder.append(address.getCity()).append(", ");
        }
        if (address.getState() != null && !address.getState().isBlank()){
            addressBuilder.append(address.getState()).append(", ");
        }
        if (address.getCountry() != null && !address.getCountry().isBlank()){
            addressBuilder.append(address.getCountry()).append(", ");
        }
        if (address.getZipCode() != null && !address.getZipCode().isBlank()) {
            addressBuilder.append(address.getZipCode());
        }

        String fullAddress = addressBuilder.toString().trim();

        /*
        * builds the full URL for the API request using UriComponentsBuilder.
        * q = the query parameter for the address
        * format = json tells Nominatim to return JSON data
        * limit = 1 tells it to return only the first matching result.
        * eg. https://nominatim.openstreetmap.org/search?q=123 Main St, New York, NY&format=json&limit=1
        */
        URI uri = UriComponentsBuilder.fromUriString(NOMINATIM_API_URL)
                .queryParam("q", fullAddress)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .build()
                .toUri();

        try {
            // Executes the HTTP GET request and expects an array of NominatimResponse
            NominatimResponse [] response = restTemplate.getForObject(uri, NominatimResponse[].class);

            System.out.println("Raw API response: " + Arrays.toString(response));

            // if the API returns a result, parse the latitude and longitude from the response.
                if (response != null && response.length > 0){

                    String latString = response[0].lat;
                    String lonString = response[0].lon;

                    if (latString != null && lonString != null){

                        double lat = Double.parseDouble(latString.trim());      // latitude is a string in the response. so we have to parse it or change it to double.
                        double lon = Double.parseDouble(lonString.trim());     // longitude is a string in the response. so we have to parse it or change it to double.
                        return Optional.of(new double[]{lat, lon});                 // return the latitude and longitude as an array. Optional.of is used to wrap the result in an Optional object.

                    }else {
                        System.err.println("Latitude or longitude is null in the API response.");
                    }

                }else {
                    System.err.println("Empty or null response from geocoding API.");
                }
            }catch (Exception e) {
            System.out.println("Geocoding failed for address: " + fullAddress);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /*
    * Inner class to represent the response from Nominatim API
    * This class is used to deserialize (Json -> java object) the JSON response from the Nominatim API.
    * It contains only the fields we are interested in: latitude and longitude.
           Here is what's happening:
    * When Spring (via RestTemplate) gets the response, it uses Jackson to map the JSON into Java objects.
    * Jackson looks at the JSON keys ("lat" and "lon") and matches them to the public fields in your class.
    * Since our class has public String lat; and public String lon;, the mapping succeeds.
    *
    * It’s important that the field names exactly match the JSON keys, and that they are public or have getters/setters — otherwise, Jackson can’t access them.

     */
    private static class NominatimResponse {
        public String lat;
        public String lon;
    }
}


