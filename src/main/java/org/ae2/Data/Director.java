package org.ae2.Data;

import java.util.HashSet;

public class Director extends Stuff {
    public Director(String name, int ID, String password, int age) {
        super(name, ID, password, age);
    }

    public Director() {
        super();
    }

    public Requirement formulateRequirement(String age, String course) {
        Requirement requirement = new Requirement();
        age = age.replaceAll("\\s+", "");
        course = course.replaceAll("\\s+", "");

        if (!course.isEmpty()) {
            requirement.setCourse(course);
        }

        if (age.contains(",")) { // 1,4,2,6,7
            HashSet<Integer> ageSet = requirement.getAgeSet();
            ageSet.clear();

            // Add all ages to Set.
            String[] stringAges = age.split(",");
            for (String stringAge : stringAges) {
                ageSet.add(Integer.parseInt(stringAge));
            }
        } else if (age.contains("-")) { // 20-40
            HashSet<Integer> ageSet = requirement.getAgeSet();
            ageSet.clear();
            String[] stringAges = age.split("-");

            if (stringAges.length > 2) {
                throw new IllegalArgumentException("Too many ages, it should look like '20-40'");
            }

            // Set max and min age.
            int firstAge = Integer.parseInt(stringAges[0]);
            int secondAge = Integer.parseInt(stringAges[1]);
            requirement.setMaxAge(Math.max(firstAge, secondAge));
            requirement.setMinAge(Math.min(firstAge, secondAge));
        } else {
            requirement.setMinAge(Integer.parseInt(age));
            requirement.setMaxAge(Integer.parseInt(age));
        }

        requirement.setCreatorID(this.getID());
        return requirement;
    }
}
