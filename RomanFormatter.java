import java.util.ArrayList;
import java.util.List;

/**
 * Change Decimal number to Roman number and the other way around.
 * 
 * @author Adam Dziedzic
 */
public class RomanFormatter {

    /** Pairs of decimal numbers and Roman numerals. */
    private static List<RomanDecimal> romansDecimals;

    /**
     * Set Roman numerals and their decimal number equivalents.
     */
    static {
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
    private static class RomanDecimal {

        /** Roman numeral equivalent of the decimal number */
        private String romanNumeral;

        /** Decimal number equivalent of the Roman numeral. */
        private int decimalNumber;

        /* Number of max occurrences of a Roman literal. */
        private int maxOccurrences;

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
            this.maxOccurrences = numberOfOccurrences;
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
         * @return number of max occurrences of the Roman numeral
         */
        public int getMaxOccurrences() {
            return maxOccurrences;
        }

    }

    /**
     * Change decimal number to Roman number.
     * 
     * @param decimalNumber
     *            decimal number
     * @return Roman number
     */
    public static String toRomanNumber(int decimalNumber) {
        StringBuffer romanNumber = new StringBuffer(); // result
        for (RomanDecimal romanDecimal : romansDecimals) {
            int numberOfLetters = decimalNumber
                    / romanDecimal.getDecimalNumber();
            decimalNumber = decimalNumber
                    - (numberOfLetters * romanDecimal.getDecimalNumber());
            for (int i = 0; i < numberOfLetters; ++i) {
                romanNumber.append(romanDecimal.getRomanNumeral());
            }
        }
        return romanNumber.toString();
    }

    /**
     * Change Roman number to decimal number.
     * 
     * @param romanNumber
     *            Roman number
     * @return decimal number
     */
    public static int fromRomanNumber(String romanNumber) {
        int decimalNumber = 0; // final result to be returned
        int substringFirstIndex = 0; // Roman number substring first index
        for (RomanDecimal romanDecimal : romansDecimals) {
            int romanDecimalLength = romanDecimal.getRomanNumeral().length();
            int occurrencesCounter = 0; // Roman numeral occurrence counter
            while (occurrencesCounter < romanDecimal.getMaxOccurrences()
                    && romanDecimalLength <= (romanNumber.length() - substringFirstIndex)
                    && romanDecimal.getRomanNumeral().equals(
                            romanNumber.substring(substringFirstIndex,
                                    substringFirstIndex + romanDecimalLength))) {
                ++occurrencesCounter;
                substringFirstIndex += romanDecimalLength;
                decimalNumber += romanDecimal.getDecimalNumber();
            }
        }
        if (romanNumber.length() - substringFirstIndex > 0) {
            throw new IllegalArgumentException("Illegal Roman number!");
        }
        return decimalNumber;
    }

    /**
     * @param args
     *            arguments for the program
     */
    public static void main(String[] args) {

        System.out.println(RomanFormatter.toRomanNumber(4));
        System.out.println(RomanFormatter.toRomanNumber(45));
        System.out.println(RomanFormatter.toRomanNumber(98));
        System.out.println(RomanFormatter.toRomanNumber(182));
        System.out.println(RomanFormatter.toRomanNumber(3456));
        String maxValue = RomanFormatter.toRomanNumber(Integer.MAX_VALUE);

        System.out.println(RomanFormatter.fromRomanNumber("MMDCXLV"));
        System.out.println(RomanFormatter.fromRomanNumber("I"));
        System.out.println(RomanFormatter.fromRomanNumber("II"));
        if (Integer.MAX_VALUE != RomanFormatter.fromRomanNumber(maxValue)) {
            System.out.println("Error - cannot handle max value!");
        }

        try {
            System.out.println(RomanFormatter.fromRomanNumber("IIII"));
        } catch (IllegalArgumentException e) {
            System.out.println("Excpetion catched!");
        }

        for (int i = 1; i < 10000; ++i) {
            String romanNumberString = RomanFormatter.toRomanNumber(i);
            int romanNumber = 0;
            try {
                romanNumber = RomanFormatter.fromRomanNumber(romanNumberString);
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
