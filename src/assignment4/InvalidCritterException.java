/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Aaron Babber
 * aab3456
 * 16480
 * Enrique Perez-Osborne
 * ehp355
 * 16465
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4;

public class InvalidCritterException extends Exception {
	String offending_class;

	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}

	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
