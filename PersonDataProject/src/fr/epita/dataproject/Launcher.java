package fr.epita.dataproject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Launcher {

	private static final int VALUE_FOR_FEMALE = 1;
	private static final int VALUE_FOR_MALE = 0;

	public static void main(String[] args) throws IOException {
		File file = new File("data.csv");
		// TODO read the lines from the file
		List<String> lines = Files.readAllLines(file.toPath());
		// transform each line into a person
		List<Person> persons = new ArrayList<>();
		lines.remove(0);
		for (String line : lines) {

			try {
				// "Name", "Sex", "Age", "Height (in)", "Weight (lbs)"
				String[] fields = line.split(",");
				String name = fields[0].trim().replaceAll("\"", "");
				String sex = fields[1].trim().replaceAll("\"", "");
				Integer age = Integer.valueOf(fields[2].trim());
				Integer height = Integer.valueOf(fields[3].trim());
				Integer weight = Integer.valueOf(fields[4].trim());

				Person person = new Person(name, age, weight, height, sex);

				persons.add(person);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println(persons.size());
		System.out.println(persons);

		
		List<Person> persons2 =from2dMatrix(to2dMatrix(persons));
		
		//persons2 and persons should be the same aside of the name
		
		System.out.println(persons2);
		
		Collections.sort(persons, new Comparator<Person>() {
			
			public int compare(Person p1, Person p2) {
				return p1.getAge()-p2.getAge();
			};
		});
		//This is the sam as...
		Collections.sort(persons, (p1,p2) -> p1.getAge()-p2.getAge());
		List<int[]> countByAges = new ArrayList<>();
		int[] currentCount = new int[2];
		for (Person person : persons) {
			if (currentCount[0] != 0 && currentCount[0] != person.getAge()) {
				countByAges.add(currentCount);
				currentCount = new int[2];
				currentCount[0] = person.getAge();
				currentCount[1] = 1;
			}else {
				currentCount[1]++;
			}
		}
		
		//...this one.
		Map<Integer, Integer> ageDistributionMap = new LinkedHashMap<>();
		for (Person person : persons) {
			Integer count = ageDistributionMap.get(person.getAge());
			if (count == null) {
				count = 1;
			}else {
				count++;
			}
			ageDistributionMap.put(person.getAge(), count);
		}
		
		System.out.println(ageDistributionMap);
		
		persons
			.stream()
			.mapToInt(Person::getHeight);
		
		Map<Integer,Long> map = persons.stream()
				.collect(Collectors.groupingBy(Person::getAge,Collectors.counting()));
		SortedMap<Integer, Long> sortedMap = new TreeMap<>();
		sortedMap.putAll(map);
		
		System.out.println("sortedMap :"+ sortedMap);
		
		System.out.println("age distribution");
		System.out.println(map);
		
		
		//computing histogram
		for (Entry<Integer,Integer> entry : ageDistributionMap.entrySet()) {
			String level = "";
			for (int i = 0; i < entry.getValue(); i++) {
				level+= "=";
			}
			System.out.println(entry.getKey() +" : " +level);
		}
		
		
	}
	
	
	private static double[][] to2dMatrix(List<Person> persons) {
		int size = persons.size();
		double[][] results = new double[size][4];
		for (int i = 0; i < size; i++) {
			double[] ds = new double[4];
			double encodedSex = -1;
			Person currentPerson = persons.get(i);
			String currentPersonSex = currentPerson.getSex();
			if ("M".equals(currentPersonSex)) {
				encodedSex = VALUE_FOR_MALE;
			}else if ("F".equals(currentPersonSex)) {
				encodedSex = VALUE_FOR_FEMALE;
			}
			ds[0]= encodedSex;
			ds[1]= currentPerson.getAge();
			ds[2]= currentPerson.getHeight();
			ds[3]= currentPerson.getAge();
			results[i] = ds;
		}
		
		return results;
	}
	private static List<Person> from2dMatrix(double[][] matrix) {
		List<Person> persons = new ArrayList<>();
	
		for (double[] ds : matrix) {
			
			
			String decodedSex = "";
			if (VALUE_FOR_MALE == ds[0]) {
				decodedSex = "M";
			}else if (VALUE_FOR_FEMALE == ds[0]) {
				decodedSex = "F";
			}
			

			Person currentPerson = new Person("", Double.valueOf(ds[1]).intValue(), 
					Double.valueOf(ds[2]).intValue(), 
					Double.valueOf(ds[3]).intValue(), 
					decodedSex );
			persons.add(currentPerson);
		}
		
		return persons;
	}
	
}
