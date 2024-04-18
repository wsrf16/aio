package com.aio.portable.swiss.suite.algorithm.geo;

import java.io.Serializable;

public class BoundingBox implements Serializable {
    private static final long serialVersionUID = -7145192134410261076L;
    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;

    /**
     * create a bounding box defined by two coordinates
     */
    public BoundingBox(GeoPoint p1, GeoPoint p2) {
        this(p1.getLatitude(), p2.getLatitude(), p1.getLongitude(), p2.getLongitude());
    }

    public BoundingBox(double y1, double y2, double x1, double x2) {
        minLon = Math.min(x1, x2);
        maxLon = Math.max(x1, x2);
        minLat = Math.min(y1, y2);
        maxLat = Math.max(y1, y2);
    }

    public BoundingBox(BoundingBox that) {
        this(that.minLat, that.maxLat, that.minLon, that.maxLon);
    }

    public GeoPoint getUpperLeft() {
        return new GeoPoint(maxLat, minLon);
    }

    public GeoPoint getLowerRight() {
        return new GeoPoint(minLat, maxLon);
    }

    public double getLatitudeSize() {
        return maxLat - minLat;
    }

    public double getLongitudeSize() {
        return maxLon - minLon;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BoundingBox) {
            BoundingBox that = (BoundingBox) obj;
            return minLat == that.minLat && minLon == that.minLon && maxLat == that.maxLat && maxLon == that.maxLon;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + hashCode(minLat);
        result = 37 * result + hashCode(maxLat);
        result = 37 * result + hashCode(minLon);
        result = 37 * result + hashCode(maxLon);
        return result;
    }

    private static int hashCode(double x) {
        long f = Double.doubleToLongBits(x);
        return (int) (f ^ (f >>> 32));
    }

    public boolean contains(GeoPoint point) {
        return (point.getLatitude() >= minLat) && (point.getLongitude() >= minLon) && (point.getLatitude() <= maxLat)
                && (point.getLongitude() <= maxLon);
    }

    public boolean intersects(BoundingBox other) {
        return !(other.minLon > maxLon || other.maxLon < minLon || other.minLat > maxLat || other.maxLat < minLat);
    }

    @Override
    public String toString() {
        return getUpperLeft() + " -> " + getLowerRight();
    }

    public GeoPoint getCenterPoint() {
        double centerLatitude = (minLat + maxLat) / 2;
        double centerLongitude = (minLon + maxLon) / 2;
        return new GeoPoint(centerLatitude, centerLongitude);
    }

    public void expandToInclude(BoundingBox other) {
        if (other.minLon < minLon) {
            minLon = other.minLon;
        }
        if (other.maxLon > maxLon) {
            maxLon = other.maxLon;
        }
        if (other.minLat < minLat) {
            minLat = other.minLat;
        }
        if (other.maxLat > maxLat) {
            maxLat = other.maxLat;
        }
    }

    public double getMinLon() {
        return minLon;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMaxLon() {
        return maxLon;
    }
}
