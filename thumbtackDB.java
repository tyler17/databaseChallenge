import java.util.*;
import java.lang.Object;

public class ThumbtackDB
{
  public static final String SET_ERROR = "The correct syntax is SET [name] [value]";
  public static final String GET_ERROR = "The correct syntax is GET [name]";
  public static final String UNSET_ERROR = "The correct syntax is UNSET [name]";
  public static final String NUMEQUALTO_ERROR = "The correct syntax is NUMEQUALTO [value]";
  public static final String END_ERROR = "END does not take any arguments";
  public static final String BEGIN_ERROR = "BEGIN does not take any arguments";
  public static final String ROLLBACK_ERROR = "ROLLBACK does not take any arguments";
  public static final String COMMIT_ERROR = "COMMIT does not take any arguments";
  public static final String TOO_MANY_ARGUMENTS = "Too many arguments. Names and objects cannot have spaces. ";
  public static final String NOT_IN_DATABASE = "Name is not in database";
  private HashMap<String, Object> m1;
  private HashMap<Object, Integer> m2;

  private static void set_method(HashMap<String, Object> & arg_m1, HashMap<Object, Integer> & arg_m2){
    arg_m1.put(name, value); //add key and value to hashmap
    if(arg_m2.containsKey(value)){
      int num  = arg_m2.get(value);
      arg_m2.put(value, num+1);
    }
    else{
      arg_m2.put(value, 1);
    }
  }

  private static void get_method(HashMap<String, Object> & arg_m1){
      Object return_name = "NULL";
      if (arg_m1.containsKey(name)) return_name = arg_m1.get(name);
      System.out.println(return_name); //get the value associated with the name
  }

  private static void unset_method(HashMap<String, Object> & arg_m1, HashMap<Object, Integer> & arg_m2){
      Object val = arg_m1.get(name);
      arg_m1.remove(name); //remove the key/value associated with the name
      if(val != null && arg_m2.containsKey(val)){
        int num = arg_m2.get(val);
        if(num==1) arg_m2.remove(val);
        arg_m2.put(val, num-1);
      }else{
        System.out.println(NOT_IN_DATABASE);
      }
  }

  private static void numequalto_method(HashMap<Object, Integer> & arg_m2){
      int num = 0;
      if (arg_m2.containsKey(value)) num = arg_m2.get(value);
      System.out.println(num); //returns number associated with this value
  }

  public static void main(String[] args)
  {
        m1 = new HashMap<String, Object>(); //map names to objects
        m2 = new HashMap<Object, Integer>(); //map objects to names
        Stack<Transaction> tx_stack = new Stack<Transaction>(); //map names to objects
        String command;

        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String line = input.nextLine();
            StringTokenizer st = new StringTokenizer(line);
            if(st.hasMoreTokens()){
              command = st.nextToken();
            }
            else{
              continue; //I think only happens if a space is entered
            }
            if(command.equals("SET")){
              if(!st.hasMoreTokens()){
                System.out.println(SET_ERROR);
                continue;
              }
              String name = st.nextToken();
              if(!st.hasMoreTokens()){
                System.out.println(SET_ERROR);
                continue;
              }
              String value = st.nextToken();
              if(st.hasMoreTokens()){
                System.out.println(TOO_MANY_ARGUMENTS + '\n' + SET_ERROR);
                continue;
              }

              if(!tx_stack.empty()){
                t = tx_stack.peek();
                set_method(t.m1, t.m2);
              }
              else{
                set_methond(m1, m2);
              }
            }
            if(command.equals("GET")){
              if(!st.hasMoreTokens()){
                System.out.println(GET_ERROR);
                continue;
              }
              String name = st.nextToken();
              if(st.hasMoreTokens()){
                System.out.println(TOO_MANY_ARGUMENTS + '\n' + GET_ERROR);
                continue;
              }
              if(!tx_stack.empty()){
                t = tx_stack.peek();
                get_method(t.m1);
              }
              else{
                get_method(m1);
              }
            }

            if(command.equals("UNSET")){
              if(!st.hasMoreTokens()){
                System.out.println(UNSET_ERROR);
                continue;
              }
              String name = st.nextToken();
              if(st.hasMoreTokens()){
                System.out.println(TOO_MANY_ARGUMENTS + '\n' + UNSET_ERROR);
                continue;
              }
              if(!tx_stack.empty()){
                t = tx_stack.peek();
                unset_method(t.m1);
              }
              else{
                unset_method(m1);
              }
            }

            if(command.equals("NUMEQUALTO")){
              if(!st.hasMoreTokens()){
                System.out.println(NUMEQUALTO_ERROR);
                continue;
              }
              String value = st.nextToken();
              if(st.hasMoreTokens()){
                System.out.println(TOO_MANY_ARGUMENTS + '\n' + NUMEQUALTO_ERROR);
                continue;
              }
              int num = 0;
              if (m2.containsKey(value)) num = m2.get(value);
              System.out.println(num); //returns number associated with this value

            }
            if(command.equals("END")){
              if(st.hasMoreTokens()){
                System.out.println(END_ERROR);
                continue;
              }
              break;
            }
            if(command.equals("BEGIN")){
              if(st.hasMoreTokens()){
                System.out.println(BEGIN_ERROR);
                continue;
              }
              Transaction t = new Transaction();
              t.m1 = m1;
              t.m2 = m2;
              tx_stack.push(t);
            }
            if(command.equals("ROLLBACK")){
              if(st.hasMoreTokens()){
                System.out.println(ROLLBACK_ERROR);
                continue;
              }
              tx_stack.pop();
            }
            if(command.equals("COMMIT")){
              if(st.hasMoreTokens()){
                System.out.println(COMMIT_ERROR);
                continue;
              }
              Transaction t = tx_stack.pop();
              m1 = t.m1;
              m2 = t.m2;
            }
        }
  }
}