package com.biruk.habeshaJobs.Model.Common;

import com.biruk.habeshaJobs.Service.GeoCodingService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class GeoHelper {

    //geometryFactory is from the JTS (Java topology suite) library.
    //this object is used to create geometry objects like points, lines, and polygons.
    private final GeometryFactory geometryFactory = new GeometryFactory();

    private final GeoCodingService geoCodingService;


    @Autowired
    public GeoHelper (GeoCodingService geoCodingService){
        this.geoCodingService = geoCodingService;
    }

    // this method creates a Point object (location) from an address object
    public Point createPointFromAddress(Address address) {

        Optional <double []> coordinate = geoCodingService.geoCodeAddress(address);

        if (coordinate.isPresent()){
            double [] coords = coordinate.get();
            return geometryFactory.createPoint(new Coordinate(coords[1], coords[0])); // Note: X = longitude, Y = latitude
        }else {
            throw new RuntimeException("Failed to geocode address: " + address);
        }
    }
}
