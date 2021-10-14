import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class daycare {

    /**
     * read and write contents to file
     * Check whether the input data is valid during write
     *
     */
    public static void admitChild(){
        try{
            String pathname = null;
            Scanner inputFilePath = new Scanner(System.in);
            System.out.println("Please enter file address here:\n");
            if (inputFilePath.hasNextLine()) {
                pathname = inputFilePath.nextLine();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname)));
            ArrayList<Integer> idList = new ArrayList<>();
            // File to store child information
            File childInput = new File("ChildList.txt");
            if(!childInput.exists()){
                try{
                    childInput.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            int count = 0;
            String checkin = null;
            while ((checkin = br.readLine()) != null){

                // File to store the result: a confirmation message or an error message
                File output = new File("output.txt");
                if(!output.exists()){
                    try{
                        output.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output,true)));
                count++;
                String[] buf = checkin.split(" ");
                boolean validInput = true;

                // check child name
                if(buf[0] == null || buf[0].length() <= 0){
                    bw.write("Missing name.\r\n");
                    validInput = false;
                } else if(daycare.isAlphaName(buf[0]) == false){
                    bw.write("Child name at line" + count + " must be alphabetic.\r\n");
                    validInput = false;
                } else if ((buf[0].length() < 2) || (buf[0].length() > 20)) {
                    bw.write("Length of name at line"+ count + " is out of range.\r\n");
                    validInput = false;
                }

                // check child ID number
                if(buf[1] == null || buf[1].length() <= 0){
                    bw.write("Missing ID number.\r\n");
                    validInput = false;
                } else if(Integer.parseInt(buf[1]) < 100 || Integer.parseInt(buf[1]) > 999){
                    bw.write("Invalid ID number at line "+ count + "\r\n");
                    validInput = false;
                } else if(validInput){

                    //Check child ID number whether unique or not
                    int childId = Integer.parseInt(buf[1]);
                    idList.add(childId);
                    HashSet<Integer> id = new HashSet<>(idList);
                    if(idList.size() != id.size()){
                        if(!idList.isEmpty()){
                            idList.remove(idList.size() - 1);
                        }
                        bw.write("Child ID number at line " + count + " must be unique.\r\n");
                        validInput = false;
                    }
                }

                // check child age
                if(buf.length < 3){
                    bw.write("Missing age.\r\n");
                    validInput = false;
                } else if(Integer.parseInt(buf[2]) < 2 || Integer.parseInt(buf[2]) > 60){
                    bw.write("Invalid age at line" + count + "\r\n");
                    validInput = false;
                }

                // This line of data is all valid
                if(validInput){
                    bw.write("Line number " + count + " added success.\r\n");
                    BufferedWriter childList = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(childInput,true)));
                    for (int i = 0; i < buf.length; i++){
                        if(i < 2){
                            childList.write(buf[i] + " ");
                        } else
                            childList.write(buf[i]);
                    }
                    childList.write("\r\n");
                    childList.flush();
                    childList.close();
                }

                bw.flush();
                bw.close();
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Removes the child associated with the given ID number.
     *
     */
    public static void deleteChild() throws IOException {
        try{
            String pathname = null;
            Scanner inputFilePath = new Scanner(System.in);
            System.out.println("Please enter file address here:\n");
            if (inputFilePath.hasNextLine()) {
                pathname = inputFilePath.nextLine();
            }

            String removeId = null;
            Scanner inputId = new Scanner(System.in);
            System.out.println("Please enter the child ID number which you want to remove:\r\n");
            if (inputId.hasNextLine()){
                removeId = inputId.nextLine();
            }

            StringBuffer bf = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname)));
            // File to store remove info
            File removeOutput = new File("removeOutput.txt");
            if(!removeOutput.exists()){
                try{
                    removeOutput.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedWriter reout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(removeOutput,true)));

            boolean Delete = false;
            if (Integer.parseInt(removeId) < 100 || Integer.parseInt(removeId) > 999){
                reout.write("Invalid ID number in daycare.\r\n");
                reout.flush();
                reout.close();
                br.close();
                return;
            }

            String input = null;
            while ((input = br.readLine()) != null) {
                input = input.trim();
                if (!input.contains(removeId)) {
                    bf.append(input).append("\r\n");
                } else {
                    Delete = true;
                    reout.write("The child with ID " + removeId + " removed success.\r\n");
                }
            }

            if (!Delete) {
                reout.write("There are not any child with the ID number.\r\n");
            }

            br.close();

            reout.flush();
            reout.close();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathname)));
            bw.write(bf.toString());

            bw.flush();
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * add teachers to daycare
     */
    public static void addTeacher(){
        try {
            String teacherlist = null;
            Scanner inputFilePath = new Scanner(System.in);
            System.out.println("Please enter file address here:\n");
            if (inputFilePath.hasNextLine()) {
                teacherlist = inputFilePath.nextLine();
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(teacherlist)));


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public static void deleteTeacher(){

    }
    /**
     *
     */
    public static void assignTeacher(){

    }
    /**
     *  Check if the child name is alphabetic
     *
     * @param s child name
     * @return if it is alphabetic return true, otherwise return false
     */
    private static boolean isAlphaName(String s) {
        if( s == null) {
            return false;
        }
        return s.matches("[a-zA-Z]+");
    }

    /**
     * Check if the child ID number is unique
     *
     * @param s child ID number
     * @return if it is unique return true, otherwise return false
     */
    private static boolean isUniqueId(String s) {
        // TODO Auto-generated method stub
        boolean[] ID = new boolean[256];
        for(int i = 0; i < s.length(); i++) {
            int val = s.charAt(i);
            if(ID[val]){
                return false;
            }
            ID[val] = true;
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("There are two choices for Cradle to Crayons.");
        System.out.println("Please enter 1 to add child list.");
        System.out.println("Please enter 2 o remove a child from list.");
        System.out.println("Please enter 3 to add teacher.");
        System.out.println("Please enter 4 to delete a teacher.");
        System.out.println("Please enter 5 to assign teacher.");

        Scanner testerInput = new Scanner(System.in);
        int inputNum = testerInput.nextInt();
        if (inputNum == 1) {
            admitChild();
        } else if (inputNum == 2) {
            deleteChild();
        } else if (inputNum == 3){
            addTeacher();
        } else if (inputNum == 4){
            deleteTeacher();
        } else if (inputNum == 5){
            assignTeacher();
        } else {
            System.out.println("Invalid instruction.\n");
        }
        testerInput.close();
    }
}
