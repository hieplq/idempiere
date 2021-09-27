package org.compiere.util;

import java.math.BigInteger;
import java.util.Arrays;

import org.compiere.util.AmtInWords_VI.FragmentBreak.Separate;

public class AmtInWords_VI implements AmtInWords{

	@Override
	public String getAmtInWords(String amount) throws Exception {
		String verifyResult = FragmentBreak.verify (amount);
		if (verifyResult != null)
			return verifyResult;
		
		return new FragmentBreak(amount).toString();
		
	}
	
	public static class FragmentBreak{
		public enum Separate{
			DOT ('.'),
			COMMA (',');
			
			char value;
			Separate(char separate){
				this.value = separate;
			}
			
			public String getValue() {
				return String.valueOf(value);
			}
		}
		
		Part [] billionPartOne;// million, thousand, hundred vnd
		Part [] billionPartTwo;// million, thousand, hundred Billion vnd
		Part [] fractionPart;//
		Separate fractionSeparate;
		String [] groupName = new String [ ]{" triệu", " nghìn", "", " triệu", " nghìn", " đồng"};
		
		public FragmentBreak(String amount) {
			this(amount, Separate.DOT);
		}
		
		public FragmentBreak(String amount, Separate fractionSeparate) {
			this (amount, fractionSeparate, ReadOption.defaultReadOption);
		}
		
		public FragmentBreak(String amount, Separate fractionSeparate, ReadOption readOption) {
			this.fractionSeparate = fractionSeparate;
			parse(amount, readOption);
		}
		
		/**
		 * a number string (0,000,000,000,000,000.0000) will parse bellow step
		 * 1. remove format character as separate at trieu, nghin, tram,....
		 * 2. integral separate by 2 part, before and after billion
		 * @param amount
		 */
		public void parse(String amount, ReadOption readOption) {
			amount = removeFomat(amount);
			String integralPart = null;
			
			if (amount.contains(fractionSeparate.getValue())) {
				String [] numParts = amount.split("\\" + fractionSeparate.getValue());
				integralPart = numParts[0];
			}else {
				integralPart = amount;
			}
			
			//TODO: not care about fraction part moment
			if (integralPart.length() < 18) {
				integralPart = String.format("%018d", new BigInteger(integralPart));
			}
			
			Part [] groups = new Part [6];
			
			boolean hasMeanBlock = false;
			for(int index = 0; index < 6; index++) {
				Part group = new Part(integralPart.substring(index * 3, (index + 1) * 3), hasMeanBlock, groupName[index]);
				group.setOption(readOption);
				hasMeanBlock = group.isMeaningBlock;
				groups[index] = group;
			}
			
			billionPartOne = Arrays.copyOfRange(groups, 0, 3);
			billionPartTwo = Arrays.copyOfRange(groups, 3, 6);
			
		}
		
		public String removeFomat(String amount) {
			if (fractionSeparate == Separate.DOT) {
				return amount.replace(Separate.COMMA.getValue(), "");
			}else {
				return amount.replace(Separate.DOT.getValue(), "");
			}
		}
		
		public static String verify(String amount) {
			return null;
		}
		
		@Override
		public String toString() {
			StringBuilder numToTextBuild = new StringBuilder();
			for (Part group : billionPartOne) {
				if(numToTextBuild.length() > 0)
					numToTextBuild.append(" ");
				
				numToTextBuild.append(group.toString());
			}
			
			if (numToTextBuild.length() > 0)
				numToTextBuild.append(" tỉ");
			
			for (Part group : billionPartTwo) {
				if(numToTextBuild.length() > 0)
					numToTextBuild.append(" ");
				
				numToTextBuild.append(group.toString());
			}
			
			return numToTextBuild.toString();
		}
		
	}
	
	public static class ReadOption{
		public static ReadOption defaultReadOption = new ReadOption().setReadZeroBlock(false).setReadZeroHundred(true).setReadZeroTen(true).setUseLe(false);
		
		public ReadOption() {}
		/**
		 * use lẻ or linh when face 0x
		 */
		boolean useLe = false;
		
		ReadOption setUseLe(boolean useLe) {
			this.useLe = useLe;
			return this;
		}
		/**
		 * effect to read hundred in case value is zero, {@link #isFirstMeaningBlock} {@link #readZeroTen}
		 */
		boolean readZeroHundred = true;
		
		ReadOption setReadZeroHundred(boolean readZeroHundred) {
			this.readZeroHundred = readZeroHundred;
			return this;
		}
		
		/**
		 * effect to read ten in case value is zero, {@link #isFirstMeaningBlock} {@link #readZeroHundred}
		 */
		boolean readZeroTen = true;
		
		ReadOption setReadZeroTen(boolean readZeroTen) {
			this.readZeroTen = readZeroTen;
			return this;
		}
		/**
		 * in case whole block is zero then can ignore or read it
		 * 1000 read as "một nghìn" or một nghìn không trăm
		 * 1000100 read as "một triệu một trăm" or "một triệu không trăm nghìn một trăm"
		 */
		boolean readZeroBlock = false;
		
		ReadOption setReadZeroBlock(boolean readZeroBlock) {
			this.readZeroBlock = readZeroBlock;
			return this;
		}
	}
	
	public static class Part{
		private static final String[] numNames = {
				"không",
				"một", 
				"hai",
				"ba", 
				"bốn", 
				"năm", 
				"sáu", 
				"bẩy", 
				"tám", 
				"chín",
				"mười", 
				"mười một", 
				"mười hai", 
				"mười ba", 
				"mười bốn", 
				"mười lăm", //list it here so no programming is needed. see note 3,4
				"mười sáu", 
				"mười bẩy", 
				"mười tám", 
				"mười chín",
				"hai mươi",
				"hai mươi mốt", //list it here so no programming is needed. see note 3,4
				"hai mươi hai",
				"hai mươi ba",
				"hai mươi bốn",
				"hai mươi lăm",//list it here so no programming is needed. see note 3,4
				"hai mươi sáu",
				"hai mươi bẩy",
				"hai mươi tám",
				"hai mươi chín",
				"ba mươi",
				"ba mươi mốt",//list it here so no programming is needed. see note 3,4
				"ba mươi hai",
				"ba mươi ba",
				"ba mươi bốn",
				"ba mươi lăm",//list it here so no programming is needed. see note 3,4
				"ba mươi sáu",
				"ba mươi bẩy",
				"ba mươi tám",
				"ba mươi chín",
				"bốn mươi",
				"bốn mươi mốt",//list it here so no programming is needed. see note 3,4
				"bốn mươi hai",
				"bốn mươi ba",
				"bốn mươi bốn",
				"bốn mươi lăm",//list it here so no programming is needed. see note 3,4
				"bốn mươi sáu",
				"bốn mươi bẩy",
				"bốn mươi tám",
				"bốn mươi chín",
				"năm mươi",
				"năm mươi mốt",//list it here so no programming is needed. see note 3,4
				"năm mươi hai",
				"năm mươi ba",
				"năm mươi bốn",
				"năm mươi lăm",//list it here so no programming is needed. see note 3,4
				"năm mươi sáu",
				"năm mươi bẩy",
				"năm mươi tám",
				"năm mươi chín",
				"sáu mươi",
				"sáu mươi mốt",//list it here so no programming is needed. see note 3,4
				"sáu mươi hai",
				"sáu mươi ba",
				"sáu mươi bốn",
				"sáu mươi lăm",//list it here so no programming is needed. see note 3,4
				"sáu mươi sáu",
				"sáu mươi bẩy",
				"sáu mươi tám",
				"sáu mươi chín",
				"bẩy mươi",
				"bẩy mươi mốt",//list it here so no programming is needed. See note 3,4
				"bẩy mươi hai",
				"bẩy mươi ba",
				"bẩy mươi bốn",
				"bẩy mươi lăm",//list it here so no programming is needed. See note 3,4
				"bẩy mươi sáu",
				"bẩy mươi bẩy",
				"bẩy mươi tám",
				"bẩy mươi chín",
				"tám mươi",
				"tám mươi mốt",//list it here so no programming is needed. See note 3,4
				"tám mươi hai",
				"tám mươi ba",
				"tám mươi bốn",
				"tám mươi lăm",//list it here so no programming is needed. See note 3,4
				"tám mươi sáu",
				"tám mươi bẩy",
				"tám mươi tám",
				"tám mươi chín",
				"chín mươi",
				"chín mươi mốt",//list it here so no programming is needed. See note 3,4
				"chín mươi hai",
				"chín mươi ba",
				"chín mươi bốn",
				"chín mươi lăm",//list it here so no programming is needed. See note 3,4
				"chín mươi sáu",
				"chín mươi bẩy",
				"chín mươi tám",
				"chín mươi chín"
			};
		
		ReadOption readOption = new ReadOption();
		
		public Part(String part, boolean hasMeaningBlock) {
			this(part, hasMeaningBlock, "");
		}
		
		public Part(String part, boolean hasMeaningBlock, String group) {
			int iPart = Integer.valueOf(part);
			hundred = iPart / 100;
			ten = iPart % 100 / 10;
			unit = iPart % 100 % 10;
			this.group = group;
			if (hundred != 0 || ten != 0 || unit != 0 || hasMeaningBlock)
				this.isMeaningBlock = true;
			
			if (this.isMeaningBlock && !hasMeaningBlock)
				this.isFirstMeaningBlock = true;
		}
		
		public Part setOption(ReadOption readOption) {
			this.readOption = readOption;
			return this;
		}
		
		/**
		 * effect to how read zero placeholder
		 * example 001 will read as "không trăm lẻ(linh) một" in case it's mid block otherwise read as "một"
		 * example 011 will read as "không trăm mừoi một" in case it's mid block otherwise read as "mười một"
		 */
		boolean isFirstMeaningBlock = false;
		
		boolean isMeaningBlock = false;
		
		int hundred;
		int ten;
		int unit;
		
		String group;// trieu, nghin
		
		@Override
		public String toString() {
			if (hundred == 0 && ten == 0 && unit == 0) {
				if (!isMeaningBlock || !readOption.readZeroBlock) {
					return "";
				}else {
					return "không trăm" + group;
				}
			}
			
			StringBuilder blockToText = new StringBuilder();
			if (hundred != 0 || (!isFirstMeaningBlock && readOption.readZeroHundred)) {
				blockToText.append(numNames[hundred]);
				blockToText.append(" trăm");
			}

			if (ten != 0) {
				if (blockToText.length() > 0)
					blockToText.append(" ");
				blockToText.append(numNames[ten * 10 + unit]);
			}else if (unit != 0){
				if (readOption.readZeroTen && !(isFirstMeaningBlock && hundred == 0) ) {
					if(readOption.useLe)
						blockToText.append(" lẻ ");
					else
						blockToText.append(" linh ");
				}
				
				if (blockToText.length() > 0)
					blockToText.append(" ");// only unit
				
				blockToText.append(numNames[unit]);
			}
			return blockToText.toString() + group;
		}
		
		
	}

	public static void main (String[] args){
		//testReadPartSuilt();
		testFragmentBreak();
	}
	
	
	
	public static void testFragmentBreak() {
		String [] testSuilt = new String [] {"12.345", "10220134578", "1.093.201.034.578", "100.932.010.345.780"};
		
		for (String testCase : testSuilt) {
			FragmentBreak  fragmentBreak = new FragmentBreak(testCase, Separate.COMMA);
			System.out.printf("%s %s\n", testCase, fragmentBreak);
		}
	}
	
	public static void testReadPartSuilt() {
		String [] testSuilt = new String [] {"500", "520", "523", "503", "023", "003", "020", "000"};
		
		ReadOption [] testOption = new ReadOption [] {new ReadOption().setReadZeroBlock(true).setReadZeroHundred(true).setReadZeroTen(true), 
				new ReadOption().setReadZeroBlock(true).setReadZeroHundred(true).setReadZeroTen(false), 
				new ReadOption().setReadZeroBlock(true).setReadZeroHundred(false).setReadZeroTen(false), 
				new ReadOption().setReadZeroBlock(false).setReadZeroHundred(false).setReadZeroTen(false),
				new ReadOption().setReadZeroBlock(true).setReadZeroHundred(false).setReadZeroTen(true),
				new ReadOption().setReadZeroBlock(false).setReadZeroHundred(false).setReadZeroTen(true),
				new ReadOption().setReadZeroBlock(false).setReadZeroHundred(true).setReadZeroTen(true)};
		
		System.out.printf("|%20s|%15s|%15s|%15s|%15s|%s\n", "First Meaning Block", "readZeroBlock", "readZeroHundred", "readZeroTen", "amount", "to text");

		for (ReadOption readOption : testOption) {
			testReadPart(testSuilt, false, readOption);
		}
		
		for (ReadOption readOption : testOption) {
			testReadPart(testSuilt, true, readOption);
		}		
	}
	
	public static void testReadPart(String [] testSuilt, boolean hasMeaningBlock, ReadOption readOption) {
		for (String testCase : testSuilt) {
			Part partTest = new Part(testCase, hasMeaningBlock).setOption(readOption);
			System.out.printf("|%20s|%15s|%15s|%15s|%15s|%s\n", !hasMeaningBlock, readOption.readZeroBlock, readOption.readZeroHundred, readOption.readZeroTen, testCase, partTest);
		}
	}
}
