import java.util.ArrayList;

public class Location {
    String name;
    String region;
    double latitude;
    double longitude;
    double distanceFromOriginMiles = -1;
    double distanceFromOriginKM = -1;
    int population;
    String location;
    String zipcode;
    int housingUnits;

    public int getHousingUnits() {
        return housingUnits;
    }

    public void setHousingUnits(int housingUnits) {
        this.housingUnits = housingUnits;
    }

    ArrayList<String> zips = new ArrayList<>();

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location1 = (Location) o;

        return location.equals(location1.location);
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }

    public double getDistanceFromOriginKM() {
        return distanceFromOriginKM;
    }

    public void setDistanceFromOriginKM(double distanceFromOriginKM) {
        this.distanceFromOriginKM = distanceFromOriginKM;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distanceFromOriginMiles=" + distanceFromOriginMiles + " Miles" +

                ", population=" + population +
                ", location='" + location + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Location(String name, String region, double latitude, double longitude, int population, String location, String zip, double distanceFromOriginMiles, double distanceFromOriginKM) {
        this.name = name;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distanceFromOriginMiles = distanceFromOriginMiles;
        this.population = population;
        this.location = location;
        this.zipcode = zip;
        this.distanceFromOriginKM = distanceFromOriginKM;
        zips.add(zip);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistanceFromOriginMiles() {
        return distanceFromOriginMiles;
    }

    public void setDistanceFromOriginMiles(double distanceFromOriginMiles) {
        this.distanceFromOriginMiles = distanceFromOriginMiles;
    }

    public void addToPopulation(int population) {
        this.population += population;
    }

    public void addToZipcode(String zip) {
        if (!zips.contains(zip)) {
            zips.add(zip);
        }
    }

    public String formatZipCodeList() {
        String zipCodeList = "";
        zipCodeList += "(";
        for (int i = 0; i <= zips.size() - 1; i++) {
            if (zips.size() == 1) {
                zipCodeList += "\"" + zips.get(0) + "\"";
            } else if (i < zips.size() - 1) {
                zipCodeList += "\"" + zips.get(i) + "\",";
            } else {
                zipCodeList += "\"" + zips.get(i) + "\"";
            }
        }
        zipCodeList += ")";
        return zipCodeList;
    }
}
