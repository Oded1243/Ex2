// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

public class Functions {
    public static boolean IsNumber(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Checks if the input is a formula
    public static boolean IsForm(String input) {
        // Return false if input is null or empty
        if (input == null || input.isBlank()) {
            return false;
        }

        String formula = "^=\\s*([A-Za-z]+\\d+|\\d+|\\(([^()]|\\([^()]*\\))*\\))(\\s*[-+*/]\\s*([A-Za-z]+\\d+|\\d+|\\(([^()]|\\([^()]*\\))*\\)))*$";

        // Matching with the regex pattern
        return Pattern.matches(formula, input.trim());
    }

    // Checks if the input is text
    public static boolean IsText(String s) {
        if (s == null || s.isBlank()) return false;
        s = s.trim();
        // It's text if it's not a number and not a formula
        return !IsNumber(s) && !IsForm(s);
    }

    // Calculate the string such as (5+3)*2
    public static String Calculate(String s) {
        if (s.equals("") || s==null) {
            return "";
        }
        try {
            //remove the = sign
            String log = s;
            double ans = 0;
            ArrayList divided = DividerbyAction(s);
            //remove the = sign
            if (divided.contains("=")) {divided.remove(0);}
            ArrayList<Integer> indexbox = new ArrayList<>();
            boolean torun = false;
            for (int i = 0; i<divided.size(); i+=1) {
                if ((divided.get(i)).equals("(")) {torun = true;}
                while (torun) {
                    indexbox.add(i);
                    //This loop will run until it finds the closing ) or finds one more opening (
                    i+=1;
                    if ((divided.get(i)).equals("(")) {
                        indexbox.clear();
                    }
                    if ((divided.get(i)).equals(")")) {
                        int left_bound = indexbox.get(1);
                        int right_bound = i;
                        //At this stage this is found (1*2) , the indexes are pointing to 1*2
                        //Calculate this expression and turn it to double
                        String expression = String.join("", divided.subList(left_bound,right_bound));
                        double result = Double.parseDouble(String.valueOf(Calculate(expression)));
                        //Add the expression that we've calculated to a string
                        divided.set(left_bound-1,Double.toString(result));
                        //Remove the initial expression from the string
                        divided.subList(left_bound,right_bound+1).clear();
                        s = String.join("",divided);
                        //Clean the indexBox
                        System.out.println("Current indexbox: " + indexbox);
                        indexbox.clear();
                        torun = false;
                        //Pass further into the recursion loop
                        double res = Double.parseDouble(Calculate(s));
                        System.out.println("The following string extracted --> " + log.substring(left_bound,right_bound+1) + " from " + divided );
                        return String.valueOf(res);
                    }
                }
            }
            //Return the result
            ans = Double.parseDouble(looper(divided).get(0).toString());

            return String.valueOf(ans);
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid form");
        }

    }

    private static ArrayList<String> DividerbyAction(String s) {
        //Check for [/,*,+,-]
        ArrayList<String> symbols = new ArrayList();
        //Add all the chars in a string to a list
        for (int i = 0; i < s.length(); i += 1) {
            symbols.add(Character.toString(s.charAt(i)));
        }
        //Add one ~ at a last index for further logic
        symbols.add("~");
        //System.out.println("Before consolidation"+symbols);
        //Consolidate the digits in the list [2,5] -> [25]
        String numbers = "0123456789.";
        StringBuilder String = new StringBuilder();
        //Create an arraylist of indexes
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < symbols.size(); i += 1) {
            if (numbers.contains(symbols.get(i))) {
                String.append(symbols.get(i));
                //Add it to the indexes list
                indexes.add(i);
                //Replace the digit with null
                if (indexes.size() > 1) {
                    symbols.set(i, null);
                }
            } else {
                //System.out.println(indexes);
                //In case we are not a digit index in symbols - we should check whether the digit containing operation has happened in the past - Indexes list shouldn't be empty
                if (!indexes.isEmpty()) {
                    //Append the collected digits to symbols
                    String collected = String.toString();
                    //Place it to the index where initial digit was found
                    int initial_index = indexes.get(0);
                    symbols.set(initial_index,collected);
                    //Clean the traces
                    indexes.clear();
                    String = new StringBuilder();
                }
            }
        }
        //System.out.println("After consolidation"+symbols);
        //Remove nulls
        symbols.removeIf(Objects::isNull);
        symbols.remove("~");
        //System.out.println("Return" + symbols);
        return symbols;



    }

    public static ArrayList<String> looper(ArrayList<String> s) {
        while (s.contains("/")) {
            for (int i = 0; i < s.size(); i += 1) {
                if (s.get(i).toString().equals("/")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(s.get(i - 1));
                    double number_after = Double.parseDouble(s.get(i + 1));
                    if (number_after==0) {throw new ArithmeticException("You cand divide by 0");}
                    newvalue = Double.toString(number_before / number_after);
                    int first_index = i - 1;
                    s.set(first_index, newvalue);
                    s.set(i, null);
                    s.set(i + 1, null);
                    break;
                }
            }

            for (int i=0;i<s.size();i+=1) {
                s.remove(null);
            }
        }
        while (s.contains("*")) {
            for (int i = 0; i < s.size(); i += 1) {
                if (s.get(i).toString().equals("*")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(s.get(i - 1));
                    double number_after = Double.parseDouble(s.get(i + 1));
                    newvalue = Double.toString(number_before * number_after);
                    int first_index = i - 1;
                    s.set(first_index, newvalue);
                    s.set(i, null);
                    s.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<s.size();i+=1) {
                s.remove(null);
            }
        }
        while (s.contains("-")) {
            for (int i = 0; i < s.size(); i += 1) {
                if (s.get(i).toString().equals("-")) {
                    String newvalue = "";
                    if (!s.isEmpty() && s.get(0).equals("-")) {
                        double number_itself = Double.parseDouble(s.get(i+1));
                        number_itself = 0 - number_itself;
                        s.set((i), String.valueOf(number_itself));
                        s.set(i+1,null);
                        break;
                    }
                    double number_before = Double.parseDouble(s.get(i - 1));
                    double number_after = Double.parseDouble(s.get(i + 1));
                    newvalue = Double.toString(number_before - number_after);
                    int first_index = i - 1;
                    s.set(first_index, newvalue);
                    s.set(i, null);
                    s.set(i + 1, null);
                    break;
                }
            }
            //Delete the nulls from the list in case break worked
            for (int i=0;i<s.size();i+=1) {
                s.remove(null);
            }
        }
        while (s.contains("+")) {
            for (int i = 0; i < s.size(); i += 1) {
                if (s.get(i).toString().equals("+")) {
                    String newvalue = "";
                    double number_before = Double.parseDouble(s.get(i - 1));
                    double number_after = Double.parseDouble(s.get(i + 1));
                    newvalue = Double.toString(number_before + number_after);
                    int first_index = i - 1;
                    s.set(first_index, newvalue);
                    s.set(i, null);
                    s.set(i + 1, null);
                    break;
                }
            }

            for (int i=0;i<s.size();i+=1) {
                s.remove(null);
            }
        }

        return s;
    }

}