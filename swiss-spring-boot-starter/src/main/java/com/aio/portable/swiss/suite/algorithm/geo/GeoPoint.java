package com.aio.portable.swiss.suite.algorithm.geo;

import java.io.Serializable;

public class GeoPoint implements Serializable {
    private static final long serialVersionUID = 7457963026513014856L;
    private final double longitude;
    private final double latitude;

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        if (Math.abs(latitude) > 90.0D || Math.abs(longitude) > 180.0D) {
            throw new IllegalArgumentException("The supplied coordinates " + this + " are out of range.");
        }
    }

    public GeoPoint(GeoPoint other) {
        this(other.latitude, other.longitude);
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String toString() {
        return String.format("(" + this.latitude + "," + this.longitude + ")");
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GeoPoint)) {
            return false;
        } else {
            GeoPoint other = (GeoPoint)obj;
            return this.latitude == other.latitude && this.longitude == other.longitude;
        }
    }

    public int hashCode() {
        int result = 42;
        long latBits = Double.doubleToLongBits(this.latitude);
        long lonBits = Double.doubleToLongBits(this.longitude);
        result = 31 * result + (int)(latBits ^ latBits >>> 32);
        result = 31 * result + (int)(lonBits ^ lonBits >>> 32);
        return result;
    }
}

