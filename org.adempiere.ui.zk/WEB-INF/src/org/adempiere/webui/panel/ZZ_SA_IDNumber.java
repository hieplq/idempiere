package org.adempiere.webui.panel;

import java.util.regex.Pattern;

/**
 *  South African ID Number Utilities.
 *  @author Anesh Meghraj
 *  @version $Id: ZZ_SA_IDNumber.java,v 1.1 2009/04/17 09:21:23 oracle Exp $
 */
public class ZZ_SA_IDNumber {

    /**
    * South Africa ID Number Format : {YYMMDD}{G}{SSS}{C}{A}{Z}
    * YYMMDD : Date of birth.
    * G  : Gender. 0-4 Female; 5-9 Male.
    * SSS  : Sequence No. for DOB/G combination.
    * C  : Citizenship. 0 SA; 1 Other.
    * A  : Usually 8, or 9 [can be other values]
    * Z  : Control digit calculated in the following section:
    ****/

    private String IDNumber = null;
    private String DOB = null;
    private String GENDER = null;
    private int CITIZENSHIP = -1;
    private int CDVNumber = 0;

    private static final int ID_NUMBER_LENGTH=13;
    public static final int FEMALE=0;
    public static final int MALE=1;
    public static final int SA_CITIZEN=0;
    public static final int NONSA_CITIZEN=1;

    public ZZ_SA_IDNumber() {
        this(null);
    }
    public ZZ_SA_IDNumber(String number) {
        if ( number != null && number.length()==ID_NUMBER_LENGTH && !Pattern.compile("\\D").matcher(number).find()) {
            IDNumber = number;
            splitIDIntoComponents();
            setCDV();
        } else {
            IDNumber=null;
        }
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String number) {
        IDNumber = number;
    }

    public int getCDVNumber() {
        return CDVNumber;
    }

    private void splitIDIntoComponents(){
        DOB = IDNumber.substring(0, 6);
        GENDER = IDNumber.substring(6, 7);
        CITIZENSHIP = Integer.parseInt(IDNumber.substring(10, 11));
        CDVNumber = Integer.parseInt(IDNumber.substring(12));
    }

    protected void setCDV () {
        char[] finalChars = IDNumber.trim().toCharArray();

        // Add up all the digits in the odd numbered positions
        int oddsTotal = 0;
        for (int o = 0; o < (ID_NUMBER_LENGTH - 1); o += 2){
            oddsTotal += (finalChars[o] - '0');
        }

        // create a new number with all the digits in even number positions
        int evensTotal = 0;
        for (int e = 1; e < ID_NUMBER_LENGTH; e += 2){
            int factor = (11 - e) / 2;
            int digit = (finalChars[e] - '0');
            int place = (int)Math.pow(10, factor);
            evensTotal += digit * place;
        }

        // Multiply evens total by 2
        evensTotal *= 2;

        // Now add each digit in the evens number
        char[] evenChars = Integer.toString(evensTotal).toCharArray();
        evensTotal = 0;
        for (int d = 0; d < evenChars.length; d++){
            evensTotal += (evenChars[d] - '0');
        }

        CDVNumber = 10 - (evensTotal + oddsTotal) % 10;
        if (CDVNumber == 10) CDVNumber = 0;
    }

    public boolean isIDNumberValid (){
        // Check the DOB.  000000 will give you a valid ID number.
        if ((DOB == null) || (!validateDOB()))
            return false;

        int cdv = Integer.parseInt(IDNumber.substring(ID_NUMBER_LENGTH - 1));
        return cdv == CDVNumber;
    }

    private boolean validateDOB(){
        int month = Integer.parseInt(DOB.substring(2,4));
        int day = Integer.parseInt(DOB.substring(4,6));

        if (month < 1 || month > 12)
            return false;

        // Don't worry about leap years, etc. since the check digit will take care of that
        if (day < 1 || day > 31)
            return false;

        return true;
    }

    public String getDOB() {
        return DOB;
    }

    /**
     * Get the Gender
     * @return 0-Female, 1=Male
     */
    public int getGENDER() {
        return (Integer.parseInt(GENDER) < 5) ? FEMALE : MALE;
    }
    public int getCITIZENSHIP() {
        return CITIZENSHIP;
    }
}