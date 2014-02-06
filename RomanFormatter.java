import java.util.ArrayList;
import java.util.List;

/**
 * Change Decimal number to Roman number and the other way around.
 * 
 * @author Adam Dziedzic
 */
public class RomanFormatter {

	/** Pairs of decimal numbers and Roman numerals. */
	private List<RomanDecimal> romansDecimals;

	/**
	 * Set Roman numerals and their decimal number equivalents.
	 */
	public RomanFormatter() {
		romansDecimals = new ArrayList<RomanDecimal>();
		romansDecimals
				.add(new RomanDecimal("M", 1000, Integer.MAX_VALUE / 1000));
		romansDecimals.add(new RomanDecimal("CM", 900, 1));
		romansDecimals.add(new RomanDecimal("D", 500, 1));
		romansDecimals.add(new RomanDecimal("CD", 400, 1));
		romansDecimals.add(new RomanDecimal("C", 100, 3));
		romansDecimals.add(new RomanDecimal("XC", 90, 1));
		romansDecimals.add(new RomanDecimal("L", 50, 1));
		romansDecimals.add(new RomanDecimal("XL", 40, 1));
		romansDecimals.add(new RomanDecimal("X", 10, 3));
		romansDecimals.add(new RomanDecimal("IX", 9, 1));
		romansDecimals.add(new RomanDecimal("V", 5, 1));
		romansDecimals.add(new RomanDecimal("IV", 4, 1));
		romansDecimals.add(new RomanDecimal("I", 1, 3));
	}

	/**
	 * To store pairs: Roman numerals and their decimal numbers equivalents.
	 * 
	 * @author Adam Dziedzic
	 * 
	 */
	private class RomanDecimal {

		/** Roman numeral equivalent of the decimal number */
		private String romanNumeral;

		/** Decimal number equivalent of the Roman numeral. */
		private int decimalNumber;

		/* Number of occurrences of the literal. */
		private int numberOfOccurrences;

		/**
		 * Create a pair consisting of Roman numeral and its decimal number
		 * equivalent.
		 * 
		 * @param romanNumeral
		 *            Roman numeral
		 * @param decimalNumber
		 *            decimal number
		 */
		public RomanDecimal(String romanNumeral, int decimalNumber,
				int numberOfOccurrences) {
			this.romanNumeral = romanNumeral;
			this.decimalNumber = decimalNumber;
			this.numberOfOccurrences = numberOfOccurrences;
		}

		/**
		 * 
		 * @return Roman numeral
		 */
		public String getRomanNumeral() {
			return romanNumeral;
		}

		/**
		 * 
		 * @return decimal number
		 */
		public int getDecimalNumber() {
			return decimalNumber;
		}

		/**
		 * 
		 * @return number of maximal occurrences of the Roman numeral
		 */
		public int getNumberOfOccurrences() {
			return numberOfOccurrences;
		}

	}

	// 4656 - "MMMMDCLVI"
	// CM
	// public String toRomanNumerals(int number)
	// public int fromRomanNumber(String romanNumber)
	//
	// 1 - I
	// 2- II
	// 3 - III
	// 4 - IV
	// 5 - V
	// 6 - VI
	// 7 - VII
	// 8 - VIII
	// 9 - IX
	// 10 - X
	// 11 - 10 + 1 = XI
	// ...
	// 20 - 10 + 10 = XX
	// 30 - XXX
	// 40 - XL
	// 50 - L
	// 100 - C
	// 500 - D
	// 900 - CM
	// 1000 - M
	// 2000 - MM
	// 4000 - MMMM

	/*
	 * 4656 4000/1000 = 4 656 / 500 = 1 156 / 100 = 1 56 / 50 = 1 6 / 10 = 0 6 /
	 * 5 = 1 1 / 1 = 1
	 * 
	 * 4 - IV 4 / 5 = 0
	 * 
	 * 9 - IX
	 * 
	 * 1950 1950 / 1000 = 1 950 / 900 = 1
	 */

	/**
	 * Change decimal number to Roman number.
	 * 
	 * @param decimalNumber
	 *            decimal number
	 * @return Roman number
	 */
	public String toRomanNumber(int decimalNumber) {
		StringBuffer result = new StringBuffer();
		for (RomanDecimal romanDecimal : romansDecimals) {
			int numberOfLetters = decimalNumber
					/ romanDecimal.getDecimalNumber();
			decimalNumber = decimalNumber
					- (numberOfLetters * romanDecimal.getDecimalNumber());
			StringBuffer romanLetters = new StringBuffer();
			for (int i = 0; i < numberOfLetters; ++i) {
				romanLetters.append(romanDecimal.getRomanNumeral());
			}
			result.append(romanLetters.toString());
		}
		return result.toString();
	}

	/**
	 * Change Roman number to decimal number.
	 * 
	 * @param romanNumber
	 *            Roman number
	 * @return decimal number
	 */
	public int fromRomanNumber(String romanNumber) {
		int result = 0;
		for (RomanDecimal romanDecimal : romansDecimals) {
			int romanDecimalLength = romanDecimal.getRomanNumeral().length();
			int occurrencesCounter = 0;
			while (occurrencesCounter < romanDecimal.getNumberOfOccurrences()
					&& romanDecimalLength <= romanNumber.length()
					&& romanDecimal.getRomanNumeral().equals(
							romanNumber.substring(0, romanDecimalLength))) {
				++occurrencesCounter;
				result += romanDecimal.getDecimalNumber();
				romanNumber = romanNumber.substring(romanDecimal
						.getRomanNumeral().length());
				if (romanDecimalLength > 1) {
					break;
				}
			}
		}
		if (romanNumber.length() > 0) {
			throw new IllegalArgumentException("Illegal Roman number!");
		}
		return result;
	}

	// M
	// public int fromRomanNumber(String romanNumber) {
	// Map<String, Integer> mapRomansInt = new HashMap<String, Integer>();
	// mapRomansInt.put("M", 1000); // order by values - descending
	// int sum = 0;
	// for (Entry<String, Integer> entry : mapRomansInt.entrySet()) {
	// // String sub = romanNumber.findSubstring(entry.getKey()); // get
	// // substring of Ms
	// // int size = sub.length();
	// // sum = size * entry.getValue();
	// }
	// }

	// MMMDCLVI - 1000/500/100/50/5/1
	// substring(0,index) - when M appears

	/**
	 * @param args
	 *            arguments for the program
	 */
	public static void main(String[] args) {
		RomanFormatter romanFormatter = new RomanFormatter();

		System.out.println(romanFormatter.toRomanNumber(4));
		System.out.println(romanFormatter.toRomanNumber(45));
		System.out.println(romanFormatter.toRomanNumber(98));
		System.out.println(romanFormatter.toRomanNumber(182));
		System.out.println(romanFormatter.toRomanNumber(3456));

		System.out.println(romanFormatter.fromRomanNumber("XLV"));
		System.out.println(romanFormatter.fromRomanNumber("I"));
		System.out.println(romanFormatter.fromRomanNumber("II"));

		try {
			System.out.println(romanFormatter.fromRomanNumber("IIII"));
		} catch (IllegalArgumentException e) {
			System.out.println("Excpetion catched!");
		}

		for (int i = 1; i < 5000; ++i) {
			String romanNumberString = romanFormatter.toRomanNumber(i);
			int romanNumber = 0;
			try {
				romanNumber = romanFormatter.fromRomanNumber(romanNumberString);
			} catch (IllegalArgumentException e) {
				System.out.println("Error for i: " + i + " Roman: "
						+ romanNumberString + " Decimal: " + romanNumber);
				e.printStackTrace();
				System.exit(1);
			}
			if (romanNumber != i) {
				System.out.println("Error for i: " + i + " Roman: "
						+ romanNumberString + " Decimal: " + romanNumber);
			}
		}
	}

}