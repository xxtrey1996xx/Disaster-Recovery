import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String host = args[0];
        String user = args[1];
        String password = args[2];
        ArrayList<Location> locations = new ArrayList<>();
        Location primaryLocation;
        double distanceNeeded;

        Connection conn;
        ResultSet rs1;
        ResultSet rs2;
        Statement st;

        try {
            Scanner input = new Scanner(System.in);
            conn = DriverManager.getConnection(host, user, password);
            String queryString;
            st = (Statement) conn.createStatement();
            //Getting Details about primary location
            System.out.println("Please enter beginning ZipCode");
            queryString = "Select * from zips2 where zipcode = \"" + input.next() + "\" and locationtype = \"Primary\"";
            System.out.println("How far would you like to search?");
            distanceNeeded = input.nextDouble();
            input.close();
            //Setting information about the primary location
            primaryLocation = new Location(null, null, -500, -500, -1, null, null, -1, -1);
            rs1 = st.executeQuery(queryString);
            while (rs1.next()) {
                primaryLocation.setLatitude(rs1.getFloat("lat"));
                primaryLocation.setLongitude(rs1.getFloat("long"));
                primaryLocation.setName(rs1.getString("city"));
                primaryLocation.setRegion(rs1.getString("state"));
                primaryLocation.setPopulation(rs1.getInt("estimatedpopulation"));
                primaryLocation.setLocation(rs1.getString("location"));
                primaryLocation.setZipcode(rs1.getString("zipcode"));
                primaryLocation.setDistanceFromOriginMiles(0);
            }
            //Getting all data from database
            queryString = "Select * from zips2 where locationtype = \"Primary\"";
            rs2 = st.executeQuery(queryString);
            while (rs2.next()) {
                boolean wasFound = false;
                int foundAt = -1;
                //If location in radius, add it to ArrayList
                if (distance(rs2.getFloat("lat"), rs2.getFloat("long"), primaryLocation.getLatitude(), primaryLocation.getLongitude(), true) < distanceNeeded) {
                    Location tempLoc = new Location(rs2.getString("city"),
                            rs2.getString("state"),
                            rs2.getFloat("lat"),
                            rs2.getFloat("long"),
                            rs2.getInt("estimatedpopulation"),
                            rs2.getString("location"),
                            rs2.getString("zipcode"),
                            distance(rs2.getFloat("lat"),
                                    rs2.getFloat("long"),
                                    primaryLocation.getLatitude(),
                                    primaryLocation.getLongitude(), true),
                            distance(rs2.getFloat("lat"),
                                    rs2.getFloat("long"),
                                    primaryLocation.getLatitude(),
                                    primaryLocation.getLongitude(), false));
                    //If first item in Arraylist, add it.
                    if (locations.size() == 0) {
                        locations.add(tempLoc);
                    }
                    for (int i = 0; i <= locations.size() - 1; i++) {
                        //Checking to see if location is already in Arraylist
                        if (tempLoc.equals(locations.get(i))) {
                            locations.get(i).addToZipcode(tempLoc.getZipcode());
                            break;
                        } else if (i == locations.size() - 1) {
                            locations.add(tempLoc);
                        }

                    }
                }
            }
            System.out.printf("%20s | %5s | %10s | %13s | %5s | %5s\n", "City", "State", "Population", "Housing Units", "Miles", "KiloM");
            System.out.println("----------------------------------------------------------------------------");
            for (int i = 0; i <= locations.size() - 1; i++) {
                String zipsFormat = locations.get(i).formatZipCodeList();
                queryString = "select sum(zips2.estimatedpopulation) from zips2 where locationtype = \"Primary\" and zipcode in " + zipsFormat;

                ResultSet rs3;
                rs3 = st.executeQuery(queryString);
                while (rs3.next()) {
                    locations.get(i).setPopulation(rs3.getInt("sum(zips2.estimatedpopulation)"));
                }
                queryString = "select distinct sum(zips.housingunits) from zips where z_primary = \"Yes\" and zip_code in " + zipsFormat;
                rs3 = st.executeQuery(queryString);
                while (rs3.next()) {
                    locations.get(i).setHousingUnits(rs3.getInt("sum(zips.housingunits)"));
                }
                System.out.printf("%20s | %5s | %10d | %13d | %5f | %5f\n",
                        locations.get(i).getName(),
                        locations.get(i).getRegion(),
                        locations.get(i).getPopulation(),
                        locations.get(i).getHousingUnits(),
                        locations.get(i).getDistanceFromOriginMiles(),
                        locations.get(i).getDistanceFromOriginKM());
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2, boolean miles) {
        //Credit given to https://rosettacode.org/wiki/Haversine_formula
        double result = -1;
        final double R = 6372.8; // In kilometers
        double haversine;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        haversine = R * c;
        //My code starts here
        if (miles)
            result = toMiles(haversine);
        else
            result = haversine;
        return result;
    }

    public static double toMiles(double km) {
        double result;
        result = km * 0.621371;
        return result;
    }
}

