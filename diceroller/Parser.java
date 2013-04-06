package zornco.diceroller;


/*
 * Parser.java
 * Calculator
 *
 * Parses an equation in String form
 */

import java.util.Random;
import java.util.Vector;

public class Parser {
	private static Random rand;

	public static String parse(String equ) {
		Vector<String> equation = new Vector<String>();
		equ = remove_whitespace(equ);
		String temp = "";
		boolean test;
		for (int index = 0; index <= equ.length() - 1; index++) {
			test = false;
			/*
			 * handles whether '-' should be a negative sign, or a subtraction
			 * operator
			 */
			if (equ.charAt(index) == '-') {
				if (index == 0 || equ.charAt(index - 1) < '0'
						|| equ.charAt(index - 1) > '9') {
					temp += "-";
				} else {
					equation.add("-");
				}
				index++;
			}
			/*
			 * Puts tokens into separate elements in the vector
			 */
			while (index < equ.length()
					&& (equ.charAt(index) >= '0' && equ.charAt(index) <= '9' || equ
							.charAt(index) == '.')) {
				temp += equ.substring(index, index + 1);
				index++;
				test = true;
			}
			if (test)
				equation.add(temp);
			temp = "";

			if (index == equ.length())
				break;
			if (equ.charAt(index) >= '(' && equ.charAt(index) <= '/'
					&& equ.charAt(index) != 44 || equ.charAt(index) == '^'
					&& equ.charAt(index) != '.'||equ.charAt(index) == 'd') {
				equation.add(equ.substring(index, index + 1));
			}
		}
		/*
		 * Assumes any tokens without an operation in between need to be
		 * multiplied eg: 3(4+5) -> 3*(4+5)
		 */
		/*
		 * for (int i = 0; i < equation.size() - 1; i++) { if
		 * (!(isOp(equation.get(i)) || isOp(equation.get(i + 1) ) ) )
		 * equation.insertElementAt("*", i + 1); }
		 */
		return parse(equation);
	}

	private static String parse(Vector<String> equ) {
		/*
		 * Recursively handles parenthetical grouping
		 */
		String result, subRes;
		int bParen = 0, eParen = 0;
		while ((!equ.get(bParen).equals("(")) && bParen <= equ.size() - 1) {
			bParen++;
			if (bParen == equ.size())
				break;
		}
		if (bParen < equ.size()) {
			while (!equ.get(eParen).equals(")") && eParen <= equ.size() - 1) {
				if (equ.get(eParen).equals("("))
					bParen = eParen; // If if it finds a new left paren
				eParen++;
			}
			Vector<String> subEqu = new Vector<String>();
			for (int index = bParen + 1; index < eParen; index++)
				subEqu.add(equ.get(index));
			subRes = parse(subEqu); // <-- Calls itself until parentheses are
									// gone
			for (int index = eParen; index >= bParen + 1; index--)
				equ.remove(index);
			equ.set(bParen, subRes);
			result = parse(equ); // <-- This will call itself until all
									// parentheses are gone
			return result;
		}
		equ = parseExp(equ); // Follows
		equ = parseDice(equ);
		equ = parseMultDiv(equ); // order of
		equ = parseAddSub(equ); // operations
		return equ.get(0).toString();
	}

	private static Vector<String> parseExp(Vector<String> equ) {
		/*
		 * Exponentiates numbers NOTE: 2 ^ 3 ^ 4 is different from (2 ^ 3) ^ 4 2
		 * ^ 3 ^ 4 is the same as 2 ^ (3 ^ 4)
		 */
		Double num, exp, result;
		for (int index = equ.size() - 1; index > 0; index--) {
			if (equ.get(index).equals("^")) {
				num = new Double(equ.get(index - 1).toString());
				exp = new Double(equ.get(index + 1).toString());
				result = Math.pow(num, exp);
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--;
				// Must decrement the index since two elements have been removed
			}
		}
		return equ;
	}

	private static Vector<String> parseMultDiv(Vector<String> equ) {
		/*
		 * Multiplication and division are next in the order of operations
		 */
		Double numOne, numTwo, result;
		for (int index = 0; index <= equ.size() - 1; index++) {
			if (index <= equ.size() - 1 && equ.get(index).equals("*")) {
				numOne = new Double(equ.get(index - 1));
				numTwo = new Double(equ.get(index + 1));
				result = numOne * numTwo;
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--;
			}
			if (index <= equ.size() - 1 && equ.get(index).equals("/")) {
				numOne = new Double(equ.get(index - 1));
				numTwo = new Double(equ.get(index + 1));
				result = numOne / numTwo;
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--;
			}
		}
		return equ;
	}

	private static Vector<String> parseDice(Vector<String> equ) {
		rand = new Random();
		Double numOne, result = 0D;
		int numTwo;
		for (int index = 0; index <= equ.size() - 1; index++) {
			if (equ.get(index).equals("d")) {
				numOne = new Double(equ.get(index - 1));
				numTwo = new Integer(equ.get(index + 1));
				for (double i = 0.0D; i < numOne; i++) {
					double roll = rand.nextInt(numTwo)+1;
					result += roll;
				}
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--; 
			}
		}
		return equ;
	}
	
	private static Vector<String> parseAddSub(Vector<String> equ) {
		/*
		 * Addition and Subtraction are last
		 */
		Double numOne, numTwo, result;
		for (int index = 0; index <= equ.size() - 1; index++) {
			if (index <= equ.size() - 1 && equ.get(index).equals("+")) {
				numOne = new Double(equ.get(index - 1));
				numTwo = new Double(equ.get(index + 1));
				result = numOne + numTwo;
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--;
			}
			if (index <= equ.size() - 1 && equ.get(index).equals("-")) {
				numOne = new Double(equ.get(index - 1));
				numTwo = new Double(equ.get(index + 1));
				result = numOne - numTwo;
				equ.set(index, result.toString());
				equ.remove(index + 1);
				equ.remove(index - 1);
				index--;
			}
		}
		return equ;
	}

	private static String remove_whitespace(String str) {
		/*
		 * Gets rid of whitespace
		 */
		String[] tokens = str.split("\\s");
		String result = "";
		for (int i = 0; i < tokens.length; i++)
			result += tokens[i];
		return result;
	}

	private static boolean isOp(String str) {
		/*
		 * Used to make sure that operators are in the right places
		 */
		char[] ops = { '^', 'd' , '*', '/', '+', '-'};
		boolean len = str.length() == 0;
		for (int i = 0; i < ops.length; i++)
			if (ops[i] == str.charAt(0))
				return len;
		return false;
	}
}